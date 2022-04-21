package com.example.authservice.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Optional;

public class CookieUtil {
    /**
     * 쿠키 이름을 받아 쿠키값 반환 🍪
     * @return
     */
    public static String getCookie(HttpServletRequest request, String key) throws Exception{
        Cookie[] cookies = request.getCookies();
        if(key == null || cookies == null) return "";
        String value = "";
        for (int i=0; i< cookies.length; i++){
            if (cookies[i].getName().equals(key)){
                value = java.net.URLDecoder.decode(cookies[i].getValue(), "UTF-8");
            }
        }
        return value;
    }
}
