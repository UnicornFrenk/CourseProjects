package com.github.servlet;


import com.github.PersonService;
import com.github.model.Person;
import com.github.rq.LoginRq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.*;
import java.net.Authenticator;

import static com.github.servlet.WebUtils.getAuthorities;
import static com.github.servlet.WebUtils.isUserNotAuth;

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
        session.getAttribute("user");
        Authentication authentication = SecurityContextHolder.getContext()
                                                             .getAuthentication();
        if (isUserNotAuth(authentication)) {
            return "login";
        } else {
            return "redirect:/authUser";
        }
    }


    @PostMapping("/login")
    public String login(LoginRq loginRq, ModelMap modelMap) {
        String login = loginRq.getLogin();
        String password = loginRq.getPassword();
        Person user = new Person(login, password);
        Person person = personService.getByLogin(user.getLogin());

        if (person != null) {
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    person, null, getAuthorities(person));
            SecurityContextHolder.getContext()
                                 .setAuthentication(authentication);
            return "redirect:/authUser";

        } else {
            modelMap.addAttribute("error", true);  //todo
            log.warn("user {} couldn't log in with password {}", login,
                     password);
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
