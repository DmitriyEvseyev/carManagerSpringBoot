package com.dmitriyevseyev.carmanagerspringboot.utils.strategy.importFile;

public class JSONValidatorExeption extends Exception {
    public JSONValidatorExeption() {
    }

    public JSONValidatorExeption(String message) {
        super(message);
    }
}
