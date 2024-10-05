package com.dmitriyevseyev.carmanagerspringboot.utils.exeptions;

public class UserExistExeption extends RuntimeException{
    public UserExistExeption() {
        super();
    }

    public UserExistExeption(String message) {
        super(message);
    }
}


