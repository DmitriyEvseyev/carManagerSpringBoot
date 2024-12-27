package com.dmitriyevseyev.carmanagerspringboot.utils.strategy.importFile.exeption;

public class DealerIdAlreadyExistException extends Exception {
    public DealerIdAlreadyExistException() {
        super();
    }

    public DealerIdAlreadyExistException(String message) {
        super(message);
    }
}