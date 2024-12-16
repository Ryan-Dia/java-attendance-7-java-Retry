package attendance.dto;

import attendance.model.AttendanceRegister;
import attendance.model.State;
import java.time.LocalDateTime;

public record AttendanceRegisterDto(LocalDateTime localDateTime, State state) {
    public AttendanceRegisterDto(AttendanceRegister attendanceRegister) {
        this(attendanceRegister.getDatetime(), attendanceRegister.getState());
    }
}
