package com.vaishnavi.sql.injection.controller;

import com.vaishnavi.sql.injection.repository.LoginRepository;
import com.vaishnavi.sql.injection.service.ConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginRestController {

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private ConnectionService connectionService;

    @PostMapping("/insecure-login")
    public ModelAndView insecureLogin(@RequestParam("username")String username, @RequestParam("password")String password) {

        // Unsafe query which uses string concatenation
        String query = "select * from credentials where username = '" + username + "' and password = '" + password + "'";

        boolean isLoginSuccessful = loginRepository.insecureLogin(query);

        return new ModelAndView("redirect:/" + ((isLoginSuccessful) ? "home.html" : "login.html?error=1"));
    }

    @PostMapping("/secure-login")
    public ModelAndView secureLogin(@RequestParam("username")String username, @RequestParam("password")String password) {

        String query = "select * from credentials where username = ? and password = ?";

        boolean isLoginSuccessful = loginRepository.secureLogin(query, username, password);

        return new ModelAndView("redirect:/" + ((isLoginSuccessful) ? "home.html" : "securelogin.html?error=1"));
    }
}

