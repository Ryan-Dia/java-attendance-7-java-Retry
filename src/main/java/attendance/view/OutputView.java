package attendance.view;

import attendance.dto.AttendanceRegisterDto;
import attendance.dto.AttendanceRegisterWithPreviousDto;
import attendance.dto.UserDto;
import attendance.model.Punishment;
import attendance.model.State;
import attendance.utils.KoreanDateFormatter;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public final class OutputView {

    private OutputView() {
    }

    public static void printAttendanceRegister(AttendanceRegisterDto attendanceRegisterDto) {
        final LocalDateTime datetime = attendanceRegisterDto.localDateTime();
        final String category = attendanceRegisterDto.state().getCategory();
        System.out.printf("%s (%s)%n", KoreanDateFormatter.loadDateConverter(datetime), category);
    }

    public static void printStatTotalCount(Map<State, Integer> totalCount) {
        System.out.println();
        for (Entry<State, Integer> state : totalCount.entrySet()) {
            System.out.printf("%s: %d회%n", state.getKey().getCategory(), state.getValue());
        }
    }

    public static void printPunishment(Punishment punishment) {
        if (punishment != Punishment.NONE) {
            System.out.printf("%s 대상자 입니다.%n", punishment.getPunishmentName());
        }
    }

    public static void printAttendanceCheck(String time, String category) {
        System.out.printf("%s (%s)", time, category);
    }

    public static void printAdjustedAttendanceTime(
            AttendanceRegisterWithPreviousDto attendanceRegisterWithPreviousDto) {
        final AttendanceRegisterDto previousAttendanceRegister = attendanceRegisterWithPreviousDto.attendanceRegisterDto();
        final LocalTime localTime = attendanceRegisterWithPreviousDto.localDateTime().toLocalTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        System.out.printf("%n%s (%s)",
                KoreanDateFormatter.loadDateConverter(previousAttendanceRegister.localDateTime()),
                previousAttendanceRegister.state().getCategory());
        System.out.printf(" -> %s (%s) 수정 완료!%n", localTime.format(formatter),
                attendanceRegisterWithPreviousDto.stateCategory());
    }

    public static void printConfirmationOfPersonsAtRiskOfExpulsion(List<UserDto> userDto) {
        System.out.println("\n제적 위험자 조회 결과");
        for (UserDto user : userDto) {
            final Map<State, Integer> stateTotalCount = user.stateTotalCountWithoutConsidered();
            final Integer absenceCount = stateTotalCount.getOrDefault(State.ABSENCE, 0);
            final Integer latenessCount = stateTotalCount.getOrDefault(State.LATENESS, 0);
            final Punishment punishment = user.punishment();
            System.out.printf("- %s: 결석 %d회, 지각 %d회 (%s)%n", user.userNickname(), absenceCount, latenessCount,
                    punishment.getPunishmentName());
        }
    }
}
