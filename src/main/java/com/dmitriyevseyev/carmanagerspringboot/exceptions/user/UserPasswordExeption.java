package com.dmitriyevseyev.carmanagerspringboot.exceptions.user;

public class UserPasswordExeption extends Exception {

    public UserPasswordExeption() {
        super();
    }

    public UserPasswordExeption(String message) {
        super(message);
    }
}

