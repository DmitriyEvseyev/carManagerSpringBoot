package com.dmitriyevseyev.carmanagerspringboot.utils.strategy.export.car;

import com.dmitriyevseyev.carmanagerspringboot.models.entity.CarDealershipEntity;
import com.dmitriyevseyev.carmanagerspringboot.models.entity.CarEntity;
import com.dmitriyevseyev.carmanagerspringboot.services.CarService;
import com.dmitriyevseyev.carmanagerspringboot.services.DealerService;
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
public class CarExportStrategyWithDealer implements ExportStrategy {
    private ConverterDTO converterDTO;
    private CarService carService;
    private DealerService dealerService;

    @Override
    public void collectExportIds(ExportDTO exportList, List<Integer> ids) throws ExportExeption {
        List<CarEntity> carListEntity = new ArrayList<>();
        for (Integer id : ids) {
            carListEntity.add(carService.getCarEntity(id));
        }
        exportList.addCars(converterDTO.convertCarEntityListToCarDTOList(carListEntity));

        List<CarDealershipEntity> dealerListEntity = new ArrayList<>();
        dealerListEntity.add(dealerService.getDealerEntity(carListEntity.get(0).getDealer().getId()));

        exportList.addDelers(converterDTO.convertDealerEntityListToDealerDTOList(dealerListEntity));


        System.out.println("CarExportStrategyWithDealer - " + exportList);

    }
}
