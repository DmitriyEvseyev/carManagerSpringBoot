package com.dmitriyevseyev.carmanagerspringboot.restControllers;

import com.dmitriyevseyev.carmanagerspringboot.models.dto.CarDealershipDTO;
import com.dmitriyevseyev.carmanagerspringboot.models.dto.UserDTO;
import com.dmitriyevseyev.carmanagerspringboot.services.DealerService;
import com.dmitriyevseyev.carmanagerspringboot.services.UserService;
import com.dmitriyevseyev.carmanagerspringboot.utils.ConverterDTO;
import com.dmitriyevseyev.carmanagerspringboot.utils.PasswordHashGenerator;
import com.dmitriyevseyev.carmanagerspringboot.utils.exeptions.CreatedExeption;
import com.dmitriyevseyev.carmanagerspringboot.utils.exeptions.NotFoundException;
import com.dmitriyevseyev.carmanagerspringboot.utils.exeptions.SignInExeption;
import com.dmitriyevseyev.carmanagerspringboot.utils.exeptions.UserExistExeption;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserRESTController {
    private final UserService userService;
    final private DealerService dealerService;
    private final PasswordHashGenerator passwordHashGenerator;
    final private ConverterDTO converterDTO;

    @Autowired
    public UserRESTController(UserService userService, DealerService dealerService, PasswordHashGenerator passwordHashGenerator, ConverterDTO converterDTO) {
        this.userService = userService;
        this.dealerService = dealerService;
        this.passwordHashGenerator = passwordHashGenerator;
        this.converterDTO = converterDTO;
    }

    @GetMapping
    public String start() {
        return "user/login";
    }

    @GetMapping("/new")
    public String reistration() {
        return "user/newUser";
    }

    @PostMapping("/signIn")
    public List<CarDealershipDTO> signIn(@RequestBody @Valid UserDTO userDTO, BindingResult bindingResult) {
        log.info("UserName - {}.", userDTO.getUserName());
        if (bindingResult.hasErrors()) {
            StringBuilder errorMes = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMes.append("-").append(error.getDefaultMessage()).append("; ");
            }
            throw new CreatedExeption(errorMes.toString());
        }

        boolean isCorrect = false;
        String password = passwordHashGenerator.getHashPassword(userDTO.getPassword());
        String passwordServer = userService.getUserPassword(userDTO.getUserName());
        if (passwordServer != null) {
            String passwordServerHash = passwordHashGenerator.getHashPassword(passwordServer);
            if (password.equals(passwordServerHash)) {
                isCorrect = true;
            }
        }
        if (isCorrect) {
            List<CarDealershipDTO> dealerDTOList = converterDTO.convertDealersListToDealersDTOList(dealerService.getDealersList());
            log.info("DealerDTOList - {}", dealerDTOList);
            return dealerDTOList;
        } else {
            throw new SignInExeption("Invalid Username or Password!");
        }
    }

    @PostMapping("/signUp")
    public List<CarDealershipDTO> signUp(@RequestBody @Valid UserDTO userDTO, BindingResult bindingResult) {
        log.info("UserName - {}.", userDTO.getUserName());
        if (bindingResult.hasErrors()) {
            StringBuilder errorMes = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMes.append("-").append(error.getDefaultMessage()).append("; ");
            }
            throw new CreatedExeption(errorMes.toString());
        }

        if (userService.getUserName(userDTO.getUserName())) {
            throw new UserExistExeption("A user with that name already exists!");
        }
        userService.addUser(userDTO.getUserName(), userDTO.getPassword());
        List<CarDealershipDTO> dealerDTOList = converterDTO.convertDealersListToDealersDTOList(dealerService.getDealersList());
        log.info("DealerDTOList - {}", dealerDTOList);
        return dealerDTOList;

    }
}


