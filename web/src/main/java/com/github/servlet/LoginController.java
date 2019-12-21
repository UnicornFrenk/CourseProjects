package com.github.servlet;


import com.github.PersonService;
import com.github.model.Person;
import com.github.rq.LoginRq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.net.Authenticator;
import java.util.Arrays;
import java.util.List;

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
        Person person = personService.getByLogin(login);

        if (person != null) {
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    person, null, getAuthorities());
            SecurityContextHolder.getContext()
                                 .setAuthentication(authentication);
            return "redirect:/authUser";

        } else {
            log.warn("user {} couldn't log in with password {}", login,
                     password);
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest rq) {

        SecurityContextHolder.clearContext();
        try {
            rq.logout();
        } catch (ServletException e) {
            throw new RuntimeException();
        }
        return "login";
    }

    private List<GrantedAuthority> getAuthorities() {
        return Arrays.asList((GrantedAuthority) () -> "ROLE_USER",
                             (GrantedAuthority) () -> "ROLE_ADMIN");
    }
}
