package com.example.forestofhabits.util;

import com.example.forestofhabits.config.jwt.JwtAuthentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class Util {
    public static String API = "/api";

    public static JwtAuthentication getAuthInfo() {
        return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }
}
