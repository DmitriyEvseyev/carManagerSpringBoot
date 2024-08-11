package com.dmitriyevseyev.carmanagerspringboot.utils.strategy.export.dealer;

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
public class DealerExportStrategyWithCar implements ExportStrategy {
    private ConverterDTO converterDTO;
    private DealerService dealerService;
    private CarService carService;

    @Override
    public void collectExportIds(ExportDTO exportList, List<Integer> ids) throws ExportExeption {
        List<CarDealershipEntity> dealerEntityList = new ArrayList<>();
        for (Integer id : ids) {
            dealerEntityList.add(dealerService.getDealerEntity(id));
        }
        exportList.addDelers(converterDTO.convertDealerEntityListToDealerDTOList(dealerEntityList));

        List<CarEntity> carEntityList = new ArrayList<>();
        for (Integer id : ids) {
            carEntityList.addAll(carEntityList.size(), carService.getCarEntityList(id));
        }


        exportList.addCars(converterDTO.convertCarEntityListToCarDTOList(carEntityList));


        System.out.println("DealerExportStrategyWithCar - " + exportList);
    }
}
