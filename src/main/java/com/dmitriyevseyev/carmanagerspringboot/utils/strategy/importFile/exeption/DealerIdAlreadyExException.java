package com.dmitriyevseyev.carmanagerspringboot.utils.strategy.importFile.exeption;

public class DealerIdAlreadyExException  extends Exception {
    public DealerIdAlreadyExException(){
        super();
    }

    public DealerIdAlreadyExException(String message){
        super(message);
    }
}
