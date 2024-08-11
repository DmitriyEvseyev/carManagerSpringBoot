package com.dmitriyevseyev.carmanagerspringboot.utils.strategy.export.car;


import com.dmitriyevseyev.carmanagerspringboot.controllers.CarController;
import com.dmitriyevseyev.carmanagerspringboot.controllers.DealerController;
import com.dmitriyevseyev.carmanagerspringboot.exceptions.DAOFactoryActionException;
import com.dmitriyevseyev.carmanagerspringboot.exceptions.car.NotFoundException;
import com.dmitriyevseyev.carmanagerspringboot.models.Car;
import com.dmitriyevseyev.carmanagerspringboot.models.CarDealership;
import com.dmitriyevseyev.carmanagerspringboot.utils.ExportDTO;
import com.dmitriyevseyev.carmanagerspringboot.utils.strategy.export.ExportExeption;
import com.dmitriyevseyev.carmanagerspringboot.utils.strategy.export.ExportStrategy;

import java.util.ArrayList;
import java.util.List;

public class CarExportStrategyWithDealer implements ExportStrategy {
    @Override
    public void collectExportIds(ExportDTO exportList, List<Integer> ids) throws ExportExeption {
//        ConverterDTO converterDTO = ConverterDTO.getInstance();
//        List<Car> carList = new ArrayList<>();
//        try {
//            CarController carController = CarController.getInstance();
//            carList = carController.getCars(ids);
//        } catch (NotFoundException | DAOFactoryActionException e) {
//            throw new ExportExeption(e.getMessage());
//        }
//
//        exportList.addCars(converterDTO.convertCarToCarDTO(carList));
//
//        List<CarDealership> dealerList = new ArrayList<>();
//        try {
//            DealerController controllerDealer = DealerController.getInstance();
//            dealerList.add(controllerDealer.getDealer(carList.get(0).getDealer().getId()));
//        } catch (NotFoundException | DAOFactoryActionException e) {
//            throw new ExportExeption(e.getMessage());
//        }
//        exportList.addDelers(converterDTO.convertDealerToDealerDTO(dealerList));
//
//
//        System.out.println("CarExportStrategyWithDealer - " + exportList);
//
   }
}
