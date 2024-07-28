package com.dmitriyevseyev.carmanagerspringboot.exceptions.car;

public class CarIdAlreadyExistException extends Exception{
    public CarIdAlreadyExistException() {
        super();
    }

    public CarIdAlreadyExistException(String message) {
        super(message);
    }
}