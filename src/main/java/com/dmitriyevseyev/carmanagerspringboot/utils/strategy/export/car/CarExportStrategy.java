package com.dmitriyevseyev.carmanagerspringboot.utils.strategy.export.car;

import com.dmitriyevseyev.carmanagerspringboot.models.entity.CarEntity;
import com.dmitriyevseyev.carmanagerspringboot.services.CarService;
import com.dmitriyevseyev.carmanagerspringboot.utils.ConverterDTO;
import com.dmitriyevseyev.carmanagerspringboot.utils.ExportDTO;
import com.dmitriyevseyev.carmanagerspringboot.utils.strategy.export.ExportExeption;
import com.dmitriyevseyev.carmanagerspringboot.utils.strategy.export.ExportStrategy;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class CarExportStrategy implements ExportStrategy {
    private ConverterDTO converterDTO;
    private CarService carService;

    @Override
    public void collectExportIds(ExportDTO exportList, List<Integer> ids) throws ExportExeption {

        List<CarEntity> carListEntity = new ArrayList<>();
        for (Integer id : ids) {
            carListEntity.add(carService.getCarEntity(id));
        }
        exportList.addCars(converterDTO.convertCarEntityListToCarDTOList(carListEntity));


        System.out.println("CarExportStrategy without dealer - " + exportList);


    }
}
