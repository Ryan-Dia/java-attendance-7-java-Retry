package attendance.model.user;

import attendance.error.ErrorMessages;
import attendance.model.State;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class AttendanceRegister {
    private final LocalDateTime datetime;
    private final State state;
    private final AttendanceRegister previousRegister;

    public AttendanceRegister(final LocalDateTime datetime, final State state) {
        this.datetime = datetime;
        this.state = state;
        this.previousRegister = null;
    }

    private AttendanceRegister(final LocalDateTime datetime, final State state,
                               final AttendanceRegister previousRegister) {
        this.datetime = datetime;
        this.state = state;
        this.previousRegister = previousRegister;
    }

    public static AttendanceRegister adjustAttendanceTime(LocalDateTime localDateTime,
                                                          AttendanceRegister previousRegister) {
        return new AttendanceRegister(localDateTime, State.findByLocalDateTime(localDateTime), previousRegister);
    }


    public static AttendanceRegister from(final String timeString) {
        final String[] splitTime = timeString.split(" ");
        final LocalDate date = LocalDate.parse(splitTime[0]);
        final LocalTime time = LocalTime.parse(splitTime[1]);
        final LocalDateTime localDateTime = LocalDateTime.of(date, time);
        return new AttendanceRegister(localDateTime, State.findByLocalDateTime(localDateTime));
    }

    public static AttendanceRegister attend(String startTime, LocalDateTime todayTime) {
        final LocalTime time = parseStartTime(startTime);
        final LocalDate toDay = todayTime.toLocalDate();
        final LocalDateTime localDateTime = LocalDateTime.of(toDay, time);
        return new AttendanceRegister(localDateTime, State.findByLocalDateTime(localDateTime));
    }

    private static LocalTime parseStartTime(String startTime) {
        try {
            return LocalTime.parse(startTime);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(ErrorMessages.INVALID_FORMAT.getMessage());
        }
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public State getState() {
        return state;
    }


    public AttendanceRegister getPreviousRegister() {
        return previousRegister;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AttendanceRegister that = (AttendanceRegister) o;
        return Objects.equals(datetime, that.datetime);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(datetime);
    }
}
