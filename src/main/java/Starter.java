import exception.DataTimeFormatException;
import exception.FilterLogsThreadException;
import exception.GetFilesPathsException;
import exception.NoSpecifiedParametersForSearchLogsException;
import lombok.Getter;
import repository.impl.PathsRepositoryImpl;
import service.FilterService;
import service.impl.FilterServiceImpl;
import utils.DataTimePeriod;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Starter {

    @Getter
    private static FilterService filterService = new FilterServiceImpl();

    public static void main(String[] args) throws NoSpecifiedParametersForSearchLogsException, DataTimeFormatException, FilterLogsThreadException, GetFilesPathsException, IOException {

        Scanner in = new Scanner(System.in);
        System.out.print("Enter username filter parameter to specify logs: ");
        String username = in.nextLine();
        System.out.print("Enter timeFrom filter parameter to specify logs: ");
        String dataTimeFrom = in.nextLine();
        System.out.print("Enter timeTo filter parameter to specify logs: ");
        String dataTimeTo = in.nextLine();
        System.out.print("Enter message filter parameter to specify logs: ");
        String message = in.nextLine();

        if (!isSearchSpecified(username, dataTimeFrom, dataTimeTo, message)) {
            throw new NoSpecifiedParametersForSearchLogsException("There no any specified parameters for searching logs");
        }

        DataTimePeriod dataTimePeriod = new DataTimePeriod(dataTimeFrom, dataTimeTo);
        System.out.print("Enter time unit to create aggregate statistics(\"username\", \"hour\", \"day\", \"month\"): ");
        String groupingParam = in.nextLine();
        if (groupingParam.isEmpty()) {
            throw new NoSpecifiedParametersForSearchLogsException("No specified grouping parameter for creating aggregate statistics");
        }

        System.out.print("Enter thread count (default = 1) ");
        Integer threadCountFromUser = in.nextInt();
        int threadCount;
        if (threadCountFromUser.toString().isEmpty()) {
            threadCount = 1;
        } else {
            threadCount = threadCountFromUser;
        }

        System.out.println("Enter path or filename to output file");
        String output = in.nextLine();
        if (output.isEmpty()) {
            output = System.getProperty(PathsRepositoryImpl.USER_DIR_PATH) + PathsRepositoryImpl.OUTPUT_DIRECTORY_PATH +
                    LocalDateTime.now().toString() + ".txt";
        }

        filterService.getFileWithFilteredLogs(Paths.get(output), dataTimePeriod, username, message, threadCount);
    }

    private static Boolean isSearchSpecified(String username, String timeFrom, String timeTo, String message) {
        return !(username.isEmpty()
                && timeFrom.isEmpty()
                && timeTo.isEmpty()
                && message.isEmpty());
    }

}


