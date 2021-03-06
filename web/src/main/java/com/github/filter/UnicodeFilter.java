package com.github.filter;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*")
public class UnicodeFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String locale = req.getParameter("locale");

        if (locale == null) {
            locale = (String) req.getSession().getAttribute("locale");
            if (locale == null) {
                req.getSession().setAttribute("locale", "ru");
            }
        } else {
            req.getSession().setAttribute("locale", locale);
        }


        filterChain.doFilter(req, resp);
    }
}
