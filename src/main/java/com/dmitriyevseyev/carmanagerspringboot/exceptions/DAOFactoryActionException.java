package com.dmitriyevseyev.carmanagerspringboot.exceptions;

public class DAOFactoryActionException extends Throwable {
    public DAOFactoryActionException() {
        super();
    }

    public DAOFactoryActionException(String message) {
        super(message);
    }
}