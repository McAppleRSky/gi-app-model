package ru.rob.handbooks.util;

import org.json.JSONObject;
import ru.rob.handbooks.SynchrDateFormat;
//import org.mozilla.universalchardet.UniversalDetector;

import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @see JsonUtil
 * User: barshay_e * Date: 14.09.11 * Time: 12:33
 */
public class StringUtils {

    public static String bold(String txt) {
        return "<span style='color: #0000FF;'><b>" + txt + "</b></span>";
    }

    public static final String END_LINE = "\r\n";

    /*
     * Метод аналогичный JSONObject.quote
     * только не добавляет кавычки в начале и в концe
     */
    public static String quote(String string) {
        if (string == null || string.length() == 0) {
            return "\"\"";
        }

        char b;
        char c = 0;
        int i;
        int len = string.length();
        StringBuffer sb = new StringBuffer(len + 4);
        String t;

        for (i = 0; i < len; i += 1) {
            b = c;
            c = string.charAt(i);
            switch (c) {
                case '\\':
                case '"':
                    sb.append('\\');
                    sb.append(c);
                    break;
                case '/':
                    if (b == '<') {
                        sb.append('\\');
                    }
                    sb.append(c);
                    break;
                case '\b':
                    sb.append("\\b");
                    break;
                case '\t':
                    sb.append("\\t");
                    break;
                case '\n':
                    sb.append("\\n");
                    break;
                case '\f':
                    sb.append("\\f");
                    break;
                case '\r':
                    sb.append("\\r");
                    break;
                default:
                    if (c < ' ' || (c >= '\u0080' && c < '\u00a0') ||
                            (c >= '\u2000' && c < '\u2100')) {
                        t = "000" + Integer.toHexString(c);
                        sb.append("\\u" + t.substring(t.length() - 4));
                    } else {
                        sb.append(c);
                    }
            }
        }
        return sb.toString();
    }

    public static String getFileExt(String fileName) {
        int i = fileName.lastIndexOf(".");
        String ext = i > 0 ? fileName.substring(i + 1) : null;
        return ext;
    }

    public static String getFileName(String fullName) {
        String name = fullName;
        int i = name.lastIndexOf("/");
        if (i >= 0)
            name = name.substring(i + 1);
        i = name.lastIndexOf("\\");
        if (i >= 0)
            name = name.substring(i + 1);
        return name;
    }

    static final String HEXES = "0123456789ABCDEF";

    public static String getHexString(byte[] raw) {
        if (raw == null) {
            return null;
        }
        final StringBuilder hex = new StringBuilder(2 * raw.length);
        for (final byte b : raw) {
            hex.append(HEXES.charAt((b & 0xF0) >> 4))
                    .append(HEXES.charAt((b & 0x0F)));
        }
        return hex.toString();
    }

    public static byte[] hex2ByteArray(String hexString) {
        hexString = hexString.toUpperCase();
        int n = hexString.length();
        byte[] out = new byte[n / 2];
        for (int i = 0; i < n; i += 2) {
            int hn = HEXES.indexOf(hexString.charAt(i));
            int ln = HEXES.indexOf(hexString.charAt(i + 1));
            out[i / 2] = (byte) ((hn << 4) | ln);
        }
        return out;
    }

    public static boolean isEmpty(String data) {
        return data == null || data.trim().length() == 0;
    }

    public static boolean isEmpty(String data, String exception) throws Exception {
        boolean empty = (data == null || data.trim().length() == 0);
        if (empty) {
            throw new Exception(exception);
        }
        return false;
    }

    public static boolean equals(String a, String b) {
        return a == null ? b == null : a.equals(b);
    }

    /**
     * Iспользуется для сортировки строк, состоящих из букв и цифр
     * Примеры использования: сортировка номеров домов, сортировка номеров квартир
     *
     * @param s
     * @return
     */
    public static String internalSortVal(String s) {
        final Pattern p = Pattern.compile("([a-zA-Z]+)|(\\d+)|([^\\da-zA-Z\\s]+)");
        if (s == null || s.length() == 0) return "";
        Matcher m = p.matcher(s);
        StringBuffer sb = new StringBuffer(30);
        while (m.find()) {
            String tmp = m.group(2);
            if (tmp == null)
                sb.append(m.group());
            else
                sb.append(String.format("%1$5s", m.group()).replace(' ', '0'));
        }
        return sb.toString();
    }

