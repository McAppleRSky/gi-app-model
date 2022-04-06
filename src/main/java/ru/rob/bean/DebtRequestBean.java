package ru.rob.bean;

import org.hibernate.*;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.git.gkh.SessionFactoryType;
import ru.git.gkh.capremont.tasks.TaskParams;
import ru.git.gkh.core.enums.DebtResponseStatus;
import ru.git.gkh.dto.debtRequests.DebtRequestFilterDto;
import ru.git.gkh.dto.debtRequests.DebtRequestPage;
import ru.git.gkh.dto.debtRequests.DebtRequestView;
import ru.git.gkh.entity.gisconfig.GisUser;
import ru.git.gkh.entity.preferences.debtRequests.DebtRequest;
import ru.git.gkh.entity.preferences.debtRequests.DebtResponse;
import ru.git.gkh.entity.preferences.debtRequests.DebtResponseCourtSolution;
import ru.git.gkh.entity.preferences.debtRequests.DebtResponseDetail;
import ru.git.gkh.entity.sec.Usr;
import ru.git.gkh.gis.task.FormResponsesTask;
import ru.git.gkh.utils.SomeUtils;
import ru.git.gkh.utils.task.TaskObserver;
import ru.git.gkh.utils.task.TaskObserverFactory;

import javax.annotation.Resource;
import javax.ejb.Local;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Stateless
@Local
@SuppressWarnings("unchecked")
public class DebtRequestBean {

    private static final Logger log = LoggerFactory.getLogger(DebtRequestBean.class);

    private FormResponsesTask task;

    @Resource(lookup = "concurrent/Common")
    private ManagedExecutorService managedExecutorService;

    @Inject
    @ru.git.gkh.SessionFactory(SessionFactoryType.GKH)
    private SessionFactory gkhSessionFactory;

    @Inject
    @ru.git.gkh.SessionFactory(SessionFactoryType.PM)
    private SessionFactory pmSessionFactory;

    @Resource
    private SessionContext ctx;

    public DebtRequestPage getRequestList(final DebtRequestFilterDto filter){
        final Session session = gkhSessionFactory.openSession();
        try {
            final List<DebtRequest> list = createCriteria(
                    DebtRequest.class,
                    session,
                    filter,
                    true
            ).list();

            Long totalCount = (Long) createCriteria(
                    DebtRequest.class,
                    session,
                    filter,
                    false
            ).uniqueResult();

            int countRow = list.size();
            if(DebtResponseStatus.SENT.equals(filter.responseStatus) && filter.hasDebt != null)
                list.removeIf(o -> o.response != null && o.response.hasDebt == !filter.hasDebt);
            totalCount -= (countRow - list.size());

            return new DebtRequestPage(list, totalCount);
        } finally {
            SomeUtils.close(session);
        }
    }

    public <TEntity> Criteria createCriteria(final Class<TEntity> clazz, final Session session, final DebtRequestFilterDto filter, final boolean isSelectCriteria){
        final Criteria criteria = session.createCriteria(clazz);

        if(filter.start != null && filter.limit != null && isSelectCriteria)
            criteria.addOrder(Order.desc("responseDate"))
                    .setFirstResult(filter.start)
                    .setMaxResults(filter.limit);
        else if (!isSelectCriteria)
            criteria.setProjection(Projections.rowCount());

        if(filter.responseStatus != null)
            criteria.add(Restrictions.eq("responseStatus", filter.responseStatus));

        if (filter.requestNumber != null)
            criteria.add(Restrictions.eq("number", filter.requestNumber));

        if (filter.responseStartDate != null && filter.responseEndDate != null)
            criteria.add(Restrictions.between("responseDate", filter.responseStartDate, filter.responseEndDate));

        return criteria;
    }

    public DebtRequestView getRequestItem(final String id){
        final Session session = gkhSessionFactory.openSession();
        try {
            final DebtRequest request = (DebtRequest) session
                    .createCriteria(DebtRequest.class)
                    .add(Restrictions.idEq(id))
                    .uniqueResult();
            return DebtRequestView.from(request);
        } finally {
            SomeUtils.close(session);
        }
    }
    public void startFormResponsesTask(final DebtRequestFilterDto filter){
        final TaskObserver observer = TaskObserverFactory.getTaskObserver();
        final Long taskId = System.currentTimeMillis();
        final String description = "Формирование ответов";
        final String user = ctx.getCallerPrincipal().getName();
        final TaskParams params = new TaskParams(gkhSessionFactory, observer, taskId, description, user);
        params.put("filter", filter);
        task = new FormResponsesTask(params, this);
        managedExecutorService.submit(task);
    }

