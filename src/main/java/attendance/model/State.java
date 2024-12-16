package attendance.model;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

public enum State {
    ATTENDANCE("출석", LocalTime.of(13, 6), LocalTime.of(10, 6)),
    LATENESS("지각", LocalTime.of(13, 5), LocalTime.of(10, 5)),
    ABSENCE("결석", LocalTime.of(13, 30), LocalTime.of(10, 30));

    private final String category;
    private final LocalTime mondayLateness;
    private final LocalTime weekLateness;

    State(final String category, final LocalTime mondayLateness, final LocalTime weekLateness) {
        this.category = category;
        this.mondayLateness = mondayLateness;
        this.weekLateness = weekLateness;
    }

    public static State findByLocalDateTime(LocalDateTime localDateTime) {
        final DayOfWeek dayOfWeek = localDateTime.getDayOfWeek();
        final int hour = localDateTime.getHour();
        final int minute = localDateTime.getMinute();
        final LocalTime localTime = LocalTime.of(hour, minute);
        if (dayOfWeek == DayOfWeek.MONDAY) {
            return getMondayState(localTime);
        }
        return getWeekState(localTime);
    }

    private static State getMondayState(final LocalTime localTime) {
        if (localTime.isAfter(State.ABSENCE.mondayLateness)) {
            return State.ABSENCE;
        }
        if (localTime.isAfter(State.LATENESS.mondayLateness)) {
            return State.LATENESS;
        }
        return State.ATTENDANCE;
    }

    private static State getWeekState(final LocalTime localTime) {
        if (localTime.isAfter(State.ABSENCE.weekLateness)) {
            return State.ABSENCE;
        }
        if (localTime.isAfter(State.LATENESS.weekLateness)) {
            return State.LATENESS;
        }
        return State.ATTENDANCE;
    }

    public String getCategory() {
        return category;
    }
}
