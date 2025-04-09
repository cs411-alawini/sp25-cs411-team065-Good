package g65.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCode {

    SUCCESS("200", "Success"),
    BAD_REQUEST("400", "Bad request"),
    UNAUTHORIZED("401", "Unauthorized"),
    NOT_FOUND("404", "Not found"),
    SERVER_ERROR("500", "Internal server error"),

    USER_NOT_FOUND("BIZ_USER_1001", "User not found"),
    PASSWORD_INCORRECT("BIZ_USER_1002", "Incorrect password"),
    USER_ALREADY_EXISTS("BIZ_USER_1003", "User already exists"),
    LOGIN_EXPIRED("BIZ_USER_1004", "Login session expired"),
    TOKEN_INVALID("BIZ_USER_1005", "Invalid token"),
    PERMISSION_DENIED("BIZ_USER_1006", "Permission denied"),

    SYS_RUNTIME_ERROR("SYS_0001", "A runtime error occurred"),
    SYS_UNKNOWN_ERROR("SYS_0002", "An unknown error occurred"),

    ;
    private final String code;
    private final String message;

}
