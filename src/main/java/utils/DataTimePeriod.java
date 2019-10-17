package utils;

import exception.DataTimeFormatException;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class DataTimePeriod {

    @Getter
    private LocalDateTime dateFrom;

    @Getter
    private LocalDateTime dateTo;

    public DataTimePeriod(String dateFrom, String dateTo) throws DataTimeFormatException {

        if (!(dateFrom.isEmpty() && dateTo.isEmpty())) {
            try {
                this.dateFrom = LocalDateTime.parse(dateFrom);
                this.dateTo = LocalDateTime.parse(dateTo);
            } catch (DateTimeParseException exp) {
                throw new DataTimeFormatException(String.format("Wrong entered data format, dateFrom : %s, dateTo : %s",
                        dateFrom, dateTo));
            }

        }
    }

}
