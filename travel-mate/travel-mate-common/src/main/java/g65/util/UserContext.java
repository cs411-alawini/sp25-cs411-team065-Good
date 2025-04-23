package g65.util;


public class UserContext {
    private static final ThreadLocal<Integer> USER_ID_HOLDER = new InheritableThreadLocal<>();
    private static final ThreadLocal<String> TOKEN_HOLDER = new InheritableThreadLocal<>();

    public static void setUserId(Integer userId) {
        USER_ID_HOLDER.set(userId);
    }

    public static Integer getUserId() {
        return USER_ID_HOLDER.get();
    }

    public static void removeUserId() {
        USER_ID_HOLDER.remove();
    }

    public static void setToken(String token) {
        TOKEN_HOLDER.set(token);
    }

    public static String getToken() {
        return TOKEN_HOLDER.get();
    }

    public static void removeToken() {
        TOKEN_HOLDER.remove();
    }

    public static void clear() {
        USER_ID_HOLDER.remove();
        TOKEN_HOLDER.remove();
    }
}
