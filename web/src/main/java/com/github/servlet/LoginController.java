package com.github.servlet;


import com.github.PersonService;
import com.github.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.*;

@Controller
@RequestMapping("")
public class LoginController {

    private static final Logger log = LoggerFactory.getLogger(
            LoginController.class);

    @Autowired
    private PersonService personService;

    public LoginController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/login")
    public String login(HttpSession session) {
        Object authUser = session.getAttribute("user");
        if (authUser == null) {
            return "login";
        }
        return "redirect:/authUser";
    }


    @PostMapping("/login")
    public String login(HttpServletRequest request) {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        Person user = new Person(login, password);
        personService.getByLogin(user.getLogin());
        request.getSession().setAttribute("user", user);

        if (request.getSession().getAttribute("user") != null) {
            return "redirect:/authUser";

        } else {

            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {

        session.removeAttribute("user");
        session.invalidate();
        return "redirect:/login";

    }

}
