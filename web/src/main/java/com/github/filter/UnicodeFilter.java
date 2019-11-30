package com.github.filter;

import com.sun.javaws.Globals;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

@WebFilter("/*")
public class UnicodeFilter implements Filter {
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        servletRequest.setCharacterEncoding("UTF-8");
//        Locale locale = servletRequest.getLocale();
//        if (locale == null) {
//            ((HttpServletRequest) servletRequest).getSession().setAttribute(
//                    "locale", "en_US");
//        }
//        filterChain.doFilter(servletRequest, servletResponse);
//        servletResponse.setCharacterEncoding("UTF-8");
//
//    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String locale = req.getParameter("locale");

        if (locale==null){
            locale = (String) req.getSession().getAttribute("locale");
            if (locale==null) {
                req.getSession().setAttribute("locale", "ru_RU");
            }
        }else {
            req.getSession().setAttribute("locale", locale);
        }


        filterChain.doFilter(req,resp);
    }
}