    // use JSPUtil#getParameter
    @Deprecated
    public static String getStringParam(String name, Map<String, String[]> pars) {
        String res = null;

        try {
            res = pars.get(name)[0];
        } catch (Throwable th) {
            res = null;
        }
        if (res != null && res.trim().length() == 0)
            res = null;

        return res != null ? res.trim() : null;
    }

    public static String convertHexToString(String hex) {
        if (hex == null) return null;
        StringBuilder sb = new StringBuilder();

        if (!isEmpty(hex)) {
            String[] arr = hex.split(";");
            //49204c6f7665204a617661 split into two characters 49, 20, 4c...
            for (String anArr : arr) {

                int decimal = Integer.parseInt(anArr);

                sb.append((char) decimal);

            }
        }


        return sb.toString();
    }

    public static String convertStringToCharCode(String s) {
        StringBuilder b = new StringBuilder();

        String newS = s != null ? s : "NULL";

        for (char c : newS.toCharArray()) {
            b.append("" + (int) c).append(";");
        }
        return b.toString();
    }

    public static String collectionAsString(Collection collection, String separator) {
        if (collection == null || collection.isEmpty()) {
            return "[]";
        }
        if (separator == null) {
            separator = " ";
        }
        StringBuilder stringBuilder = new StringBuilder();
        Iterator iterator = collection.iterator();
        while (iterator.hasNext()) {
            Object o = iterator.next();
            stringBuilder.append(o.toString());
            if (iterator.hasNext()) {
                stringBuilder.append(separator);
            }
        }
        return stringBuilder.toString();
    }

    public static String collectionAsString(Collection collection) {
        return collectionAsString(collection, null);
    }

    public static String checkLength(String string, int maxLength) {
        if (string == null) {
            return null;
        }
        return string.length() > maxLength ? string.substring(0, maxLength) : string;
    }

    public static boolean isJoItemEmpty(JSONObject jo, String key) throws Exception {
        return (jo == null || !jo.has(key) || jo.get(key) == null || isEmpty(jo.get(key).toString()));
    }

    public static String removeTrailingStr(String str, String str2) throws Exception {
        String result = str;
        while (str2.equals(result.substring(result.length() - str2.length(), result.length()))) {
            result = result.substring(0, result.length() - str2.length());
        }

        return result;
    }

    /**
     * убирает из строки (имя файла) префиск, добавляемый при загрузке через Chrome (c:\fakepath\)
     */
    public static String clearChromeFilePrefix(String fileName) throws Exception {
        return fileName.replace("C:\\fakepath\\", "");
    }

    public static String getHouseNomer(String nomer, String korpus, String str, String litera) {
        StringBuilder sb = new StringBuilder();
        boolean f = false;
        if (nomer != null && nomer.length() > 0) {
            sb.append(nomer);
            f = true;
        }
        if (korpus != null && korpus.length() > 0) {
            if (f) sb.append(" ");
            sb.append("к. ").append(korpus);
            f = true;
        }
        if (str != null && str.length() > 0) {
            if (f) sb.append(" ");
            sb.append("стр. ").append(str);
            f = true;
        }
        if (litera != null && litera.length() > 0) {
            if (f) sb.append(" ");
            sb.append("лит. ").append(litera);
        }
        return sb.toString();
    }

    /**
     * Поиск периода в строке. Возвращает найденную подстроку если она одна, иначе null
     * Iщет по шаблонам: " mm.yyyy", " mm yyyy", " mm-yyyy"
     */
    public static String findPeriod(String str) throws Exception {
        Pattern ptn = Pattern.compile("\\s\\d{2}\\s\\d{4}|\\s\\d{2}\\.\\d{4}|\\s\\d{2}-\\d{4}");
        Matcher matcher = ptn.matcher(str);
        List<String> results = new ArrayList<>();
        while (matcher.find()) {
            results.add(matcher.group().trim());
        }
        if (results.size() == 1) return results.get(0);
        else return null;
    }

    public static String toStringIfNotNull(Object object) {
        if (object != null) {
            return object.toString();
        }
        return null;
    }

    /*public static String detectCharset(byte[] buf) throws IOException {
        if (buf[0] == -17 && buf[1] == -69 && buf[2] == -65) {
            return "UTF-8";
        }

        Set<String> applyCharsetNames = new HashSet<String>(Arrays.asList(new String[]{"IBM866", "WINDOWS-1251", "UTF-8"}));

        String result = "WINDOWS-1251";
        ByteArrayInputStream in = new ByteArrayInputStream(buf);
        byte[] arr = new byte[4096];
        UniversalDetector detector = new UniversalDetector(null);
        int nread;
        while ((nread = in.read(arr)) > 0 && !detector.isDone()) {
            detector.handleData(arr, 0, nread);
        }
        detector.dataEnd();

        String encoding = detector.getDetectedCharset();
        if (encoding != null && applyCharsetNames.contains(encoding)) {
            result = encoding;
        }

        detector.reset();

        return result;
    }*/

