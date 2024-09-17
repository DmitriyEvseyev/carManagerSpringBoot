package com.dmitriyevseyev.carmanagerspringboot.utils.exeptions;

public class NotFoundException extends RuntimeException {

    public NotFoundException() {
        super();
    }

    public NotFoundException(String message) {
        super(message);
    }
}
