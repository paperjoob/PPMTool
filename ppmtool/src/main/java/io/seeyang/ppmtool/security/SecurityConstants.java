package io.seeyang.ppmtool.security;


public class SecurityConstants {

    // abstracting certain values we might change in the future - imported to Security Config
    public static final String SIGN_UP_URLS = "/api/users/**";

    // h2 - url to be enabled
    public static final String H2_URL = "h2-console";

    // generates JSON Web Token
    public static final String SECRET_JWT = "SecretKeyToGenerateJWTs";

    // token prefix
    public static final String TOKEN_PREFIX = "Bearer ";

    // header string
    public static final String HEADER_STRING = "Authorization";

    // token expiration time = 60 seconds
    public static final long EXPIRATION_TIME = 300_000; // 5 minutes
}
