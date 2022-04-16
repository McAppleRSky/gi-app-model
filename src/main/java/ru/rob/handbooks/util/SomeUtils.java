package ru.rob.handbooks.util;

import com.google.gson.Gson;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

//import ru.git.gkh.core.UserSession;

public class SomeUtils {
    private static final Logger LOG = LoggerFactory.getLogger(SomeUtils.class);

    private static final DecimalFormat CURRENCY_DECIMAL_FORMAT = new DecimalFormat();
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat();
    private static final DecimalFormat DECIMAL_EXCEL_FORMAT = new DecimalFormat();
    public static final double MIN_CURRENCY = 0.00001;

    public static final Gson GSON = new Gson();

    static {
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
        decimalFormatSymbols.setDecimalSeparator('.');
        decimalFormatSymbols.setGroupingSeparator(' ');
        CURRENCY_DECIMAL_FORMAT.setDecimalFormatSymbols(decimalFormatSymbols);
        CURRENCY_DECIMAL_FORMAT.setGroupingSize(3);
        CURRENCY_DECIMAL_FORMAT.setMinimumFractionDigits(2);
        CURRENCY_DECIMAL_FORMAT.setMaximumFractionDigits(2);
        CURRENCY_DECIMAL_FORMAT.setMaximumIntegerDigits(18);

        DECIMAL_FORMAT.setDecimalFormatSymbols(decimalFormatSymbols);
        DECIMAL_FORMAT.setGroupingSize(3);
        DECIMAL_FORMAT.setMinimumFractionDigits(0);
        DECIMAL_FORMAT.setMaximumFractionDigits(18);
        DECIMAL_FORMAT.setMaximumIntegerDigits(18);

        DECIMAL_EXCEL_FORMAT.setDecimalFormatSymbols(decimalFormatSymbols);
        DECIMAL_EXCEL_FORMAT.setGroupingUsed(false);
        DECIMAL_EXCEL_FORMAT.setMinimumFractionDigits(0);
        DECIMAL_EXCEL_FORMAT.setMaximumFractionDigits(18);
        DECIMAL_EXCEL_FORMAT.setMaximumIntegerDigits(18);
    }

