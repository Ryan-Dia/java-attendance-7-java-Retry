package attendance.utils;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public final class OpenChecker {
    private static final List<Integer> HOLIDAY = List.of(25);
    private static final LocalTime OPEN_TIME = LocalTime.of(8, 0);
    private static final LocalTime CLOSE_TIME = LocalTime.of(23, 0);

    public static void checkCampusOpen(LocalDateTime date) {
        final int day = date.toLocalDate().getDayOfMonth();
        if (!WeekdayChecker.isWeekday(day) || HOLIDAY.contains(day)) {
            final String dateFormat = KoreanDateFormatter.loadDateConverterWithoutTime(date);
            throw new IllegalArgumentException(String.format("[ERROR] %s은 등교일이 아닙니다.", dateFormat));
        }
//        if (isCloseTime(date.toLocalTime())) {
//            throw new IllegalArgumentException(ErrorMessages.OPERATING_HOURS.getMessage());
//        }
    }

    private static boolean isCloseTime(LocalTime time) {
        return time.isBefore(OPEN_TIME) || time.isAfter(CLOSE_TIME);
    }
}
