package ac.liverpool.forum.enums;

public enum AppHttpCodeEnum {
    // 成功
    SUCCESS(200,"operation success"),
    // 登录
    NEED_LOGIN(401,"Login is required for operation"),
    NO_OPERATOR_AUTH(403,"Unauthorized operation"),
    SYSTEM_ERROR(500,"An error occurred"),
    USERNAME_EXIST(501,"The username already exists"),

    EMAIL_EXIST(503, "The email already exists"),
    REQUIRE_USERNAME(504,
            "Username required"),
    LOGIN_ERROR(505,"Incorrect username or password"),
    USER_NOT_EXIST(507, "user does not exist"),
    PARAMETER_ERROR(510, "Parameter error"),

    NO_POSTS(508, "No posts or comment found"),
    REPEAT_LIKE(509, "Already liked"),
    // 🔥 新增这个枚举值
    USER_ID_MISSING(506, "no user id"),;





    int code;
    String msg;

    AppHttpCodeEnum(int code, String errorMessage) {
        this.code = code;
        this.msg = errorMessage;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
