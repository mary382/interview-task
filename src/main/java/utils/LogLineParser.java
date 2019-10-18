package utils;

import entity.LogEntity;

import java.time.LocalDateTime;

public class LogLineParser {

    private static final String LOG_LINE_SPLITTER = ";";

    public static Boolean isConformLog(String filterParam, String paramFromLog) {
        return filterParam.isEmpty() || paramFromLog.equals(filterParam);
    }

    public static LogEntity toEntity(String logLine) {
        var dateTime = LocalDateTime.parse(logLine.split(LOG_LINE_SPLITTER)[0]);
        var username = logLine.split(LOG_LINE_SPLITTER)[1];
        var message = logLine.split(LOG_LINE_SPLITTER)[2];

        return LogEntity.builder()
                .dateTime(dateTime)
                .username(username)
                .message(message)
                .build();
    }

}
