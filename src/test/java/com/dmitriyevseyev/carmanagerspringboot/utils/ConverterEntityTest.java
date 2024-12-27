package com.dmitriyevseyev.carmanagerspringboot.utils;

import com.dmitriyevseyev.carmanagerspringboot.models.Car;
import com.dmitriyevseyev.carmanagerspringboot.models.CarDealership;
import com.dmitriyevseyev.carmanagerspringboot.models.User;
import com.dmitriyevseyev.carmanagerspringboot.models.entity.CarDealershipEntity;
import com.dmitriyevseyev.carmanagerspringboot.models.entity.CarEntity;
import com.dmitriyevseyev.carmanagerspringboot.models.entity.UserEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

class ConverterEntityTest {
    ConverterEntity converterEntity;
    private Car car;
    private CarEntity carEntity;
    private CarDealership dealer;
    private CarDealershipEntity dealerEntity;
    private User user;
    private UserEntity userEntity;
    List<Car> carsList;
    List<CarEntity> carEntitiesList;
    List<CarDealership> dealersList;
    List<CarDealershipEntity> dealerEntitiesList;

    @BeforeEach
    void setUp() throws ParseException {
        converterEntity = new ConverterEntity();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        this.dealer = CarDealership.builder().
                id(1).name("DEALER").
                address("DEALER_ADDRESS").build();
        this.dealerEntity = CarDealershipEntity.builder().
                id(1).
                name("DEALER").
                address("DEALER_ADDRESS").build();
        this.car = Car.builder().
                id(1).
                dealerId(1).
                name("BMW").
                date(formatter.parse("2023-01-01")).
                color("white").isAfterCrash(false).build();
        this.carEntity = CarEntity.builder().
                id(1).
                dealer(dealerEntity).
                name("BMW").
                date(formatter.parse("2023-01-01")).
                color("white").
                isAfterCrash(false).build();
        this.user = User.builder().
                id(1).
                userName("EEE").
                password("EEE").build();
        this.userEntity = UserEntity.builder().
                id(1).
                userName("EEE").
                password("EEE").build();

        carsList = new ArrayList<>();
        carEntitiesList = new ArrayList<>();
        dealersList = new ArrayList<>();
        dealerEntitiesList = new ArrayList<>();

        carsList.add(car);
        carEntitiesList.add(carEntity);
        dealersList.add(dealer);
        dealerEntitiesList.add(dealerEntity);
    }

    @Test
    void convertUserToUserEntity() {
        UserEntity userEntityReturn = converterEntity.convertUserToUserEntity(user);
        System.out.println(userEntityReturn);
        Assertions.assertNotNull(userEntityReturn);
        Assertions.assertEquals(userEntity.getId(), userEntityReturn.getId());
        Assertions.assertEquals(userEntity.getUserName(), userEntityReturn.getUserName());
        Assertions.assertEquals(userEntity.getPassword(), userEntityReturn.getPassword());
        Assertions.assertInstanceOf(UserEntity.class, userEntityReturn);
    }

    @Test
    void convertDealerEntitiesListToDealersListTest() {
        List<CarDealership> dealersListReturn =
                converterEntity.convertDealerEntitiesListToDealersList(dealerEntitiesList);
        System.out.println(dealersListReturn);
        Assertions.assertEquals(dealersListReturn.size(), dealersList.size());
        Assertions.assertEquals(dealersListReturn, dealersList);
    }

    @Test
    void convertDealerEntityToDealerTest() {
        CarDealership dealerReturn = converterEntity.convertDealerEntityToDealer(dealerEntity);
        System.out.println(dealerReturn);
        Assertions.assertNotNull(dealerReturn);
        Assertions.assertEquals(dealer.getId(), dealerReturn.getId());
        Assertions.assertEquals(dealer.getName(), dealerReturn.getName());
        Assertions.assertEquals(dealer.getAddress(), dealerReturn.getAddress());
        Assertions.assertInstanceOf(CarDealership.class, dealerReturn);
    }

    @Test
    void convertDealerToDealerEntityTest() {
        CarDealershipEntity dealerEntityReturn = converterEntity.convertDealerToDealerEntity(dealer);
        System.out.println(dealerEntityReturn);
        Assertions.assertNotNull(dealerEntityReturn);
        Assertions.assertEquals(dealerEntity.getId(), dealerEntityReturn.getId());
        Assertions.assertEquals(dealerEntity.getName(), dealerEntityReturn.getName());
        Assertions.assertEquals(dealerEntity.getAddress(), dealerEntityReturn.getAddress());
        Assertions.assertInstanceOf(CarDealershipEntity.class, dealerEntityReturn);
    }

    @Test
    void convertCarEntitiesListToCarsListTest() {
        List<Car> carsListReturn = converterEntity.convertCarEntitiesListToCarsList(carEntitiesList);
        System.out.println(carsListReturn);
        Assertions.assertEquals(carsList.size(), carsListReturn.size());
        Assertions.assertEquals(carsList, carsListReturn);
    }

    @Test
    void converterCarToCarEntityTest() {
        CarEntity carEntityReturn = converterEntity.converterCarToCarEntity(car, dealerEntity);
        System.out.println(carEntityReturn);
        Assertions.assertNotNull(carEntityReturn);
        Assertions.assertEquals(carEntity.getId(), carEntityReturn.getId());
        Assertions.assertEquals(carEntity.getDealer(), carEntityReturn.getDealer());
        Assertions.assertEquals(carEntity.getName(), carEntityReturn.getName());
        Assertions.assertEquals(carEntity.getDate(), carEntityReturn.getDate());
        Assertions.assertEquals(carEntity.getColor(), carEntityReturn.getColor());
        Assertions.assertEquals(carEntity.isAfterCrash(), carEntityReturn.isAfterCrash());
        Assertions.assertInstanceOf(CarEntity.class, carEntityReturn);
    }

    @Test
    void converterCarEntityToCar() {
        Car carReturn = converterEntity.converterCarEntityToCar(carEntity);
        System.out.println(carReturn);
        Assertions.assertNotNull(carReturn);
        Assertions.assertEquals(car.getId(), carReturn.getId());
        Assertions.assertEquals(car.getDealerId(), carReturn.getDealerId());
        Assertions.assertEquals(car.getName(), carReturn.getName());
        Assertions.assertEquals(car.getDate(), carReturn.getDate());
        Assertions.assertEquals(car.getColor(), carReturn.getColor());
        Assertions.assertEquals(car.isAfterCrash(), carReturn.isAfterCrash());
        Assertions.assertInstanceOf(Car.class, carReturn);
    }
}