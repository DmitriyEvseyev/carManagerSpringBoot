package com.dmitriyevseyev.carmanagerspringboot.utils;

import com.dmitriyevseyev.carmanagerspringboot.models.dto.CarDTO;
import com.dmitriyevseyev.carmanagerspringboot.models.dto.CarDealershipDTO;
import com.dmitriyevseyev.carmanagerspringboot.models.entity.CarDealershipEntity;
import com.dmitriyevseyev.carmanagerspringboot.models.entity.CarEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class ConverterDTO {

    public List<CarDTO> convertCarEntityListToCarDTOList(List<CarEntity> carList) {
        List<CarDTO> carDTOList = new ArrayList<>();

        for (CarEntity carEntity : carList) {
            CarDTO carDTO = CarDTO.builder().
                    id(carEntity.getId()).
                    idDealer(carEntity.getDealer().getId()).
                    name(carEntity.getName()).
                    date(carEntity.getDate()).
                    color(carEntity.getColor()).
                    isAfterCrash(carEntity.isAfterCrash()).
                    build();
            carDTOList.add(carDTO);
        }


        System.out.println("convertCarEntityListToCarDTOList - " + carDTOList);

        return carDTOList;
    }

    public List<CarDealershipDTO> convertDealerEntityListToDealerDTOList(List<CarDealershipEntity> dealerList) {
        List<CarDealershipDTO> dealerDTOList = new ArrayList<>();

        for (CarDealershipEntity dealerEntity : dealerList) {
            CarDealershipDTO dealerDTO = CarDealershipDTO.builder().
                    id(dealerEntity.getId()).
                    name(dealerEntity.getName()).
                    address(dealerEntity.getAddress()).build();
            dealerDTOList.add(dealerDTO);
        }
        return dealerDTOList;
    }

    public List<CarDealershipEntity> convertetDealerDTOToDealerEntity(List<CarDealershipDTO> dealerDTOList) {
        List<CarDealershipEntity> dealerEntityList = new ArrayList<>();
        for (CarDealershipDTO dealerDTO : dealerDTOList) {
            CarDealershipEntity dealerEntity = CarDealershipEntity.builder().
                    id(dealerDTO.getId()).
                    name(dealerDTO.getName()).
                    address(dealerDTO.getAddress()).
                    build();
            dealerEntityList.add(dealerEntity);
        }
        return dealerEntityList;
    }

    public CarEntity converterCarDTOToCarEntity(CarDTO carDTO) {
        CarEntity car = CarEntity.builder().
                id(carDTO.getId()).
                name(carDTO.getName()).
                date(carDTO.getDate()).
                color(carDTO.getColor()).
                isAfterCrash(carDTO.isAfterCrash()).
                build();
        return car;
    }
}
