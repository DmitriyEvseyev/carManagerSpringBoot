package com.dmitriyevseyev.carmanagerspringboot.controllers;

import com.dmitriyevseyev.carmanagerspringboot.services.UserService;
import com.dmitriyevseyev.carmanagerspringboot.utils.PasswordHashGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/")
public class UserController {
    private UserService userService;
    private PasswordHashGenerator passwordHashGenerator;

    @Autowired
    public UserController(UserService userService, PasswordHashGenerator passwordHashGenerator) {
        this.userService = userService;
        this.passwordHashGenerator = passwordHashGenerator;
    }

    @GetMapping
    public String start() {
        return "user/login";
    }

    @GetMapping("/new")
    public String reistration() {
        return "user/newUser";
    }

    @PostMapping("/log")
    public String loginUser(@RequestParam("name") String userName,
                            @RequestParam("password") String userPassword) {
        boolean isCorrect = false;
        String password = passwordHashGenerator.getHashPassword(userPassword);
        String passwordServer = userService.getUserPassword(userName);
        if (passwordServer != null) {
            String passwordServerHash = passwordHashGenerator.getHashPassword(passwordServer);
            if (password.equals(passwordServerHash)) {
                isCorrect = true;
            }
        }
        if (isCorrect == true) {
            return "forward:/dealer/getDealers";
        }
        return "user/error";
    }

    @PostMapping("/create")
    public String addUser(@RequestParam("name") String userName,
                          @RequestParam("password") String userPassword,
                          @RequestParam("password2") String userPassword2) {
        System.out.println("USERNAME - " + userName);

        if (!userPassword.equals(userPassword2)) {
            return "user/passError";
        } else if (userService.getUserName(userName)) {
            return "user/userExistError";
        } else {
            userService.addUser(userName, userPassword);
            return "forward:/dealer/getDealers";
        }
    }
}

