package com.dmitriyevseyev.carmanagerspringboot.exceptions.car;

public class DeleteCarExeption extends Exception{

    public DeleteCarExeption() {
        super();
    }

    public DeleteCarExeption(String message) {
        super(message);
    }
}