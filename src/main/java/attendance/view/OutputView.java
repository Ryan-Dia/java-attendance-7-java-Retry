package attendance.view;

import attendance.dto.AttendanceRegisterDto;
import attendance.dto.AttendanceRegisterWithPreviousDto;
import attendance.model.Punishment;
import attendance.model.State;
import attendance.utils.KoreanDateFormatter;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Map.Entry;

public final class OutputView {

    private OutputView() {
    }

    public static void printAttendanceRegister(AttendanceRegisterDto attendanceRegisterDto) {
        final LocalDateTime datetime = attendanceRegisterDto.localDateTime();
        final String category = attendanceRegisterDto.state().getCategory();
        System.out.println(KoreanDateFormatter.loadDateConverter(datetime) + " " + "(" + category + ")");
    }

    public static void printStatTotalCount(Map<State, Integer> totalCount) {
        System.out.println();
        for (Entry<State, Integer> state : totalCount.entrySet()) {
            System.out.printf("%s: %d회%n", state.getKey().getCategory(), state.getValue());
        }
    }

    public static void printPunishment(Punishment punishment) {
        if (punishment != Punishment.NONE) {
            System.out.printf("%s 대상자 입니다.", punishment.getPunishmentName());
        }
    }

    public static void printAttendanceCheck(String time, String category) {
        System.out.printf("%s (%s)", time, category);
    }

    public static void printAdjustedAttendanceTime(
            AttendanceRegisterWithPreviousDto attendanceRegisterWithPreviousDto) {
        final AttendanceRegisterDto previousAttendanceRegister = attendanceRegisterWithPreviousDto.attendanceRegisterDto();
        final LocalTime localTime = previousAttendanceRegister.localDateTime().toLocalTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        System.out.printf("%n%s (%s)",
                KoreanDateFormatter.loadDateConverter(previousAttendanceRegister.localDateTime()),
                previousAttendanceRegister.state().getCategory());
        System.out.printf("-> %s (%s) 수정 완료!", localTime.format(formatter),
                attendanceRegisterWithPreviousDto.stateCategory());
    }
}
