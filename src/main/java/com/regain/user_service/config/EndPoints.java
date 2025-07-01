package com.regain.user_service.config;

public class EndPoints {
    public static final String[] PUBLIC_GET = {
            "/user-api/**"
    };

    public static final String[] PUBLIC_POST = {
            "/user-api/**"
    };

    public static final String[] ADMIN_HTTP = {
            "/admin-api/**"
    };
    public static final String FRONT_END_HOST = "http://localhost:3000";
}
