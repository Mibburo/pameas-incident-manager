package pameas.incident.detection.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static String dateToString(LocalDateTime ldt) {
        return ldt.format(formatter);
    }

    public static LocalDateTime dateStringToLDT(String dateS) {
        return LocalDateTime.parse(dateS, formatter);
    }
}