    /**
     * Метод убирает все символы кроме перечисленных в regexp. (Проблема была в названиях файлов упаковываемых в ZIP)
     * Метод убирает "/", т.к. например есть юр.лица, у которых названия содержат данный символ
     * @param value строка из которой нужно убрать символы
     * @return - строка, содержащая только символы a-z A-Z А-Я а-я 0-9 - \s & , # ; % ? : ( ) . < > _ = + \
     */
    public static String removeCharsFowZipEntryName(String value) {
        if(value == null) return null;

        return value.replaceAll("[^a-zA-ZА-Яа-я0-9-\\s&,#;%?:().<>_=+\\\\']", "");
    }

    /**
     * Метод кастует переданное строковое значение к указанному классу
     * Поддерживаются BigDecimal, Double, Long, Integer, Date (dd.mm.yyyy)
     * @param val - строковое значение
     * @param clazz - класс к которому необходимо привести значение
     */
    public static <T> T cast(String val, Class<T> clazz) throws Exception {
        if (StringUtils.isEmpty(val) || clazz == null) return null;
        switch (clazz.getSimpleName()) {
            case "BigDecimal":{
                return (T) new BigDecimal(val);
            }
            case "Double": {
                return (T) Double.valueOf(val);
            }
            case "Long": {
                return (T) (Long) Long.parseLong(val);
            }
            case "Integer": {
                return (T) (Integer) Integer.parseInt(val);
            }
            case "Date": {
                return (T) (Date) SynchrDateFormat.parseDate(val);
            }
            default: throw new ClassCastException("Не поддерживаемый класс");
        }
    }

    /**
     * Получение значения доли владения из строкового значения.
     *
     * @param shareOwnerStr
     * @return
     * @throws Exception
     */
    public static BigDecimal getShareOwnerVal(String shareOwnerStr) throws Exception {
        BigDecimal res = null;
        // проверяем кол-во вхождений спец символов
        String[] ss = new String[]{".", "/", ","};

        int cntSs = 0;
        for(String ssi : ss){
            if(shareOwnerStr.contains(ssi)) cntSs++;
        }

        if(cntSs > 1) throw new Exception("Ошибка ввода значения доли владения");

        // если вырезать все спец символы, то должны остаться только цифровые симолы
        if(shareOwnerStr.contains("/")){
            String[] arr = shareOwnerStr.split("/");

            if(arr.length < 2 || arr.length > 2) throw new Exception("Ошибка ввода значения доли владения");

            Integer ch = 0;
            Integer zn = 0;
            try {
                ch = Integer.valueOf(arr[0]);
                zn = Integer.valueOf(arr[1]);
            }catch(NumberFormatException ne){
                throw new Exception("Ошибка ввода значения доли владения");
            }

            if(zn == 0) throw new Exception("Ошибка ввода значения доли владения");

            res = BigDecimal.valueOf(ch).divide(BigDecimal.valueOf(zn),  5, BigDecimal.ROUND_HALF_UP);

        }else{
            res = new BigDecimal(shareOwnerStr.replaceAll(",", "."));
        }

        // проверка на то что значение не больше 1
        if(res.compareTo(BigDecimal.ONE) > 0){
            throw new Exception("Значение доли не может быть больше 100%");
        }

        return res;
    }

    /**
     * Проверяет соответствует ли переданная строка шаблону
     * @param sourceStr - исходная строка для проверки
     * @param regex - регулярное выражение
     */
    public static boolean checkRegex(String sourceStr, String regex) throws Exception {
        Pattern ptn = Pattern.compile(regex);
        Matcher matcher = ptn.matcher(sourceStr);
        return matcher.find();
    }

    /**
     * Возвращает список значений, найденных в исходной строке по указанному регулярному выражению
     * @param sourceStr - исходная строка
     * @param regex - регулярное выражение для поиска
     */
    public static List<String> regexpFind(String sourceStr, String regex) throws Exception {
        Pattern ptn = Pattern.compile(regex);
        Matcher matcher = ptn.matcher(sourceStr);

        List<String> results = new ArrayList<>();
        while (matcher.find()) {
            results.add(matcher.group());
        }

        return results;
    }
}
