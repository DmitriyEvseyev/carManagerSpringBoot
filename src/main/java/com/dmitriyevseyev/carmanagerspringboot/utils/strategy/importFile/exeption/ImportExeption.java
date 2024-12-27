package com.dmitriyevseyev.carmanagerspringboot.utils.strategy.importFile.exeption;

public class ImportExeption extends Exception {
    public ImportExeption(){
        super();
    }

    public ImportExeption(String message){
        super(message);
    }
}