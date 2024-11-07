package store.constants;

public enum Errors {
    //Name
    NULL_NAME("이름으로 null값이 입력되었습니다."),
    BLANK_NAME("이름으로 빈칸이 입력되었습니다.");


    private static final String ERROR_PREFIX = "[ERROR]";

    private String message;

    Errors(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
