package filter;

import exception.ReadFileException;
import lombok.AllArgsConstructor;
import utils.DataTimePeriod;
import utils.LogLineParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

@AllArgsConstructor
public class LogFilter implements Callable<List<String>> {

    private Path filePath;
    private DataTimePeriod period;
    private String username;
    private String message;

    @Override
    public List<String> call() throws ReadFileException {
        try {
            return Files.lines(filePath)
                    .filter(logLine -> {
                        if (period.getDateFrom() != null && period.getDateTo() != null) {
                            var dateTime = LocalDateTime.parse(LogLineParser.splitLog(logLine)[0]);
                            return dateTime.isAfter(period.getDateFrom()) && dateTime.isBefore(period.getDateTo());
                        }
                        return true;
                    })
                    .filter(logLine -> LogLineParser.isConformLog(logLine, username, 1))
                    .filter(logLine -> LogLineParser.isConformLog(logLine, message, 2))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new ReadFileException(String.format("Error reading file from filePath: %s", filePath));
        }
    }

}
