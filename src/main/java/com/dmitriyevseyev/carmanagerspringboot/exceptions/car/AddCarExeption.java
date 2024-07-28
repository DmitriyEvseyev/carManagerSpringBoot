package com.dmitriyevseyev.carmanagerspringboot.exceptions.car;

public class AddCarExeption extends Exception {
    public AddCarExeption() {
        super();
    }

    public AddCarExeption(String message) {
        super(message);
    }
}
