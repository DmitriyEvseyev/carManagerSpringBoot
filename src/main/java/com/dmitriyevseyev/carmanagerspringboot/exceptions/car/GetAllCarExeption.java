package com.dmitriyevseyev.carmanagerspringboot.exceptions.car;

public class GetAllCarExeption extends Exception {
    public GetAllCarExeption() {
        super();
    }

    public GetAllCarExeption(String message) {
        super(message);
    }
}
