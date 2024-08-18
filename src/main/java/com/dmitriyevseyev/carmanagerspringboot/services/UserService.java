package com.dmitriyevseyev.carmanagerspringboot.services;

import com.dmitriyevseyev.carmanagerspringboot.models.entity.UserEntity;
import com.dmitriyevseyev.carmanagerspringboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void addUser(String userName, String userPassword) {
        userRepository.save(UserEntity.builder().
                userName(userName).
                password(userPassword).
                build());
    }

    public String getUserPassword(String name) {
        String password = null;
        if (userRepository.findByUserName(name) != null) {
            password = userRepository.findByUserName(name).getPassword();
        }
        return password;
    }

    public boolean getUserName(String name) {
        boolean isNameExist = false;
        if (userRepository.findByUserName(name) != null) {
            isNameExist = true;
        }
        return isNameExist;
    }
}