    private static final char[] charsL = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
            'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z'};
    private static final char[] charsU = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
            'U', 'V', 'W', 'X', 'Y', 'Z'};
    private static final char[] charsD = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    private static final char[][] chars = {charsL, charsU, charsD};

    /*public static void checkUser(final Session session, final Long id, final String exception) throws Exception{
        if (extractUser(session, id) == null)
            throw new Exception(exception);
    }

    public static Usr extractUser(Session session, SessionContext ctx) {
        return extractUser(session, ctx.getCallerPrincipal().getName());
    }

    public static Usr extractUser(Session session, String name) {
        Criteria usrCriteria = session.createCriteria(Principal.class);
        usrCriteria = usrCriteria.add(Restrictions.eq("name", name));
        Principal p = (Principal) usrCriteria.uniqueResult();
        return (Usr) p; //session.byNaturalId(Usr.class).using("name",name).load();
    }

    public static Usr extractUser(Session session, Long usrId) {

        return (Usr) session.get(Usr.class, usrId);
    }

    public static Usr extractUserOrAnonymous(Session session, SessionContext ctx) {
        return extractUserOrAnonymous(session, ctx.getCallerPrincipal().getName());
    }

    public static Usr extractUserOrAnonymous(Session session, String userName) {
        Usr result = extractUser(session, userName);
        if (result == null) {
            LOG.warn("User with name {} not found, getting ANONYMOUS user", userName);
            result = extractUser(session, "ANONYMOUS");
            if (result == null) {
                LOG.warn("User with name {} not found, and ANONYMOUS user not found too", userName);
                throw new RuntimeException("User with name " + userName + " not found, and ANONYMOUS user not found too");
            }
        }
        return result;
    }

    public static void putUserAddr(Session s, Principal principal) throws Exception {

    }

	*//*
    @Deprecated
    public static UserSession getUserSession(HttpServletRequest request)
			throws NoUserSessionException {

		HttpSession httpSession = request.getSession(false);
		if (httpSession == null)
			throw new NoUserSessionException("No HttpSession");
		UserSession userSession = (UserSession) httpSession
				.getAttribute("UserSession");
		if (userSession == null)
			throw new NoUserSessionException("No UserSession");

		return userSession;
	}
    *//*

    *//**
     * Возвращает sseHandler для данной сессии.
     *
     * @param sessionId - id сессии httpServletRequest.getSession().getId()
     *//*
    public static GlobalHandler getSessionSSEHandler(String sessionId,
                                                     ServerSentEventHandlerContext<GlobalHandler> sseContext) {
        for (GlobalHandler handler : sseContext.getHandlers()) {
            String handlerSessionId = handler.getSessionId();
            if (handlerSessionId != null && sessionId != null && handlerSessionId.equals(sessionId)) {
                return handler;
            }
        }
        return null;
    }

    *//**
     * Возвращает sseHandler для данной сессии.
     * id сессии берется из ThreadLocalContextHolder.get("sessionId").
     * Вобщем все равно какой метод использовать, этот или предыдущий.
     * То, что для одного нужен httpServletRequest.getSession().getId(), а для другого httpServletRequest.getRequestedSessionId()
     * сложилось исторически, и принципиального значения не имеет.
     * При использовании этого метода надо быть уверенным, что для потока задан sessionId в ThreadLocalContextHolder
     * (Для всех запросов из клиента он задается в UserSessionFilter)
     *//*
    public static GlobalHandler getSessionSSEHandler(ServerSentEventHandlerContext<GlobalHandler> sseContext) {
        String sessionId = (String) ThreadLocalContextHolder.get("sessionId");
        for (GlobalHandler handler : sseContext.getHandlers()) {
            // В UserSessionFilter в ThreadLocalContextHolder кладется httpServletRequest.getRequestedSessionId()
            String handlerSessionId = handler.getRequestedSessionId();
            if (handlerSessionId != null && sessionId != null && handlerSessionId.equals(sessionId)) {
                return handler;
            }
        }
        return null;
    }

    public static String concat(String text, String item) {
        if (text == null) text = "";
        return text + ((text.length() > 0) ? ", " : "") + item;
    }

    *//**
     * Проверяет наличие хотя бы одной из ролей
     *//*
    public static void checkOneRole(SessionContext ctx, String roles) throws Exception {

        String res = "";

        String[] rolesArr = roles.split(",");

        for (String role : rolesArr) {
            if (ctx.isCallerInRole(role.trim()))
                return;
            else
                res = concat(res, role);
        }

        if (!"".equals(res))
            throwEx(null, "для выполнения указанного метода необходима одна из ролей: " + res);

    }


    *//**
     * Проверяет наличие всех ролей
     *//*
    public static void checkRoles(SessionContext ctx, String roles) throws Exception {

        String res = "";

        String[] rolesArr = roles.split(",");

        for (String role : rolesArr) {
            if (ctx.isCallerInRole(role.trim()))
                continue;
//				return;
            else
                res = concat(res, role);
        }

        if (!"".equals(res))
            throwEx(null, "для выполнения указанного метода необходимы роли: " + res);

    }


    public static Boolean checkRoles(SessionContext ctx, String roles, Boolean exception) throws Exception {

        String res = "";
        String[] rolesArr = roles.split(",");

        for (String role : rolesArr) {
            if (ctx.isCallerInRole(role.trim())) {
                continue;
            } else {
                res = concat(res, role);
            }
        }

        if (!"".equals(res)) {

            if (exception)
                throw new Exception("для выполнения операции нужна одна из ролей: " + res);
            return Boolean.FALSE;

        } else {
            return Boolean.TRUE;
        }

    }

    *//**
     * Проверка роли через список principal_role.
     * ! Iспользовать только для динамически добавляемых ролей, название которых не известно.
     * Для заранее определённых ролей iспользвать checkRoles(SessionContext ctx, String roles).
     *
     * @param usr
     * @param role
     * @throws Exception
     *//*
    public static void checkRole(Usr usr, Role role) throws Exception {

        if (usr == null || role == null) {
            throw new Exception("checkRole - некорректные входные параметры");
        }

        if (usr.roleSet == null || usr.roleSet.isEmpty()) {
            throw new Exception("пользователю " + usr.getCaption() + " не заданы роли!");
        }

        if (!usr.roleSet.contains(role)) {
            throw new Exception("для выполнения операции нужна роль: " + role.name);
        }
    }

    *//**
     * Iспользуется для проверки роли пользователя при отображении спiска отчетов КР,
     * чтобы не заводiть все отчетные роля в CapRemontBean
     *//*
    public static Boolean hasRole(Session session, Usr usr, String roleName) throws Exception {

        if (usr == null || roleName == null) {
            return false;
        }

        if (usr.roleSet == null || usr.roleSet.isEmpty()) {
            return false;
        }

        Role role = (Role) session.createQuery("from Role where name = :roleName").setText("roleName", roleName).uniqueResult();
        if (role == null) {
            return false;
        }

        return usr.getUserRoles().contains(role);
    }

    public static void throwEx(String title, String comm) throws Exception {
        String mess = comm;
        if (title != null) mess += "(" + title + ")";
        throw new Exception(mess);
    }

    public static String toLine(Collection<String> keys) {
        keys.remove(null);
        List<String> workList = new ArrayList<>(keys);
        Collections.sort(workList);
        StringBuilder builder = new StringBuilder();
        int i = 0;
        for (String key : keys) {
            if (i > 10) {
                builder.append("...");
                break;
            } else {
                i++;
            }
            if (builder.length() > 0)
                builder.append("</br>");
            builder.append(key);
        }

        return builder.toString();
    }

    public static String toLineMoney(Set<BigDecimal> keys) {
        List<BigDecimal> workList = new ArrayList<>(keys);
        Collections.sort(workList);
        StringBuilder builder = new StringBuilder();
        int i = 0;
        for (BigDecimal key : keys) {
            if (i > 10) {
                builder.append("...");
                break;
            } else {
                i++;
            }
            if (builder.length() > 0)
                builder.append("</br>");
            builder.append(Numerals.formatMoney(key));
        }

        return builder.toString();
    }

    public static String toLineDate(Set<Date> keys) {
        List<Date> workList = new ArrayList<>(keys);
        Collections.sort(workList);
        StringBuilder builder = new StringBuilder();
        int i = 0;
        for (Date key : keys) {
            if (i > 10) {
                builder.append("...");
                break;
            } else {
                i++;
            }
            if (builder.length() > 0)
                builder.append("</br>");
            builder.append(ru.rob.handbooks.SynchrDateFormat.formatDate(key));
        }

        return builder.toString();
    }

    public static String toLineMonth(Set<Date> keys) {
        List<Date> workList = new ArrayList<>(keys);
        Collections.sort(workList);
        StringBuilder builder = new StringBuilder();
        int i = 0;
        for (Date key : keys) {
            if (i > 10) {
                builder.append("...");
                break;
            } else {
                i++;
            }
            if (builder.length() > 0)
                builder.append("</br>");
            builder.append(ru.rob.handbooks.SynchrDateFormat.formatPeriod(key));
        }

        return builder.toString();
    }

    public static interface Consumer<T> {
        public void accept(T t);
    }

    public static interface Predicate<T> {
        public boolean filter(T t);
    }

    public static <T> T each(Iterable<T> iterable, Predicate<T> predicate) {
        for (T t : iterable) {
            if (predicate.filter(t)) return t;
        }
        return null;
    }

    public static <T> void each(Iterable<T> iterable, Consumer<T> consumer) {
        for (T t : iterable) {
            consumer.accept(t);
        }
    }

    public static Object getObject(Session session, Class c, Serializable id) throws Exception {
        Object res = session.get(c, id);
        if (res == null)
            throwEx(null, "не найден " + c.getName() + " . id= " + id);

        return res;
    }

    public static Date[] parseDates(HttpServletRequest request) throws Exception {

        Date[] res = new Date[2];

        @SuppressWarnings("unchecked")
        String operDateStr = JSPUtil.getParameter(request.getParameterMap(), "date");
        Date parOperDate = operDateStr != null ? ru.rob.handbooks.SynchrDateFormat.parseDate(operDateStr) : null;

        @SuppressWarnings("unchecked")
        String archDateStr = JSPUtil.getParameter(request.getParameterMap(), "archDate");
        Date parArchDate = archDateStr != null ? ru.rob.handbooks.SynchrDateFormat.parseDateTimeFloat(archDateStr) : null;

        Date sdDate = ru.rob.handbooks.SynchrDateFormat.removeTimeFromDate(new Date());
        Date archiveDate = new Date();
        if (parOperDate != null) {
            sdDate = parOperDate;
            archiveDate = parArchDate != null ? parArchDate : archiveDate;
        }

        res[0] = sdDate;
        res[1] = archiveDate;

        return res;

    }

    public static void checkParams(HttpServletRequest request, String params) throws Exception {
        String[] pars = params.split(",");
        String[] res = new String[pars.length];
        for (int i = 0; i < pars.length; i++) {
            res[i] = pars[i].trim();
        }

        checkParams(request, res);
    }

    public static void checkParams(HttpServletRequest request, String[] params) throws Exception {
        String res = "";
        for (String param : params) {
            if (request.getParameter(param) == null)
                res = res + ((res.length() > 0) ? ", " : "") + param;
        }

        if (res.length() > 0)
            throwEx(null, "не задан параметр :" + res);
    }

    public static void save(Session session, Object obj) {
        session.saveOrUpdate(obj);
        session.flush();
    }*/

    public static void close(Session session) {
        if (session != null && session.isOpen())
            session.close();
    }

