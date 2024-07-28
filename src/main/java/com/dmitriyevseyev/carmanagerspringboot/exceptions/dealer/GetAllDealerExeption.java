package com.dmitriyevseyev.carmanagerspringboot.exceptions.dealer;

public class GetAllDealerExeption extends Exception {
    public GetAllDealerExeption() {
        super();
    }

    public GetAllDealerExeption(String message) {
        super(message);
    }
}
