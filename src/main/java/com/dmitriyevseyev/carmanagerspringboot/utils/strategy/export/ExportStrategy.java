package com.dmitriyevseyev.carmanagerspringboot.utils.strategy.export;


import com.dmitriyevseyev.carmanagerspringboot.exceptions.car.NotFoundException;
import com.dmitriyevseyev.carmanagerspringboot.utils.ExportDTO;

import java.util.List;

public interface ExportStrategy {
    void collectExportIds(ExportDTO exportList, List<Integer> ids) throws ExportExeption, NotFoundException;
}