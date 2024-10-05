package com.dmitriyevseyev.carmanagerspringboot.utils.strategy.importFile.exeption;

public class JSONValidatorExeption extends Exception {
    public JSONValidatorExeption() {
    }

    public JSONValidatorExeption(String message) {
        super(message);
    }
}
