package utils;

public class LogLineParser {

    private static final String LOG_LINE_SPLITTER = ";";

    public static Boolean isConformLog(String logLine, String filterParam, int paramNumberInLine) {
        return filterParam.isEmpty() || splitLog(logLine)[paramNumberInLine].equals(filterParam);
    }

    public static String[] splitLog(String logLine) {
        return logLine.split(LOG_LINE_SPLITTER);
    }

}
