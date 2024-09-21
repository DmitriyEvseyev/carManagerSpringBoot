package com.dmitriyevseyev.carmanagerspringboot.utils;

import com.dmitriyevseyev.carmanagerspringboot.models.Car;
import com.dmitriyevseyev.carmanagerspringboot.models.CarDealership;
import com.dmitriyevseyev.carmanagerspringboot.models.dto.CarDTO;
import com.dmitriyevseyev.carmanagerspringboot.models.dto.CarDealershipDTO;
import com.dmitriyevseyev.carmanagerspringboot.models.entity.CarDealershipEntity;
import com.dmitriyevseyev.carmanagerspringboot.models.entity.CarEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class ConverterDTO {

    public List<CarDTO> convertCarsListToCarsDTOList(List<Car> carsList) {
        List<CarDTO> carsDTOList = new ArrayList<>();

        for (Car car : carsList) {
            CarDTO carDTO = CarDTO.builder().
                    id(car.getId()).
                    dealerId(car.getDealerId()).
                    name(car.getName()).
                    date(car.getDate()).
                    color(car.getColor()).
                    isAfterCrash(car.isAfterCrash()).
                    build();
            carsDTOList.add(carDTO);
        }


        System.out.println("convertCarsListToCarsDTOList - " + carsDTOList);

        return carsDTOList;
    }

    public List<CarDealershipDTO> convertDealersListToDealersDTOList(List<CarDealership> dealersList) {
        List<CarDealershipDTO> dealersDTOList = new ArrayList<>();

        for (CarDealership dealer : dealersList) {
            CarDealershipDTO dealerDTO = CarDealershipDTO.builder().
                    id(dealer.getId()).
                    name(dealer.getName()).
                    address(dealer.getAddress()).build();
            dealersDTOList.add(dealerDTO);
        }
        return dealersDTOList;
    }

    public List<CarDealershipEntity> convertDealerDTOListToDealerEntityList(List<CarDealershipDTO> dealerDTOList) {
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

    public CarEntity convertCarDTOToCarEntity(CarDTO carDTO) {
        CarEntity car = CarEntity.builder().
                id(carDTO.getId()).
                name(carDTO.getName()).
                date(carDTO.getDate()).
                color(carDTO.getColor()).
                isAfterCrash(carDTO.isAfterCrash()).
                build();
        return car;
    }
    public CarDealershipDTO convertDealerToDealerDTO (CarDealership dealer) {
        CarDealershipDTO dealerDTO = CarDealershipDTO.builder().
                id(dealer.getId()).
                name(dealer.getName()).
                address(dealer.getAddress()).
                build();
        return dealerDTO;
    }
    public CarDealership convertDealerDTOToDealer (CarDealershipDTO dealerDTO) {
        CarDealership dealer = CarDealership.builder().
                id(dealerDTO.getId()).
                name(dealerDTO.getName()).
                address(dealerDTO.getAddress()).
                build();
        return dealer;
    }

    public CarDTO convertCarToCarDTO (Car car) {
        CarDTO carDTO = CarDTO.builder().
                id(car.getId()).
                dealerId(car.getDealerId()).
                name(car.getName()).
                date(car.getDate()).
                color(car.getColor()).
                isAfterCrash(car.isAfterCrash()).
                build();
        return carDTO;
    }
    public Car convertCarDTOToCar (CarDTO carDTO) {
        Car car = Car.builder().
                id(carDTO.getId()).
                dealerId(carDTO.getDealerId()).
                name(carDTO.getName()).
                date(carDTO.getDate()).
                color(carDTO.getColor()).
                isAfterCrash(carDTO.isAfterCrash()).
                build();
        return car;
    }
}
