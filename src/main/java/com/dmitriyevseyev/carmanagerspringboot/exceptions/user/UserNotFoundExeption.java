package com.dmitriyevseyev.carmanagerspringboot.exceptions.user;

public class UserNotFoundExeption extends Exception {

    public UserNotFoundExeption() {
        super();
    }

    public UserNotFoundExeption(String message) {
        super(message);
    }
}

