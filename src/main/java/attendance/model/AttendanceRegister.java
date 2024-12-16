package attendance.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AttendanceRegister {
    private final LocalDateTime datetime;
    private final State state;

    public AttendanceRegister(final LocalDateTime datetime, final State state) {
        this.datetime = datetime;
        this.state = state;
    }

    public static AttendanceRegister from(final String timeString) {
        final String[] splitTime = timeString.split(" ");
        final LocalDate date = LocalDate.parse(splitTime[0]);
        final LocalTime time = LocalTime.parse(splitTime[1]);
        final LocalDateTime localDateTime = LocalDateTime.of(date, time);
        return new AttendanceRegister(localDateTime, State.findByLocalDateTime(localDateTime));
    }


    public LocalDateTime getDatetime() {
        return datetime;
    }

    public State getState() {
        return state;
    }
}
