package attendance.model;

public enum Command {
    CHECK_ATTENDANCE("1", "출석 확인"),
    EDIT_ATTENDANCE("2", "출석 수정"),
    CHECK_ATTENDANCE_RECORD("3", "크루별 출석 기록 확인"),
    CONFIRMATION_OF_THOSE_AT_RISK_OF_EXPULSION("4", "제적 위험자 확인"),
    QUIT("Q", "종료");

    private final String commandOrder;
    private final String command;

    Command(final String commandOrder, final String command) {
        this.commandOrder = commandOrder;
        this.command = command;
    }

    public static boolean isCheckAttendance(String command) {
        return Command.CHECK_ATTENDANCE.getCommandOrder().equals(command);
    }

    public static boolean isEditAttendance(String command) {
        return Command.EDIT_ATTENDANCE.getCommandOrder().equals(command);
    }

    public static boolean isCommandCheckAttendanceRecord(String command) {
        return Command.CHECK_ATTENDANCE_RECORD.getCommandOrder().equals(command);
    }

    public static boolean isConfirmationOfThoseAtRiskOfExpulsion(String command) {
        return Command.CONFIRMATION_OF_THOSE_AT_RISK_OF_EXPULSION.getCommandOrder().equals(command);
    }

    public String getCommandOrder() {
        return commandOrder;
    }

    public String getCommand() {
        return command;
    }
}

