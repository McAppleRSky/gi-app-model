package ru.rob.handbooks;

import org.hibernate.*;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.springframework.stereotype.Component;
import ru.rob.handbooks.entity.DebtRequest;
import ru.rob.handbooks.entity.DebtResponse;
import ru.rob.handbooks.entity.DebtResponseCourtSolution;
import ru.rob.handbooks.entity.DebtResponseDetail;
import ru.rob.handbooks.enums.DebtResponseStatus;
import ru.rob.handbooks.util.SomeUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class DebtRequestBeanImpl implements DebtRequestBean{

    private static final Logger LOGGER = LoggerFactory.getLogger(DebtRequestBeanImpl.class);

//    private FormResponsesTask task;

//    @Resource(lookup = "concurrent/Common")
//    private ManagedExecutorService managedExecutorService;

//    @Inject
//    @ru.git.gkh.SessionFactory(SessionFactoryType.GKH)
//    private SessionFactory gkhSessionFactory;
@PersistenceContext
private EntityManager entityManager;

//    @Inject
//    @ru.git.gkh.SessionFactory(SessionFactoryType.PM)
//    private SessionFactory pmSessionFactory;

//    @Resource
//    private SessionContext ctx;

    /*public DebtRequestPage getRequestList(final DebtRequestFilterDto filter){
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
    }*/

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

    /*public DebtRequestView getRequestItem(final String id){
        final Session session = //gkhSessionFactory.openSession();
        try {
            final DebtRequest request = (DebtRequest) session
                    .createCriteria(DebtRequest.class)
                    .add(Restrictions.idEq(id))
                    .uniqueResult();
            return DebtRequestView.from(request);
        } finally {
            SomeUtils.close(session);
        }
    }*/
    /*public void startFormResponsesTask(final DebtRequestFilterDto filter){
        final TaskObserver observer = TaskObserverFactory.getTaskObserver();
        final Long taskId = System.currentTimeMillis();
        final String description = "Формирование ответов";
        final String user = ctx.getCallerPrincipal().getName();
        final TaskParams params = new TaskParams(gkhSessionFactory, observer, taskId, description, user);
        params.put("filter", filter);
        task = new FormResponsesTask(params, this);
        managedExecutorService.submit(task);
    }*/

    public int formResponses(final DebtRequestFilterDto filter) throws Exception {
        Session gkhSession = null;
        try {
            int formedResponsesCount = 0;
            gkhSession = //gkhSessionFactory.openSession();
            //Session session =
                    (Session) entityManager.getDelegate();

            final List<DebtRequest> requests = createCriteria(
                    DebtRequest.class,
                    gkhSession,
                    filter,
                    true
            ).list();

            int progress = 0;
            for(int i = 0; i < requests.size(); i++) {
                //progress = task.updateProgress(i, requests.size(), 10, progress);
                final DebtRequest request = requests.get(i);
                if(request.response == null)
                    try {
                        /*final Usr user = SomeUtils.extractUser(gkhSession, ctx);
                        final GisUser gisUser = (GisUser) gkhSession.get(GisUser.class, user.getId());
                        if(gisUser == null)
                            throw new Exception("У пользователя \"".concat(user.getCaption() + "\" не добавлен идентификатор ГIС"));*/

                        request.response = new DebtResponse();
//                        request.response.executorFio = gisUser.fio;
//                        request.response.executorGuid = gisUser.userGuid;

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
                        LOGGER.error(e.getMessage(), e);
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

        return (List<DebtResponseCourtSolution>) session
                .createSQLQuery("" +
                        "  SELECT fs.FILE_NAME||'.'||fs.FILE_EXT AS fileName, " +
                        "         fs.FILE_PATH||'.'||fs.FILE_EXT AS filePath, " +
                        "         fs.DESCRIPTION AS description " +
                        "    FROM task_document td JOIN FILE_STORAGE fs ON fs.id = td.FSID " +
                        "   WHERE td.EXECUTION_ID IN ( :executionList )" +
                        "         AND td.DELETE_DATE IS null ")
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
            pmSession = //pmSessionFactory.openSession();
            (Session) entityManager.getDelegate();

            if(!flsList.isEmpty()) {
                final List<Map<String, Object>> result = new ArrayList<>();
                final List<Map<String, Object>> debtorList = pmSession
                        .createSQLQuery("" +
                                "SELECT DISTINCT pd.id AS debtorId, " +
                                "                nvl(pd.name,'не указано') as debtorFio " +
                                "           FROM PIR_FLS pf JOIN PIR_OWNER_INFO oi ON oi.FLS_ID = pf.ID " +
                                "                JOIN PIR_DEBTOR pd ON pd.id = oi.DEBTOR_ID " +
                                "          WHERE pf.FULLNUM IN (:flsList) ")
                        .addScalar("debtorFio", StandardBasicTypes.STRING)
                        .addScalar("debtorId", StandardBasicTypes.LONG)
                        .setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE)
                        .setParameterList("flsList", flsList)
                        .list();

                for (Map<String, Object> map : debtorList){
                    final Long debtorId = (Long) map.get("debtorId");
                    final List<Map<String, Object>> executionList = pmSession
                            .createSQLQuery("" +
                                    "SELECT DISTINCT act.EXECUTION_ID_ as executionId  " +
                                    "           from PIR_LAWSUIT pl JOIN PIR_UNION pu ON pu.id = pl.UNION_ID  " +
                                    "                JOIN ACT_RU_TASK act ON pu.\"INSTANCE\" = act.EXECUTION_ID_  " +
                                    "                AND act.TASK_DEF_KEY_ = 'desicionCourOrder' " +
                                    "          WHERE pl.DEBTOR_ID = :debtorId  " +
                                    "                AND pu.INIT_PERIOD BETWEEN :startDate AND :endDate ")
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
        final Query query = session
                .createSQLQuery("" +
                        "  SELECT nvl(sum(d.balance), 0) AS debtSum, f.fullnum AS flsNumber " +
                        "    FROM fias_house fh JOIN house h ON h.fias_house = fh.fiasid " +
                        "         JOIN house_addr ha ON ha.addr = h.addr " +
                        "         JOIN fls f ON f.house = h.addr " +
                        "         JOIN fls_flat ff ON ff.fls = f.id " +
                        "         JOIN flat fl ON fl.addr = ff.flat " +
                        (request.flatNumber != null ? " AND fl.nomer = :flatNumber " : "") +
                        "         LEFT JOIN debt d ON d.fls = f.id " +
                        "         AND d.period between :startDate " +
                        "         AND :endDate " +
                        "   WHERE fh.houseguid = :fiasGuid " +
                        "GROUP BY f.fullnum " )
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
