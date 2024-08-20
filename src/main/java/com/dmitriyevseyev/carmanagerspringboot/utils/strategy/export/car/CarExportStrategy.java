package com.dmitriyevseyev.carmanagerspringboot.utils.strategy.export.car;

import com.dmitriyevseyev.carmanagerspringboot.utils.NotFoundException;
import com.dmitriyevseyev.carmanagerspringboot.models.Car;
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
    public void collectExportIds(ExportDTO exportList, List<Integer> ids) throws ExportExeption, NotFoundException {

        List<Car> carsList = new ArrayList<>();
        for (Integer id : ids) {
            carsList.add(carService.getCarById(id));
        }
        exportList.addCars(converterDTO.convertCarsListToCarsDTOList(carsList));


        System.out.println("CarExportStrategy without dealer - " + exportList);


    }
}