    public int formResponses(final DebtRequestFilterDto filter) throws Exception {
        Session gkhSession = null;
        try {
            int formedResponsesCount = 0;
            gkhSession = gkhSessionFactory.openSession();

            final List<DebtRequest> requests = createCriteria(
                    DebtRequest.class,
                    gkhSession,
                    filter,
                    true
            ).list();

            int progress = 0;
            for(int i = 0; i < requests.size(); i++) {
                progress = task.updateProgress(i, requests.size(), 10, progress);
                final DebtRequest request = requests.get(i);
                if(request.response == null)
                    try {
                        final Usr user = SomeUtils.extractUser(gkhSession, ctx);
                        final GisUser gisUser = (GisUser) gkhSession.get(GisUser.class, user.getId());
                        if(gisUser == null)
                            throw new Exception("У пользователя \"".concat(user.getCaption() + "\" не добавлен идентификатор ГИС"));

                        request.response = new DebtResponse();
                        request.response.executorFio = gisUser.fio;
                        request.response.executorGuid = gisUser.userGuid;

                        final List<Map<String, Object>> flsDebtList = findFlsList(gkhSession, request);
                        final List<String> flsHasDebtList = flsDebtList.stream()
                                .filter(map -> ((BigDecimal) map.get("debtSum")).signum() > 0)
                                .map(map -> (String) map.get("flsNumber"))
                                .collect(Collectors.toList());

                        final List<Map<String, Object>> debtorListPir = findDebtorList(flsHasDebtList, request.startDate, request.endDate);
                        request.response.hasDebt = debtorListPir.stream()
                                .anyMatch(o -> {
                                    final Object obj =  o.get("executionList");
                                    return obj != null && !((List<Object>) obj).isEmpty();
                                });
                        for(Map<String, Object> debtorData : debtorListPir){
                            final String[] debtorFio = ((String) debtorData.get("debtorFio")).split(" ");
                            final DebtResponseDetail detail = new DebtResponseDetail();
                            detail.response = request.response;
                            detail.lastName = (debtorFio.length > 0 ? debtorFio[0] : "не указано");
                            detail.firstName = (debtorFio.length > 1 ? debtorFio[1] : "не указано");
                            detail.middleName = (debtorFio.length > 2 ? debtorFio[2] : null);

                            detail.courtSolutions = this.findCourtSolutionFiles(gkhSession, (List<Object>) debtorData.get("executionList"));
                            if(!detail.courtSolutions.isEmpty()){
                                detail.courtSolutions
                                        .forEach(o -> o.detail = detail);
                                request.response.getDetails().add(detail);
                            }
                        }
                        request.responseStatus = DebtResponseStatus.SENT;
                        gkhSession.update(request);
                        gkhSession.flush();
                        formedResponsesCount++;
                    } catch (SQLException | HibernateException e) {
                        log.error(e.getMessage(), e);
                        continue;
                    }
            }

            return formedResponsesCount;
        } finally {
            SomeUtils.close(gkhSession);
        }
    }

    private List<DebtResponseCourtSolution> findCourtSolutionFiles(final Session session, final List<Object> executionList){
        if(executionList == null || executionList.isEmpty())
            return Collections.emptyList();

        return (List<DebtResponseCourtSolution>) session.createSQLQuery(
                        " select  fs.FILE_NAME||'.'||fs.FILE_EXT AS fileName, " +
                                "        fs.FILE_PATH||'.'||fs.FILE_EXT AS filePath, " +
                                "        fs.DESCRIPTION AS description " +
                                " from task_document td " +
                                "         JOIN FILE_STORAGE fs ON fs.id = td.FSID " +
                                " WHERE td.EXECUTION_ID in ( :executionList ) " +
                                "  AND td.DELETE_DATE IS null " )
                .addScalar("fileName", StandardBasicTypes.STRING)
                .addScalar("filePath", StandardBasicTypes.STRING)
                .addScalar("description", StandardBasicTypes.STRING)
                .setResultTransformer(Transformers.aliasToBean(DebtResponseCourtSolution.class))
                .setParameterList("executionList", executionList)
                .list();
    }


