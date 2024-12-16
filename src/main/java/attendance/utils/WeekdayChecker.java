package attendance.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;

public final class WeekdayChecker {

    public static boolean isWeekday(int day) {
        LocalDate date = LocalDate.of(2024, 12, day);
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek == DayOfWeek.MONDAY ||
                dayOfWeek == DayOfWeek.TUESDAY ||
                dayOfWeek == DayOfWeek.WEDNESDAY ||
                dayOfWeek == DayOfWeek.THURSDAY ||
                dayOfWeek == DayOfWeek.FRIDAY;
    }
}

