package com.github.impl;

import com.github.hib.dao.PersonDao;
import com.github.hib.dao.impl.DefaultPersonDao;
import com.github.model.Person;
import com.github.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultSecurityService implements SecurityService {

    @Autowired
       private PersonDao personDao;

        public Person userName (String login, String password){
            Person user = personDao.getByLogin(login);
            if (user == null) {
                return null;
            }
            if (user.getPassword().equals(password)) {
                return user;
            } else {
                return null;
            }
        }
    }

