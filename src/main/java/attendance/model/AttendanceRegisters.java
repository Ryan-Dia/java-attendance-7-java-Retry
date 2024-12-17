package attendance.model;

import static java.util.stream.Collectors.toList;

import attendance.error.ErrorMessages;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
                .sorted((a, b) -> a.getDatetime().getDayOfMonth() - b.getDatetime().getDayOfMonth()).collect(toList());
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

    public void attend(String startTime, LocalDateTime todayTime) {
        final LocalDate todayDate = todayTime.toLocalDate();
        if (isAlreadyPresent(todayDate)) {
            throw new IllegalArgumentException(ErrorMessages.ALREADY_PRESENT.getMessage());
        }
        attendanceRegisters.add(AttendanceRegister.attend(startTime, todayTime));
    }


    public AttendanceRegister adjustAttendanceTime(LocalDateTime localDateTime) {
        final AttendanceRegister previousAttendanceRegister = attendanceRegisters.stream()
                .filter(register -> register.getDatetime().toLocalDate().equals(localDateTime.toLocalDate()))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessages.INVALID_FORMAT.getMessage()));
        final AttendanceRegister newAttendanceRegister = AttendanceRegister.adjustAttendanceTime(localDateTime,
                previousAttendanceRegister);

        attendanceRegisters.remove(previousAttendanceRegister);
        attendanceRegisters.add(newAttendanceRegister);
        return newAttendanceRegister;
    }

    private boolean isAlreadyPresent(final LocalDate todayDate) {
        return attendanceRegisters.stream()
                .anyMatch(register -> register.getDatetime().toLocalDate().equals(todayDate));
    }

    public AttendanceRegister findAttendanceByToday(LocalDateTime todayTime) {
        return attendanceRegisters.stream()
                .filter(register -> register.getDatetime().toLocalDate().equals(todayTime.toLocalDate()))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessages.INVALID_FORMAT.getMessage()));
    }


    public List<AttendanceRegister> getAttendanceRegisters() {
        return attendanceRegisters;
    }
}
