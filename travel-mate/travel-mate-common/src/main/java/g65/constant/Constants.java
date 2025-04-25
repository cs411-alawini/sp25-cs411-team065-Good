package g65.constant;


public class Constants {

    public static final String SPLIT = ",";
    public static final String COLON = ":";
    public static final String SPACE = " ";
    public static final String UNDERLINE = "_";

    /**
     * Redis key prefix constants.
     */
    public static class RedisKey {
        public static final String LOGIN_TOKEN = "login_token_";
    }


    /**
     * Auth-related constants like interceptor exclude paths.
     */
    public static class Auth {
        public static final String[] EXCLUDE_PATHS = {
                "/api/user/login",
                "/api/user/register",
                "/api/attractions/state",
                "/api/attractions/top-rated",
                "/api/attractions/count",
                "/api/attractions/*",
                "/api/hotels/by-attraction/*"
        };
    }


}
