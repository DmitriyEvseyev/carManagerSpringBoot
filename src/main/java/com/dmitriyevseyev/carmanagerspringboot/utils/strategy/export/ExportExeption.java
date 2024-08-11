package com.dmitriyevseyev.carmanagerspringboot.utils.strategy.export;

public class ExportExeption extends Exception {
    public ExportExeption(){
    super();
}

    public ExportExeption (String message){
        super(message);
    }
}
