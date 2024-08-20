package com.dmitriyevseyev.carmanagerspringboot.utils.strategy.export.dealer;


import com.dmitriyevseyev.carmanagerspringboot.models.CarDealership;
import com.dmitriyevseyev.carmanagerspringboot.models.entity.CarDealershipEntity;
import com.dmitriyevseyev.carmanagerspringboot.services.DealerService;
import com.dmitriyevseyev.carmanagerspringboot.utils.ConverterDTO;
import com.dmitriyevseyev.carmanagerspringboot.utils.ExportDTO;
import com.dmitriyevseyev.carmanagerspringboot.utils.NotFoundException;
import com.dmitriyevseyev.carmanagerspringboot.utils.strategy.export.ExportExeption;
import com.dmitriyevseyev.carmanagerspringboot.utils.strategy.export.ExportStrategy;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class DealerExportStrategy implements ExportStrategy {
    private ConverterDTO converterDTO;
    private DealerService dealerService;

    @Override
    public void collectExportIds(ExportDTO exportList, List<Integer> ids) throws ExportExeption, NotFoundException {
        List<CarDealership> dealersList = new ArrayList<>();
        for (Integer id : ids) {
            dealersList.add(dealerService.getDealer(id));
        }
        exportList.addDelers(converterDTO.convertDealersListToDealersDTOList(dealersList));


        System.out.println("DealerExportStrategy without cars - " + exportList);
    }
}
