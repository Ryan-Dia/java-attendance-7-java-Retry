package attendance.error;

public enum ErrorMessages {
    EMPTY_INPUT("빈 값을 입력할 수 없습니다."),
    INVALID_FORMAT("잘못된 형식을 입력하였습니다."),
    UNREGISTERED_NAME("등록되지 않은 닉네임입니다."),
    INVALID_FUTURE("아직 수정할 수 없습니다."),
    OPERATING_HOURS("캠퍼스 운영 시간에만 출석이 가능합니다."),
    ALREADY_PRESENT("이미 출석을 확인하였습니다. 필요한 경우 수정 기능을 이용해 주세요.");


    private static final String PREFIX = "[ERROR] ";
    private final String message;

    ErrorMessages(String message) {
        this.message = PREFIX + message;
    }

    public String getMessage() {
        return message;
    }
}
