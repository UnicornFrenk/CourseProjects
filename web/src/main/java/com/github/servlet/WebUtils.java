package com.github.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


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

}
