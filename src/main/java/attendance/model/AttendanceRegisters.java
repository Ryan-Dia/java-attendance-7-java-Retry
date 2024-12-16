package attendance.model;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AttendanceRegisters {

    private final List<AttendanceRegister> attendanceRegisters;

    public AttendanceRegisters(final List<AttendanceRegister> attendanceRegisters) {
        this.attendanceRegisters = sort(attendanceRegisters);
    }

    private List<AttendanceRegister> sort(List<AttendanceRegister> attendanceRegisters) {
        return attendanceRegisters.stream()
                .sorted((a, b) -> a.getDatetime().getDayOfMonth() - b.getDatetime().getDayOfMonth()).toList();
    }

    public Map<State, Integer> calculateAttendance() {
        Map<State, Integer> box = new LinkedHashMap<>();
        long attendance = attendanceRegisters.stream().filter(register -> register.getState() == State.ATTENDANCE)
                .count();
        long late = attendanceRegisters.stream().filter(register -> register.getState() == State.LATENESS)
                .count();
        long absence = attendanceRegisters.stream().filter(register -> register.getState() == State.ABSENCE)
                .count();
        if (late >= 3) {
            long value = late / 3;
            absence += value;
            late -= value * 3;
        }
        box.put(State.ATTENDANCE, (int) attendance);
        box.put(State.LATENESS, (int) late);
        box.put(State.ABSENCE, (int) absence);
        return box;
    }


    public List<AttendanceRegister> getAttendanceRegisters() {
        return attendanceRegisters;
    }
}
