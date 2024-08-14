package com.dmitriyevseyev.carmanagerspringboot.utils.strategy.importFile;

public interface ImportStrategy <T> {
    void store(T object) throws ImportExeption;
}