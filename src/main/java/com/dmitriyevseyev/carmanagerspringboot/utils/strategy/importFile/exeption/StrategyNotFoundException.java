package com.dmitriyevseyev.carmanagerspringboot.utils.strategy.importFile.exeption;

public class StrategyNotFoundException extends Exception {
    public StrategyNotFoundException() {
    }

    public StrategyNotFoundException(String message) {
        super(message);
    }
}
