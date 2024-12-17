package attendance.dto;

import attendance.model.AttendanceRegister;
import java.time.LocalDateTime;

public record AttendanceRegisterWithPreviousDto(LocalDateTime localDateTime, String stateCategory,
                                                AttendanceRegisterDto attendanceRegisterDto) {
    public AttendanceRegisterWithPreviousDto(AttendanceRegister attendanceRegister) {
        this(attendanceRegister.getDatetime(), attendanceRegister.getState().getCategory(),
                new AttendanceRegisterDto(attendanceRegister.getPreviousRegister()));
    }
}
