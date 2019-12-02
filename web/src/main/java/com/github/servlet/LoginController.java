package com.github.servlet;


import com.github.PersonService;
import com.github.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.*;

@Controller
@RequestMapping("/shop")
public class LoginController {

    private static final Logger log = LoggerFactory.getLogger(
            LoginController.class);

    private PersonService personService;

    public LoginController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/login")
    public String doGet(HttpSession session) {
        Object authUser = session.getAttribute("authUser");
        if (authUser == null) {
            return "login";
        }
        return "redirect:/authUser";
    }


    @PostMapping("/login")
    public String doPost(HttpServletRequest request) {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        Person user = new Person(login, password);
        personService.getByLogin(user.getLogin());


        if (user != null) {
            request.getSession().setAttribute("user", user);
            request.getSession().setAttribute("login", login);
            request.getSession().setAttribute("password", password);

            if (request.getSession().getAttribute("user") != null) {
                return "redirect:/authUser";

            } else {

                return "redirect:/login";
            }
        }
        return "redirect:/authUser";

    }
}
