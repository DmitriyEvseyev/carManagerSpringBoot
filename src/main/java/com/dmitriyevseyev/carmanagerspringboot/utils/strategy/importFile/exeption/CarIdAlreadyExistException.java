package com.dmitriyevseyev.carmanagerspringboot.utils.strategy.importFile.exeption;

public class CarIdAlreadyExistException extends Exception {
    public CarIdAlreadyExistException(){
        super();
    }

    public CarIdAlreadyExistException(String message){
        super(message);
    }
}