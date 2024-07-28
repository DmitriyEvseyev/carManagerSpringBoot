package com.dmitriyevseyev.carmanagerspringboot.utils;

import com.dmitriyevseyev.carmanagerspringboot.models.CarDealership;
import com.dmitriyevseyev.carmanagerspringboot.models.User;
import com.dmitriyevseyev.carmanagerspringboot.models.entity.CarDealershipEntity;
import com.dmitriyevseyev.carmanagerspringboot.models.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ConverterEntity {
    public UserEntity convertUserToUserEntity(User user) {
        UserEntity userEntity = UserEntity.builder().
                id(user.getId()).
                userName(user.getUserName()).
                password(user.getPassword()).
                build();
        return userEntity;
    }

    //    public List<Car> convertCarToCarDTO(List<Car> carList) {
//        List<CarDTO> carDTOList = new ArrayList<>();
//
//        for (Car car : carList) {
//            CarDTO carDTO = CarDTO.builder().
//                    id(car.getId()).
//                    idDealer(car.getDealer().getId()).
//                    name(car.getName()).
//                    date(car.getDate()).
//                    color(car.getColor()).
//                    isAfterCrash(car.isAfterCrash()).
//                    build();
//            carDTOList.add(carDTO);
//        }
//
//
//        System.out.println("convertCarToCarDTO - " + carDTOList);
//
//        return carDTOList;
//    }
//
    public List<CarDealership> convertDealerEntityLisToDealerList(List<CarDealershipEntity> dealerListEntity) {
        List<CarDealership> dealerList = new ArrayList<>();
        for (CarDealershipEntity dealerEntity : dealerListEntity) {
            CarDealership dealer = CarDealership.builder().
                    id(dealerEntity.getId()).
                    name(dealerEntity.getName()).
                    address(dealerEntity.getAddress()).build();
            dealerList.add(dealer);
        }
        return dealerList;
    }

    public CarDealership convertDealerEntityToDealer(CarDealershipEntity dealerEntity) {
        return CarDealership.builder().
                id(dealerEntity.getId()).
                name(dealerEntity.getName()).
                address(dealerEntity.getAddress()).
                build();
    }

    public CarDealershipEntity convertDealerToDealerEntity(CarDealership dealer) {
        CarDealershipEntity dealerEntity = CarDealershipEntity.builder().
                id(dealer.getId()).
                name(dealer.getName()).
                address(dealer.getAddress()).build();
        return dealerEntity;
    }

//
//    public List<CarDealership> convertetDealerDTOToDealer(List<CarDealershipDTO> dealerDTOList) {
//        List<CarDealership> dealerList = new ArrayList<>();
//        for (CarDealershipDTO dealerDTO : dealerDTOList) {
//            CarDealership dealer = CarDealership.builder().
//                    id(dealerDTO.getId()).
//                    name(dealerDTO.getName()).
//                    address(dealerDTO.getAddress()).
//                    build();
//            dealerList.add(dealer);
//        }
//        return dealerList;
//    }
//
//    public Car converterCarDTOToCar(CarDTO carDTO) {
//        Car car = Car.builder().
//                id(carDTO.getId()).
//                name(carDTO.getName()).
//                date(carDTO.getDate()).
//                color(carDTO.getColor()).
//                isAfterCrash(carDTO.isAfterCrash()).
//                build();
//        return car;
//    }
}
