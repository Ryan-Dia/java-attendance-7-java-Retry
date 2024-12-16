package attendance.model;

import java.util.Map;
import java.util.Objects;

public class User {
    private final String nickname;
    private final AttendanceRegisters attendanceRegisters;

    public User(final String nickname, final AttendanceRegisters attendanceRegisters) {
        this.nickname = nickname;
        this.attendanceRegisters = attendanceRegisters;
    }

    public String getNickname() {
        return nickname;
    }

    public AttendanceRegisters getAttendanceRegisters() {
        return attendanceRegisters;
    }

    public Map<State, Integer> getStateTotalCount() {
        return attendanceRegisters.calculateAttendance();
    }

    public Punishment getPunishment() {
        final Map<State, Integer> stateTotalCount = getStateTotalCount();
        return Punishment.findByPunishMent(stateTotalCount.get(State.ABSENCE));
    }


    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final User user = (User) o;
        return Objects.equals(nickname, user.nickname);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(nickname);
    }
}
