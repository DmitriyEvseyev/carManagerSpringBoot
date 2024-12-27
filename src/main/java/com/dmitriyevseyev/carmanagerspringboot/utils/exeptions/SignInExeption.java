package com.dmitriyevseyev.carmanagerspringboot.utils.exeptions;

public class SignInExeption extends RuntimeException{
    public SignInExeption() {
        super();
    }

    public SignInExeption(String message) {
        super(message);
    }
}
