package com.dmitriyevseyev.carmanagerspringboot.exceptions.car;

public class NotFoundException extends Exception {

    public NotFoundException() {
        super();
    }

    public NotFoundException(String message) {
        super(message);
    }
}
