package com.github.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

@Controller
public class MainPageController extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(
            MainPageController.class);

    @GetMapping()
    protected String mainPage(HttpServletRequest request){
        request.getContextPath();
        log.info("main page {} logged");
        return "redirect:/index";

    }

}
