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


    //create
    @GetMapping("/authUser")
    public String getByLogin(HttpServletRequest request){

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
    @PostMapping("/updatePassword")
    public String updateUserPassword(HttpServletRequest request){
        return "users";
    }

    //delete
    @PostMapping ("/deleteuser")
    public String deleteUser(HttpServletRequest request ) {

        Integer id = Integer.parseInt(request.getParameter("deleteUser"));
        personService.deletePerson(id);
        return "redirect:/users";
    }


    //getAll
    @GetMapping("/users")
    public String showAllUsers(HttpServletRequest request) {
        List<Person> users = personService.getAll();
        request.setAttribute("users", users);
        return "users";
    }

}
