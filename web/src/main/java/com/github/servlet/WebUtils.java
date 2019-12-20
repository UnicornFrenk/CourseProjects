package com.github.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.github.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;


public class WebUtils {

    private static final Logger log = LoggerFactory.getLogger(WebUtils.class);

    public static void forword(String page, HttpServletRequest rq, HttpServletResponse rs) {
        try {
            rq.getRequestDispatcher("/" + page + ".jsp").forward(rq, rs);
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }

    }


    public static void redirect(String page, HttpServletRequest req, HttpServletResponse resp) {
        try {
            resp.sendRedirect(req.getContextPath() + "/" + page);
        } catch (IOException e) {
            log.warn("Exception during redirect to page {}", page);
            throw new RuntimeException();
        }
    }


    public static Optional<Cookie> findCookie(String cookieName, HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            return Arrays.stream(cookies)
                         .filter(c -> c.getName().equals(cookieName))
                         .findAny();
        } else {
            return Optional.empty();
        }
    }

    public static boolean isUserNotAuth(Authentication authentication) {
        return authentication == null || "anonymousUser".equals(authentication.getPrincipal());
    }

    public static List<GrantedAuthority> getAuthorities(Person person) {
        String role = "ROLE_" + person.getRole().name();
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add((GrantedAuthority) () -> role);
        return grantedAuthorities;
    }

}
