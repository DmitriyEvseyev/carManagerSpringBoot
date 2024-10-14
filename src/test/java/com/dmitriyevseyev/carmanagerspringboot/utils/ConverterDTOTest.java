package com.dmitriyevseyev.carmanagerspringboot.utils;

import com.dmitriyevseyev.carmanagerspringboot.models.Car;
import com.dmitriyevseyev.carmanagerspringboot.models.CarDealership;
import com.dmitriyevseyev.carmanagerspringboot.models.dto.CarDTO;
import com.dmitriyevseyev.carmanagerspringboot.models.dto.CarDealershipDTO;
import com.dmitriyevseyev.carmanagerspringboot.models.entity.CarDealershipEntity;
import com.dmitriyevseyev.carmanagerspringboot.models.entity.CarEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

class ConverterDTOTest {
    ConverterDTO converterDTO;
    private CarDTO carDTO;
    private Car car;
    private CarEntity carEntity;
    private CarDealershipDTO dealerDTO;
    private CarDealership dealer;
    private CarDealershipEntity dealerEntity;
    List <CarDealershipDTO> dealerDTOList;
    List <CarDealershipEntity> dealerEntityList;


    @BeforeEach
    void setUp() throws ParseException {
        converterDTO = new ConverterDTO();
        this.dealerDTO = CarDealershipDTO.builder().
                id(1).name("DEALER").
                address("DEALER_ADDRESS").build();
        this.dealer = CarDealership.builder().
                id(1).name("DEALER").
                address("DEALER_ADDRESS").build();
        this.dealerEntity = CarDealershipEntity.builder().
                id(1).
                name("DEALER").
                address("DEALER_ADDRESS").build();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        this.carDTO = CarDTO.builder().
                id(1).
                dealerId(1).
                name("BMW").
                date(formatter.parse("2023-01-01")).
                color("white").isAfterCrash(false).build();
        this.car = Car.builder().
                id(1).
                dealerId(1).
                name("BMW").
                date(formatter.parse("2023-01-01")).
                color("white").isAfterCrash(false).build();
        this.carEntity = CarEntity.builder().
                id(1).
               // dealer(dealerEntity).
                name("BMW").
                date(formatter.parse("2023-01-01")).
                color("white").
                isAfterCrash(false).build();
        dealerDTOList = new ArrayList<>();
        dealerEntityList  = new ArrayList<>();
        dealerDTOList.add(dealerDTO);
        dealerEntityList.add(dealerEntity);
    }

    @Test
    void convertDealerDTOListToDealerEntityListTest () {

    }

    @Test
    void convertCarDTOToCarEntityTest() {
        CarEntity carEntityReturn = converterDTO.convertCarDTOToCarEntity(carDTO);
        System.out.println(carEntityReturn);
        Assertions.assertNotNull(carEntityReturn);
        Assertions.assertEquals(carEntity.getId(), carEntityReturn.getId());
        Assertions.assertEquals(carEntity.getName(), carEntityReturn.getName());
        Assertions.assertInstanceOf(CarEntity.class, carEntityReturn);
    }

    @Test
    void convertDealerToDealerDTOTest() {
        CarDealershipDTO dealerDTOReturn = converterDTO.convertDealerToDealerDTO(dealer);
        System.out.println(dealerDTOReturn);
        Assertions.assertNotNull(dealerDTOReturn);
        Assertions.assertEquals(dealer.getId(), dealerDTOReturn.getId());
        Assertions.assertEquals(dealer.getName(), dealerDTOReturn.getName());
        Assertions.assertInstanceOf(CarDealershipDTO.class, dealerDTOReturn);
    }

    @Test
    void convertDealerDTOToDealerTest() {
        CarDealership dealerReturn = converterDTO.convertDealerDTOToDealer(dealerDTO);
        System.out.println(dealerReturn);
        Assertions.assertNotNull(dealerReturn);
        Assertions.assertEquals(dealerDTO.getId(), dealerReturn.getId());
        Assertions.assertEquals(dealerDTO.getName(), dealerReturn.getName());
        Assertions.assertInstanceOf(CarDealership.class, dealerReturn);
    }

    @Test
    void convertCarToCarDTOTest() {
        CarDTO carDTOReturn = converterDTO.convertCarToCarDTO(car);
        System.out.println(carDTOReturn);
        Assertions.assertNotNull(carDTOReturn);
        Assertions.assertEquals(carDTO.getId(), carDTOReturn.getId());
        Assertions.assertEquals(carDTO.getName(), carDTOReturn.getName());
        Assertions.assertInstanceOf(CarDTO.class, carDTOReturn);
    }

    @Test
    void convertCarDTOToCarTest() {
        Car carReturn = converterDTO.convertCarDTOToCar(carDTO);
        System.out.println(carReturn);
        Assertions.assertNotNull(carReturn);
        Assertions.assertEquals(car.getId(), carReturn.getId());
        Assertions.assertEquals(car.getName(), carReturn.getName());
        Assertions.assertInstanceOf(Car.class, carReturn);
    }

    @Test
    void summTest() {
        Integer b = converterDTO.sum(30);
        System.out.println("SUM - " + b);

        Assertions.assertEquals(60, b);
    }
}