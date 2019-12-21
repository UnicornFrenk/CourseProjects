package com.github.servlet;

import com.github.PersonService;
import com.github.hib.entity.Role;
import com.github.model.Item;
import com.github.model.Person;
import com.github.rq.CreatePersonRq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(
            UserController.class);

    @Autowired
    private PersonService personService;

    public UserController(PersonService personService) {
        this.personService = personService;
    }


    @GetMapping("/authUser")
    public String getByLogin(HttpServletRequest request) {

        String login = request.getParameter("login");
        request.setAttribute("login", login);
        String password = request.getParameter("password");
        request.setAttribute("password", password);

        personService.getByLogin(login);
        return "authUser";
    }


    //read
    @PostMapping("/authUser")
    public String read(HttpServletRequest request) {
        String login = request.getParameter("login");
        request.setAttribute("login", login);
        personService.getByLogin(login);
        return "authUser";
    }


    //update
    @GetMapping("/update")
    public String updatePerson(HttpServletRequest request) {
        Integer id = Integer.valueOf(request.getParameter("id"));
        Person person = personService.getById(id);
        request.setAttribute("users", person);

        return "update";
    }


    @PostMapping("/update")
    public String updateUserPassword(HttpServletRequest request) {
        Integer id = Integer.parseInt(request.getParameter("id"));
        String login = request.getParameter("login");
        String password = request.getParameter("pass");
        Person person =personService.getById(id);
        request.setAttribute("users", person);
        personService.updatePerson(id, password);
        log.info("person updated:{} at {}", id, LocalDateTime.now());
        return "redirect:/users";
    }

    //delete
    @PostMapping("/deleteuser")
    public String deleteUser(HttpServletRequest request) {

        Integer id = Integer.parseInt(request.getParameter("deleteUser"));
        personService.deletePerson(id);
        return "redirect:/users";
    }


    //getAll
    @GetMapping("/users")
    public String showAllUsers(HttpServletRequest request, UsernamePasswordAuthenticationToken authentication) {
        List<Person> users = personService.getAll();
        request.setAttribute("users", users);
        return "users";
    }

    @PostMapping()
    @Secured("ADMIN")
    public String create(CreatePersonRq rq, UsernamePasswordAuthenticationToken authentication) {
        String login = rq.getLogin();
        String pasword = rq.getPassword();
        Role role = rq.getRole();
        int id = personService.createPerson(
                new Person(null, login, pasword, role));

        return "redirect:/users";
    }

}
