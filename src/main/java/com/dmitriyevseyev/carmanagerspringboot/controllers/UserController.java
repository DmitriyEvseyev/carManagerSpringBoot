package com.dmitriyevseyev.carmanagerspringboot.controllers;

import com.dmitriyevseyev.carmanagerspringboot.models.CarDealership;
import com.dmitriyevseyev.carmanagerspringboot.models.User;
import com.dmitriyevseyev.carmanagerspringboot.services.UserService;
import com.dmitriyevseyev.carmanagerspringboot.utils.PasswordHashGenerator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@AllArgsConstructor
@RequestMapping("/")
public class UserController {
    private UserService userService;
    private PasswordHashGenerator passwordHashGenerator;

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
        String passwordServer = userService.getPassword(userName);
        if (passwordServer != null) {
            String passwordServerHash = passwordHashGenerator.getHashPassword(passwordServer);
            if (password.equals(passwordServerHash)) {
                isCorrect = true;
            }
        }
        if (isCorrect == true) {

            System.out.println("forward:/dealer");

            return "forward:/dealer/getAllDealer";
        }
        return "user/error";
    }

    @PostMapping("/create")
    public String regUser(@RequestParam("name") String userName,
                          @RequestParam("password") String userPassword,
                          @RequestParam("password2") String userPassword2) {
        System.out.println(111 + " - /create");
        System.out.println("USERNAME - " + userName);

        if (!userPassword.equals(userPassword2)) {
          return "user/passError";
        } else if (userService.isUserExistServer(userName)) {
            return "user/userExistError";
        } else {
            userService.addUser(userName, userPassword);
            return "forward:/dealer/getAllDealer";
        }
    }

//
//    public boolean isUserNameExistServer(String userName) throws UserNotFoundExeption {
//        boolean isNameExist = false;
//        isNameExist = postgreSQLUserDAO.isUserExist(userName);
//        return isNameExist;
//    }

    public void addUser(User user) {
        userService.createUser(user);
    }
}

