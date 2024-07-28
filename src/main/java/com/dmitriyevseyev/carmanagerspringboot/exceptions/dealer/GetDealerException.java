package com.dmitriyevseyev.carmanagerspringboot.exceptions.dealer;

public class GetDealerException extends Exception {
    public GetDealerException() {
        super();
    }

    public GetDealerException (String message) {
        super(message);
    }
}

