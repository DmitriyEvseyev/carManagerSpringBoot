package com.dmitriyevseyev.carmanagerspringboot.utils.strategy.importFile;

public class ImportExeption extends Exception {
    public ImportExeption(){
        super();
    }

    public ImportExeption(String message){
        super(message);
    }
}