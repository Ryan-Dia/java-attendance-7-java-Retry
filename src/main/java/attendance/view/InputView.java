package attendance.view;

import attendance.error.ErrorMessages;
import attendance.model.Command;
import attendance.utils.KoreanDateFormatter;
import camp.nextstep.edu.missionutils.Console;
import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDateTime;

public final class InputView {

    private static final String START = "오늘은 12월 %s일 %s요일입니다. 기능을 선택해 주세요.%n";
    private static final String NICKNAME = "\n닉네임을 입력해주세요.";
    private static final String TIME = "등교 시간을 입력해주세요.";
    private static final String ADJUSTMENT_DATE = "수정하려는 날짜(일)를 입력해 주세요.";
    private static final String ADJUSTMENT_TIME = "언제로 변경하겠습니까?";

    private InputView() {
    }

    public static String readCommand() {
        final LocalDateTime now = DateTimes.now();
        final String dayOfWeek = KoreanDateFormatter.DAY_OF_WEEK_KO_MAP.get(now.getDayOfWeek());
        System.out.printf(START, now.getDayOfMonth(), dayOfWeek);
        printCommandList();
        String input = Console.readLine().strip();

        if (input.isEmpty()) {
            throw new IllegalArgumentException(ErrorMessages.EMPTY_INPUT.getMessage());
        }

        return input;
    }

    public static String readNickname() {
        System.out.println(NICKNAME);
        String input = Console.readLine().strip();

        if (input.isEmpty()) {
            throw new IllegalArgumentException(ErrorMessages.EMPTY_INPUT.getMessage());
        }

        return input;
    }

    public static String readSchoolStartTime() {
        System.out.println(TIME);
        String input = Console.readLine().strip();

        if (input.isEmpty()) {
            throw new IllegalArgumentException(ErrorMessages.EMPTY_INPUT.getMessage());
        }

        return input;
    }

    public static String readDateForAdjustment() {
        System.out.println(ADJUSTMENT_DATE);
        String input = Console.readLine().strip();

        if (input.isEmpty()) {
            throw new IllegalArgumentException(ErrorMessages.EMPTY_INPUT.getMessage());
        }

        return input;
    }

    public static String readTimeForAdjustment() {
        System.out.println(ADJUSTMENT_TIME);
        String input = Console.readLine().strip();

        if (input.isEmpty()) {
            throw new IllegalArgumentException(ErrorMessages.EMPTY_INPUT.getMessage());
        }

        return input;
    }


    private static void printCommandList() {
        final Command[] commands = Command.values();
        for (Command command : commands) {
            System.out.printf("%s.  %s%n", command.getCommandOrder(), command.getCommand());
        }
    }
}
