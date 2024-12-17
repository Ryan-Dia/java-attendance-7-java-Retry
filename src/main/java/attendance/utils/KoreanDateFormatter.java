package attendance.utils;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public final class KoreanDateFormatter {
    private KoreanDateFormatter() {
    }

    public static final Map<DayOfWeek, String> DAY_OF_WEEK_KO_MAP = Map.of(
            DayOfWeek.SUNDAY, "일",
            DayOfWeek.MONDAY, "월",
            DayOfWeek.TUESDAY, "화",
            DayOfWeek.WEDNESDAY, "수",
            DayOfWeek.THURSDAY, "목",
            DayOfWeek.FRIDAY, "금",
            DayOfWeek.SATURDAY, "토"
    );

    //12월 02일 월요일 13:00 (출석)
    public static String loadDateConverter(LocalDateTime loadDateTime) {
        String dayOfWeekKo = DAY_OF_WEEK_KO_MAP.getOrDefault(loadDateTime.getDayOfWeek(), "Not a day of a week");
        String dayOfMonth = String.valueOf(loadDateTime.getDayOfMonth());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        final LocalTime localTime = loadDateTime.toLocalTime();
        String formattedTime = localTime.format(formatter);
        if (formattedTime.equals("00:00")) {
            formattedTime = "--:--";
        }
        if (dayOfMonth.length() == 1) {
            dayOfMonth = "0" + dayOfMonth;
        }
        return String.format("%s월 %s일 %s요일 %s",
                loadDateTime.getMonthValue(),
                dayOfMonth,
                dayOfWeekKo,
                formattedTime);
    }

    public static String loadDateConverterWithoutTime(LocalDateTime loadDateTime) {
        String dayOfWeekKo = DAY_OF_WEEK_KO_MAP.getOrDefault(loadDateTime.getDayOfWeek(), "Not a day of a week");
        String dayOfMonth = String.valueOf(loadDateTime.getDayOfMonth());
        if (dayOfMonth.length() == 1) {
            dayOfMonth = "0" + dayOfMonth;
        }
        return String.format("%s월 %s일 %s요일",
                loadDateTime.getMonthValue(),
                dayOfMonth,
                dayOfWeekKo);
    }
}
