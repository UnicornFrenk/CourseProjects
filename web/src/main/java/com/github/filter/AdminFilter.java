package com.github.filter;

import com.github.hib.entity.Role;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static com.github.hib.entity.Role.ADMIN;
import static com.github.servlet.WebUtils.findCookie;
import static com.github.servlet.WebUtils.redirect;

@WebFilter()
public class AdminFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req= (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        Optional<Cookie> cookie = findCookie("myAppUserCookie", req);
        if(cookie.isPresent()) {
            Role role = Role.valueOf((String) req.getSession().getAttribute("role"));
            if (role.equals(ADMIN)) {
                redirect("adminHome", req, resp);
            } else {
                filterChain.doFilter(req, resp);
            }
        }else {
            redirect("login",req,resp);
        }
    }
    }
