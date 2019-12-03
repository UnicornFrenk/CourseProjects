package com.github.servlet;


import com.github.PersonService;
import com.github.impl.DefaultPersonService;
import com.github.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/registration")
public class RegistrationController extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(
            RegistrationController.class);

    @Autowired
    PersonService personService;

    @Autowired
    public RegistrationController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping()
    public String registrationForm(HttpServletRequest request) {

        return "/registration";
    }

    @PostMapping()
    public String makeRegistration(HttpServletRequest rq) {
        String login = rq.getParameter("login");
        rq.setAttribute("login", login);
        String password = rq.getParameter("password");
        rq.setAttribute("password", password);


        personService.createPerson(new Person(login, password));
        return "redirect:/login";
    }
}
