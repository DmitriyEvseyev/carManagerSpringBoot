package com.dmitriyevseyev.carmanagerspringboot.utils.strategy.importFile;

import com.dmitriyevseyev.carmanagerspringboot.utils.strategy.importFile.exeption.ImportExeption;

public interface ImportStrategy <T> {
    void store(T object) throws ImportExeption;
}