/*    public static void rollback(Transaction transaction) {
        if (transaction != null && transaction.isActive())
            transaction.rollback();
    }

    public static void rollback(UserTransaction transaction) throws Exception {
        if (transaction != null)
            transaction.rollback();
    }


    public static String generatePassword(int size) {
        String password = "";
        Random random = new Random();
        if (size <= 0)
            size = random.nextInt(5) + 4;

        HashSet<Integer> set = new HashSet<Integer>();

        for (int i = 0; i < chars.length; i++)
            set.add(new Integer(i));

        for (int i = 0; i < size; i++) {
            //password = password + chars[random.nextInt(chars.length)];

            int s = random.nextInt(chars.length);

            Integer S = new Integer(s);
            if (size - i <= set.size() && !set.contains(S)) {
                Integer[] arr = new Integer[set.size()];
                set.toArray(arr);

                S = arr[random.nextInt(arr.length)];
                s = S.intValue();
            }
            set.remove(S);

            password = password +
                    chars[s][random.nextInt(chars[s].length)];
        }


        return password;
    }

    public static void close(Closeable closeable) {
        if (closeable != null)
            try {
                closeable.close();
            } catch (IOException e) {
            }
    }

    public static String formatName(String str) {
        String rslt;
        if (str == null) return str;
        rslt = str.trim();
        if (rslt.length() == 0) return null;
        if (rslt.length() == 1) {
            return rslt.toUpperCase();
        } else {
            //убираем подряд идущие пробелы
            rslt = rslt.replaceAll("[ ]+", " ");
            //проверка, что имя начинается с буквы
            Pattern russianLetter = Pattern.compile("^[А-Яа-яЁё]+.*");
            if (!russianLetter.matcher(rslt).matches()) {
                LOG.error("Поле должно начинаться с русской буквы {}", str);
                throw new RuntimeException("Поле должно начинаться с русской буквы");
            }
            return rslt.substring(0, 1).toUpperCase().concat(rslt.substring(1));
        }


    }

    public static final int max(int... values) {
        if (values.length == 0) {
            throw new IllegalArgumentException("Provide array of at least one number");
        }
        if (values.length == 1) {
            return values[0];
        }
        Arrays.sort(values);
        return values[values.length - 1];
    }

    *//**
     * Формат чисел для денег. Максимум 2 знака после запятой. Если число целое, добавляется 00 после запятой.
     *
     * @param number число
     * @return число в отформатированном виде.
     *//*
    public static String formatCurrency(Number number) {
        if (number == null) {
            return "";
        }
        if (Math.abs(number.doubleValue()) < MIN_CURRENCY)
            number = BigDecimal.ZERO;
        return CURRENCY_DECIMAL_FORMAT.format(number);
    }

    public static BigDecimal parseCurrency(String number) throws ParseException {
        return number == null ? BigDecimal.ZERO : new BigDecimal(CURRENCY_DECIMAL_FORMAT.parse(number).toString());
    }

    *//**
     * Формат чисел для остальных чисел. Максимум 18 знаков после запятой. Если число целое, дробная часть не отображается.
     *
     * @param number число
     * @return число в отформатированном виде.
     *//*
    public static String formatDecimal(Object number) {
        return number == null ? "" : DECIMAL_FORMAT.format(number);
    }

    *//**
     * Формат чисел для экспорта в Excel через Extjs. Максимум 18 знаков после запятой. Разделения групп разрядов нет
     *
     * @param number число
     * @return число в отформатированном виде.
     *//*
    public static String formatDecimalExcel(Object number) {
        return number == null ? "" : DECIMAL_EXCEL_FORMAT.format(number);
    }


    private static String DB_TYPE = null;

    *//**
     * Определяет тип используемой СУБД
     *
     * @return
     *//*
    public static final String getDbType() {
        Connection connection = null;
        if (DB_TYPE != null) {
            return DB_TYPE;
        }
        try {
            Context c = new InitialContext();
            DataSource ds = (DataSource) c.lookup("jdbc/gkh");
            connection = ds.getConnection();
            DB_TYPE = connection.getMetaData().getDatabaseProductName();
        } catch (Throwable t) {
            LOG.error("Problem getting db type");
            return null;
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
        return DB_TYPE;
    }

    public static final void createFilterTable(Session session) {
        Connection connection = null;
        try {
            if ("PostgreSQL".equals(getDbType())) {
                List rs = session.createSQLQuery("SELECT count(*) cnt FROM information_schema.tables WHERE table_type='LOCAL TEMPORARY' AND table_name='filter_table'")
                        .addScalar("cnt", StandardBasicTypes.LONG).list();
                long cnt = (Long) rs.get(0);

                if (cnt == 0) {
                    session.createSQLQuery("CREATE temp TABLE filter_table (     id BIGINT NOT NULL,     id_str VARCHAR(255) NOT NULL,     key VARCHAR(255) NOT NULL,     fd DATE,     id_long BIGINT,     sd DATE,     CONSTRAINT filter_table_pkey PRIMARY KEY (key, id) ) ON COMMIT PRESERVE ROWS")
                            .addSynchronizedEntityClass(FilterTable.class)
                            .executeUpdate();
                }
            }
        } catch (Throwable t) {
            LOG.error("Problem with db", t);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }

    }

    public static final String printParams(Map<String, String[]> params) {
        String result = "";
        for (String key : params.keySet()) {
            result = result + key + " -> " + Arrays.toString(params.get(key)) + " ";
        }
        return result;
    }

    *//**
     * Очистка всей временной таблицы по ключу.
     *
     * @param session
     *//*
    public static Integer clearTempTable(Session session) {
        return clearTempTable(session, null);
    }

    *//**
     * Очистка временной таблицы по ключу (like '[key]%').
     *
     * @param session
     * @param key     - ключ обязателен!.
     *//*
    public static Integer clearTempTableStartWithKey(Session session, String key) throws Exception {
        //createFilterTable(session);
        Query query;
        if (!StringUtils.isEmpty(key)) {
            query = session.createSQLQuery("delete from filter_table ft where key like :key || '%' ").addSynchronizedEntityClass(FilterTable.class).setString("key", key);
        } else {
            throw new GkhEjbException("filter table key is empty!");
        }
        Integer cnt = 0;
        if (query != null) {
            cnt = query.executeUpdate();
            session.flush();
        }

        return cnt;
    }

    *//**
     * Очистка временной таблицы по ключу.
     *
     * @param session
     * @param key     - если ключ null, то чистится вся таблица.
     *//*
    public static Integer clearTempTable(Session session, String key) {
        //createFilterTable(session);
        Query query;
        if (key != null) {
            query = session.createSQLQuery("delete from filter_table ft where key = :key").addSynchronizedEntityClass(FilterTable.class).setString("key", key);
        } else {
            query = session.createSQLQuery("delete from filter_table").addSynchronizedEntityClass(FilterTable.class);
        }
        Integer cnt = 0;
        if (query != null) {
            cnt = query.executeUpdate();
            session.flush();
        }

        return cnt;
    }

    *//**
     * Заполнение временной таблицы.
     *
     * @param session
     * @param iterator
     * @return
     * @throws Exception
     *//*
    public static String fillTempTable(Session session, Iterator iterator) throws Exception {

        //случайный ключ
        String key = GKHConstants.FTKEY_CALC_PENALTY + "tempKey" + SomeUtils.randInt(0, 30000) +
                "_" + System.currentTimeMillis();
        while (iterator.hasNext()) {
            Long value = null;
            try {
                value = new Long(iterator.next().toString());
            } catch (Exception e) {
                throw new Exception("некорректный формат данных - элементы массива должны быть числами!");
            }
            LOG.trace("TempTable: value: {}, key: {}", value, key);
            FilterTable filterTable = new FilterTable(value, key);
            session.save(filterTable);
        }

        session.flush();

        return key;

    }

    *//**
     * Заполнение временной таблицы.
     *
     * @param session
     * @param iterator
     * @return
     * @throws Exception
     *//*
    public static String fillTempTableWithStrId(Session session, Iterator iterator) throws Exception {

        //случайный ключ
        String key = "tempKey" + SomeUtils.randInt(0, 30000) + "_" + System.currentTimeMillis();
        while (iterator.hasNext()) {
            String value = null;
            value = iterator.next().toString();
            LOG.trace("TempTable: value: {}, key: {}", value, key);
            FilterTable filterTable = new FilterTable(value, key, 1L);
            session.save(filterTable);
        }

        session.flush();

        return key;

    }

    // Так же можно использовать ValListBean
    public static ValList getValList(Session s, Integer attrId, Integer vlId) {
        return (ValList) s.getNamedQuery("ValList.getBy(attrId,valListId)").setInteger("valListId", vlId).setInteger("attrId", attrId).uniqueResult();
    }

    *//**
     * Возвращает псевдослучайное число в промежутке между максимумом и минимумом.
     *
     * @param min - нижняя граница.
     * @param max - верхняя граница.
     * @return
     *//*
    public static int randInt(int min, int max) {

        if (max <= min) {
            throw new RuntimeException("верхняя граница должны быть строго больше нижней");
        }

        Random rand = new Random();

        //добавляем единицу, чтобы сделать включение верхнего значения
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

    *//**
     * Получение домов для заданного пользователя. Iспользуется в спiсках ПС.
     *
     * @param session  - сессия.
     * @param ovkId    - ID военкомата, по кт надо наложить фильтр. Может быть null.
     * @param userName - имя пользователя.
     * @return
     * @throws Exception
     *//*
    public static Collection<House> getUserAddrList(Session session, Integer ovkId, String userName) throws Exception {

        //получаем дома пользователя
        Usr usr = extractUser(session, userName);

        session.enableFilter("houseByUser").setParameter("u", usr.id);
        session.enableFilter("kladrByUser").setParameter("u", usr.id);

        //понятия не имею, как должен работать этот фильтр,
        // сделал так, чтобы он хотя не бросал NullPointerException (anikin_ma 21.09.2015)
        Query query = session.createQuery("select distinct h from House h " +
                (ovkId != null ? "join h.paramSet ps " +
                        "join ps.attr attr " +
                        "join ps.valRef valList " +
                        "where attr.id = :ovkAttr " +
                        "and valList.id = :ovk " : ""));
        if (ovkId != null) {
            query.setInteger("ovkAttr", ATTR_PASSPORT_VOENKOM).setInteger("ovk", ovkId);
        }

        return query.list();
    }


    *//***
     * Получение списка всех ролей в системе
     *
     * @param session
     * @return HashMap<String, Role> список всех ролей в системе
     *//*

    public static HashMap<String, Role> getRoles(Session session) {
        HashMap<String, Role> result = new HashMap<String, Role>();

        List<Role> roleList = session.createQuery("from Role r").list();

        for (Role role : roleList) {
            result.put(role.name, role);
        }

        return result;
    }

    *//**
     * Клонирование InputStream.
     *
     * @param inputStream
     * @return
     * @throws IOException
     *//*
    public static InputStream cloneStream(InputStream inputStream) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = inputStream.read(buffer)) > -1) {
            baos.write(buffer, 0, len);
        }
        baos.flush();

        return new ByteArrayInputStream(baos.toByteArray());
    }

    *//**
     * Получение ByteArrayOutputStream для повторного использования InputStream.
     *
     * @param inputStream
     * @return
     * @throws IOException
     *//*
    public static ByteArrayOutputStream getBaosFromIs(InputStream inputStream) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = inputStream.read(buffer)) > -1) {
            baos.write(buffer, 0, len);
        }
        baos.flush();

        return baos;
    }

    public static String jarForClass(Class klass) {
        try {
            File f = new File(klass.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
            LOG.debug("file klass {}", f.getAbsolutePath());
            URL location = klass.getResource('/' + klass.getName().replace('.', '/') + ".class");
            return location.getFile();
        } catch (Throwable t) {
            LOG.error("Got throwable {}, return null", t.getMessage(), t);
        }
        return null;
    }

    public static String jarForClass(String className) {
        Class klass = null;
        try {
            klass = Class.forName(className);
            File f = new File(klass.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
            LOG.debug("file klass {}", f.getAbsolutePath());
            URL location = klass.getResource('/' + klass.getName().replace('.', '/') + ".class");
            return location.getFile();
        } catch (ClassNotFoundException e) {
            LOG.error("Class {} not found, return null", className);
        } catch (Throwable t) {
            LOG.error("Got throwable {}, return null", t.getMessage(), t);
        }
        return null;
    }


    public static String getMessage(Throwable ex) {
        String message = "";

        if (ex instanceof EJBException) {
            int steps = 0;
            while ((ex.getCause() != null) && (steps++ < 100)) {
                ex = ex.getCause();
            }
        }

        if (ex instanceof ConstraintViolationException) {
            ConstraintViolationException cve = (ConstraintViolationException) ex;
            Set<ConstraintViolation<?>> cvs = cve.getConstraintViolations();
            message = "";
            for (ConstraintViolation cv : cvs) {
                message += String.format("- %s <br>", cv.getMessage());
            }
            return message;
        }

        message = ex instanceof EJBException && ex.getCause() != null
                ? ex.getCause().toString() : ex.getMessage();

        if (message == null) {
            message = ex.toString();
        }

        return message;
    }

    *//**
     * Возвращает почтовый индекс с дома, или улицы, или населенного пункта дома ФIАС
     *
     * @param fiasHouse - дом в ФIАС
     *//*
    public static String getFiasPostalCode(FiasHouse fiasHouse) throws Exception {
        if (fiasHouse == null) return null;
        if (!StringUtils.isEmpty(fiasHouse.postalcode)) return fiasHouse.postalcode;
        if (fiasHouse.addrstreet != null && !StringUtils.isEmpty(fiasHouse.addrstreet.postalcode))
            return fiasHouse.addrstreet.postalcode;
        if (fiasHouse.addrobj != null && !StringUtils.isEmpty(fiasHouse.addrobj.postalcode))
            return fiasHouse.addrobj.postalcode;

        return null;
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> getListFromMap(Map map, String listId) {
        List<T> list = new ArrayList<>();
        Object obj = map.get(listId);
        if (!ValidateUtils.checkNull(obj)) {
            list = (List) obj;
        }
        return list;
    }

    @SuppressWarnings("unchecked")
    public static String getStringFromMap(Map map, String strId) {
        String str = "";
        Object obj = map.get(strId);
        if (!ValidateUtils.checkNull(obj)) {
            str = (String) obj;
        }
        return str;
    }

    @SuppressWarnings("unchecked")
    public static Long getLongFromMap(Map map, String longId) {
        Long l = 0L;
        Object obj = map.get(longId);
        if (!ValidateUtils.checkNull(obj)) {
            l = (Long) obj;
        }
        return l;
    }

    @SuppressWarnings("unchecked")
    public static Long getLongFromMapDef(Map map, String longId, Long def) {
        Long l = def;
        Object obj = map.get(longId);
        if (obj == null) return l;
        if (!StringUtils.isEmpty(obj.toString())) {
            l = Long.decode(obj.toString());
        }
        return l;
    }

    @SuppressWarnings("unchecked")
    public static Integer getIntegerFromMapDef(Map map, String integerId, Integer def) {
        Integer i = def;
        Object obj = map.get(integerId);
        if (obj == null) return i;
        if (!StringUtils.isEmpty(obj.toString())) {
            i = Integer.decode(obj.toString());
        }
        return i;
    }

    public static void checkInterrupted() throws Exception {
        if (Thread.currentThread().isInterrupted()) {
            throw new Exception(String.format(" Поток %s был остановлен пользователем", Thread.currentThread().getId()));
        }
    }

    public static Map<String, String> getReportTemplateMd5Hex(ReportTemplate reportTemplate) {

        Map<String, String> res = new HashMap<>();
        String md5Script = null;
        String md5Template = null;

        try {
            if (reportTemplate.template != null) {
                Long length = reportTemplate.template.length();
                md5Template = DigestUtils.md5Hex(reportTemplate.template.getBytes(1L, length.intValue()));
            }
            if (reportTemplate.text != null) {
                md5Script = DigestUtils.md5Hex(reportTemplate.text.trim());
            }
        } catch (Exception e) {
            res.put("md5s", null);
            res.put("md5t", null);
            return res;
        }

        res.put("md5s", md5Script);
        res.put("md5t", md5Template);

        return res;
    }

    *//**
     * Понятное для пользователя название Boolean
     *//*
    public static String sayYes(boolean yes) {
        return yes ? "ДА" : "НЕТ";
    }

    *//**
     * Получение роли для отчета
     *//*
    public static String getReportRole(Session session, String reportName) {

        HashMap<String, Role> roles = getRoles(session);
        String role = "report." + (reportName == null ? "undefined" : reportName.toLowerCase());
        if (!roles.containsKey(role)) role = "view.templates";
        return role;
    }

    public static Boolean isFlsGroupReport(SessionContext ctx, Session session, String reportName) throws Exception {

        if (StringUtils.isEmpty(reportName)) return false;
        reportName = reportName.toLowerCase();

        if ("housesbyfls".equals(reportName)) return true;
        if ("nach_fls".equals(reportName)) return true;
        if ("nach".equals(reportName)) return true;
        if ("sheetdefaulters".equals(reportName)) return true;
        if ("nach_payment_management_fls".equals(reportName)) return true;
        if ("munfls".equals(reportName)) return true;
        if ("cr_saldo_supernova_fls".equals(reportName)) return true;
        if ("cr_payment_fls".equals(reportName)) return true;
        if ("crNachFls".equals(reportName)) return true;
        if ("cr_defaulters_fls".equals(reportName)) return true;
        if ("cr_nach_payment_fls".equals(reportName)) return true;
        if ("ro_nach_fls".equals(reportName)) return true;
        if ("ro_saldo_fls".equals(reportName)) return true;
        if ("ro_saldo_fls_serv".equals(reportName)) return true;
        if ("ticketregister".equals(reportName)) return true;
        if ("paymentregister".equals(reportName)) return true;
        if ("reestr".equals(reportName)) return true;
        if ("paymentsfortransfer".equals(reportName)) return true;
        if ("epdmainlist".equals(reportName)) return true;
        if ("epdmainlist900".equals(reportName)) return true;
        if ("nachcollectionpercentmo".equalsIgnoreCase(reportName)) return true;

        return false;
    }

    public static boolean getSystemOption(Session session, int attrId) {
        BigDecimal option = (BigDecimal) session.createQuery("" +
                        "select sp.valNum from SysParam sp " +
                        "join sp.attr attr " +
                        "where attr.id = :needCalcPenalty " +
                        "and :archDate between sp.svd and sp.fvd " +
                        "order by sp.sd").
                setInteger("needCalcPenalty", attrId).
                setTimestamp("archDate", new Date()).uniqueResult();

        return option != null && option.compareTo(BigDecimal.ZERO) != 0;
    }

    public static boolean getSystemOptionBoolBySysKey(Session session, String syKey) {
        SysParam option = (SysParam) session.createQuery("" +
                        " select sp " +
                        " from SysParam sp " +
                        " join sp.attr attr " +
                        " where attr.sysKey = :sysKey " +
                        " and :archDate between sp.svd and sp.fvd " +
                        " order by sp.sd").
                setParameter("sysKey", syKey).
                setTimestamp("archDate", new Date())
                .uniqueResult();

        return option != null ? option.valBool() : false;
    }

    public static boolean getSystemOptionDef(Session session, int attrId, boolean def) {
        BigDecimal option = (BigDecimal) session.createQuery("" +
                        "select sp.valNum from SysParam sp " +
                        "join sp.attr attr " +
                        "where attr.id = :needCalcPenalty " +
                        "and :archDate between sp.svd and sp.fvd " +
                        "order by sp.sd").
                setInteger("needCalcPenalty", attrId).
                setTimestamp("archDate", new Date())
                .uniqueResult();

        return option != null ? option.compareTo(BigDecimal.ZERO) != 0 : def;
    }

    public static boolean getSystemOptionWithCache(Session session, int attrId) {
        //TODO метод добавлен временно пока не изменили расчет пеней
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.set(Calendar.MILLISECOND, 0);
        SysParam sp = (SysParam) session.createQuery("" +
                        "select sp from SysParam sp " +
                        "join sp.attr attr " +
                        "where attr.id = :needCalcPenalty " +
                        "and :archDate between sp.svd and sp.fvd " +
                        "order by sp.sd").
                setInteger("needCalcPenalty", attrId).
                setTimestamp("archDate", c.getTime())
                .setCacheRegion("epd-query-cache")
                .setCacheable(true)
                .uniqueResult();

        BigDecimal option = sp != null ? sp.valNum : null;

        return option != null && option.compareTo(BigDecimal.ZERO) != 0;
    }

    public static boolean getSystemOptionDefWithCache(Session session, int attrId, boolean def) {
        //TODO метод добавлен временно пока не изменили расчет пеней
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.set(Calendar.MILLISECOND, 0);
        SysParam sp = (SysParam) session.createQuery("" +
                        "select sp from SysParam sp " +
                        "join sp.attr attr " +
                        "where attr.id = :needCalcPenalty " +
                        "and :archDate between sp.svd and sp.fvd " +
                        "order by sp.sd").
                setInteger("needCalcPenalty", attrId).
                setTimestamp("archDate", c.getTime())
                .setCacheRegion("epd-query-cache")
                .setCacheable(true)
                .uniqueResult();
        BigDecimal option = sp != null ? sp.valNum : null;

        return option != null ? option.compareTo(BigDecimal.ZERO) != 0 : def;
    }

    public static int getSystemOptionInt(Session session, int attrId, int def) {
        BigDecimal option = (BigDecimal) session.createQuery("" +
                        "select sp.valNum from SysParam sp " +
                        "join sp.attr attr " +
                        "where attr.id = :attrId " +
                        "and :archDate between sp.svd and sp.fvd " +
                        "order by sp.sd").
                setInteger("attrId", attrId).
                setTimestamp("archDate", new Date()).uniqueResult();

        return option != null ? option.intValue() : def;
    }

    public static int getSystemOptionRef(Session session, int attrId, int def) {
        Integer option = (Integer) session.createQuery("" +
                        "select ref.id from SysParam sp join sp.valRef ref " +
                        "where sp.attrId = :attrId " +
                        "and :archDate between sp.svd and sp.fvd " +
                        "order by sp.sd").
                setInteger("attrId", attrId).
                setTimestamp("archDate", new Date()).uniqueResult();

        return option != null ? option : def;
    }

    public static Date getSystemOptionDate(Session session, int attrId, Date operDate) {
        Date option = (Date) session.createQuery("" +
                        "select sp.valDate from SysParam sp " +
                        "join sp.attr attr " +
                        "where attr.id = :attrId " +
                        "and :archDate between sp.svd and sp.fvd " +
                        "and :operDate between sp.sd and sp.fd " +
                        "order by sp.sd").
                setInteger("attrId", attrId).
                setTimestamp("archDate", new Date()).setTimestamp("operDate", operDate).uniqueResult();

        return option;
    }

    public static Date getSystemOptionDateBySysKey(Session session, String syKey) {

        Date archDate = new Date();

        Date option = (Date) session.createQuery("" +
                        " select sp.valDate " +
                        " from SysParam sp " +
                        " join sp.attr attr " +
                        " where attr.sysKey = :sysKey " +
                        " and :archDate between sp.svd and sp.fvd " +
                        " and :operDate between sp.sd and sp.fd "
                )
                .setString("sysKey", syKey)
                .setTimestamp("archDate", archDate)
                .setDate("operDate", archDate)
                .uniqueResult();

        return option;
    }

    public static String getSystemOptionStrBySysKey(Session session, String syKey) {
        Date archDate = new Date();
        return (String) session.createQuery(
                        " select sp.valStr " +
                                " from SysParam sp " +
                                " join sp.attr attr " +
                                " where attr.sysKey = :sysKey " +
                                " and :archDate between sp.svd and sp.fvd " +
                                " and :operDate between sp.sd and sp.fd " )
                .setString("sysKey", syKey)
                .setTimestamp("archDate", archDate)
                .setDate("operDate", archDate)
                .uniqueResult();
    }

    public static void writeProtocolMsg(OutputStream protocolStream, String msg) {
        if (checkNull(protocolStream)) return;
        try {
            protocolStream.write((SynchrDateFormat.formatDateTime(new Date()) + ": " + msg + "\n").getBytes(Charset.forName("windows-1251")));
        } catch (IOException e) {
            LOG.error("ошибка при записи в файл проткола ", e);
        }
    }

    public static void deleteFromTablesByParams(Session session, List<Map<String, Object>> paramList, String... tables) {
        for(Map<String, Object> params : paramList)
            if(params != null && !params.isEmpty())
                for(String table : tables){
                    if(Strings.isEmptyOrNull(table))
                        continue;
                    StringBuilder buildParam = new StringBuilder();
                    for(Map.Entry<String, Object>  param : params.entrySet())
                        buildParam.append((buildParam.length() == 0 ? " where " : " and ").concat(param.getKey()).concat("=:").concat(param.getKey().concat("Param")));
                    Query query = session.createSQLQuery(" delete from ".concat(table).concat(buildParam.toString()));
                    for(Map.Entry<String, Object>  param : params.entrySet())
                        query.setParameter(param.getKey().concat("Param"), param.getValue());
                    query.executeUpdate();
                }
    }

    public static Response buildResponseFile(String fileName, byte[] content) {
        Response.ResponseBuilder response = Response.ok(content);
        response.header("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
        response.header("Accept-Ranges", "bytes");
        response.header("Content-Length", content.length);
        return response.build();

    }

    public static void updateTask(final Task task, final String progress, final String message){
        if(task != null) task.updateTask(progress, message);
    }*/

}
