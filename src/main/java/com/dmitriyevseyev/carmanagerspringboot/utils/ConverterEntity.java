package com.dmitriyevseyev.carmanagerspringboot.utils;

import com.dmitriyevseyev.carmanagerspringboot.models.Car;
import com.dmitriyevseyev.carmanagerspringboot.models.CarDealership;
import com.dmitriyevseyev.carmanagerspringboot.models.User;
import com.dmitriyevseyev.carmanagerspringboot.models.entity.CarDealershipEntity;
import com.dmitriyevseyev.carmanagerspringboot.models.entity.CarEntity;
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

    public List<CarDealership> convertDealerEntitiesListToDealersList(List<CarDealershipEntity> dealerListEntity) {
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

    public List<Car> convertCarEntitiesListToCarsList(List<CarEntity> carEntityList) {
        List<Car> carList = new ArrayList<>();

        for (CarEntity carEntity : carEntityList) {
            Car car = Car.builder().
                    id(carEntity.getId()).
                    idDealer(carEntity.getDealer().getId()).
                    name(carEntity.getName()).
                    date(carEntity.getDate()).
                    color(carEntity.getColor()).
                    isAfterCrash(carEntity.isAfterCrash()).
                    build();
            carList.add(car);
        }
        return carList;
    }

    public CarEntity converterCarToCarEntity(Car car, CarDealershipEntity dealerEntity) {
        CarEntity carEntity = CarEntity.builder().
                id(car.getId()).
                dealer(dealerEntity).
                name(car.getName()).
                date(car.getDate()).
                color(car.getColor()).
                isAfterCrash(car.isAfterCrash()).
                build();
        return carEntity;
    }

    public Car converterCarEntityToCar(CarEntity carEntity) {
        Car car = Car.builder().
                id(carEntity.getId()).
                idDealer(carEntity.getDealer().getId()).
                name(carEntity.getName()).
                date(carEntity.getDate()).
                color(carEntity.getColor()).
                isAfterCrash(carEntity.isAfterCrash()).
                build();
        return car;
    }
}