    private List<Map<String, Object>> findDebtorList(final List<String> flsList, final Date startDate, final Date endDate) throws Exception {
        Session pmSession = null;
        try {
            pmSession = pmSessionFactory.openSession();
            if(!flsList.isEmpty()) {
                final List<Map<String, Object>> result = new ArrayList<>();
                final List<Map<String, Object>> debtorList = pmSession.createSQLQuery(
                                " SELECT DISTINCT  " +
                                        " pd.id AS debtorId, " +
                                        " nvl(pd.name,'не указано') as debtorFio " +
                                        " FROM PIR_FLS pf  " +
                                        " JOIN PIR_OWNER_INFO oi ON oi.FLS_ID = pf.ID  " +
                                        " JOIN PIR_DEBTOR pd ON pd.id = oi.DEBTOR_ID " +
                                        " WHERE pf.FULLNUM IN (:flsList) ")
                        .addScalar("debtorFio", StandardBasicTypes.STRING)
                        .addScalar("debtorId", StandardBasicTypes.LONG)
                        .setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE)
                        .setParameterList("flsList", flsList)
                        .list();

                for (Map<String, Object> map : debtorList){
                    final Long debtorId = (Long) map.get("debtorId");
                    final List<Map<String, Object>> executionList = pmSession.createSQLQuery(
                                    "SELECT DISTINCT  " +
                                            " act.EXECUTION_ID_ as executionId  " +
                                            " from PIR_LAWSUIT pl  " +
                                            " JOIN PIR_UNION pu ON pu.id = pl.UNION_ID  " +
                                            " JOIN ACT_RU_TASK act ON pu.\"INSTANCE\" = act.EXECUTION_ID_  " +
                                            "    AND act.TASK_DEF_KEY_ = 'desicionCourOrder' " +
                                            " WHERE pl.DEBTOR_ID = :debtorId  " +
                                            "    AND pu.INIT_PERIOD BETWEEN :startDate AND :endDate ")
                            .addScalar("executionId", StandardBasicTypes.STRING)
                            .setLong("debtorId", debtorId)
                            .setDate("startDate", startDate)
                            .setDate("endDate", endDate)
                            .list();

                    final Map<String, Object> debtorData = new HashMap<>();
                    debtorData.put("debtorFio", map.get("debtorFio"));
                    debtorData.put("executionList", executionList);

                    result.add(debtorData);
                }

                return result;
            } else
                return Collections.emptyList();
        } finally {
            SomeUtils.close(pmSession);
        }
    }

    private List<Map<String, Object>> findFlsList(final Session session, final DebtRequest request) {
        final Query query = session.createSQLQuery(
                        "   select nvl(sum(d.balance), 0) as debtSum, f.fullnum as flsNumber " +
                                "   from fias_house fh " +
                                "             join house h on h.fias_house = fh.fiasid   " +
                                "             join house_addr ha on ha.addr = h.addr   " +
                                "             join fls f on f.house = h.addr   " +
                                "             join fls_flat ff on ff.fls = f.id   " +
                                "             join flat fl on fl.addr = ff.flat   " +
                                (request.flatNumber != null ? " and fl.nomer = :flatNumber " : "") +
                                "             left join debt d on d.fls = f.id " +
                                "               and d.period between :startDate and :endDate " +
                                " where fh.houseguid = :fiasGuid " +
                                " group by f.fullnum " )
                .addScalar("flsNumber", StandardBasicTypes.STRING)
                .addScalar("debtSum", StandardBasicTypes.BIG_DECIMAL)
                .setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE)
                .setDate("startDate", request.startDate)
                .setDate("endDate", request.endDate)
                .setString("fiasGuid", request.houseGuid);

        if (request.flatNumber != null)
            query.setString("flatNumber", request.flatNumber);

        return (List<Map<String, Object>>) query.list();
    }

}
