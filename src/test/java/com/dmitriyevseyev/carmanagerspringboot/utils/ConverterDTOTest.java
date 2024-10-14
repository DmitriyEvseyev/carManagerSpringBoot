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
    List<CarDealership> dealersList;
    List<CarDealershipDTO> dealerDTOList;
    List<CarDealershipEntity> dealersEntityList;
    List<Car> carsList;
    List<CarDTO> carDTOList;

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
                name("BMW").
                date(formatter.parse("2023-01-01")).
                color("white").
                isAfterCrash(false).build();
        dealersList = new ArrayList<>();
        dealerDTOList = new ArrayList<>();
        dealersEntityList = new ArrayList<>();
        carsList = new ArrayList<>();
        carDTOList = new ArrayList<>();

        dealersList.add(dealer);
        dealerDTOList.add(dealerDTO);
        dealersEntityList.add(dealerEntity);
        carsList.add(car);
        carDTOList.add(carDTO);

    }
    @Test
    void convertCarsListToCarsDTOListTest() {
        List<CarDTO> carDTOListReturn = converterDTO.convertCarsListToCarsDTOList(carsList);
        System.out.println(carDTOListReturn);
        Assertions.assertEquals(carDTOListReturn.size(), carDTOList.size());
        Assertions.assertEquals(carDTOListReturn, carDTOList);
    }

    @Test
    void  convertDealersListToDealersDTOListTest (){
        List<CarDealershipDTO> dealerDTOsListReturn =
                converterDTO.convertDealersListToDealersDTOList(dealersList);
        System.out.println(dealerDTOsListReturn);
        Assertions.assertEquals(dealerDTOsListReturn.size(), dealerDTOList.size());
        Assertions.assertEquals(dealerDTOsListReturn, dealerDTOList);
    }
    @Test
    void convertDealerDTOListToDealerEntityListTest() {
        List<CarDealershipEntity> dealerEntityListReturn =
                converterDTO.convertDealerDTOListToDealerEntityList(dealerDTOList);
        System.out.println(dealerEntityListReturn);
        Assertions.assertEquals(dealerDTOList.size(), dealersEntityList.size());
        Assertions.assertEquals(dealersEntityList, dealerEntityListReturn);
    }

    @Test
    void convertCarDTOToCarEntityTest() {
        CarEntity carEntityReturn = converterDTO.convertCarDTOToCarEntity(carDTO);
        System.out.println(carEntityReturn);
        Assertions.assertNotNull(carEntityReturn);
        Assertions.assertEquals(carEntity.getId(), carEntityReturn.getId());
        Assertions.assertEquals(carEntity.getName(), carEntityReturn.getName());
        Assertions.assertEquals(carEntity.getDate(), carEntityReturn.getDate());
        Assertions.assertEquals(carEntity.getColor(), carEntityReturn.getColor());
        Assertions.assertEquals(carEntity.isAfterCrash(), carEntityReturn.isAfterCrash());
        Assertions.assertInstanceOf(CarEntity.class, carEntityReturn);
    }

    @Test
    void convertDealerToDealerDTOTest() {
        CarDealershipDTO dealerDTOReturn = converterDTO.convertDealerToDealerDTO(dealer);
        System.out.println(dealerDTOReturn);
        Assertions.assertNotNull(dealerDTOReturn);
        Assertions.assertEquals(dealer.getId(), dealerDTOReturn.getId());
        Assertions.assertEquals(dealer.getName(), dealerDTOReturn.getName());
        Assertions.assertEquals(dealer.getAddress(), dealerDTOReturn.getAddress());
        Assertions.assertInstanceOf(CarDealershipDTO.class, dealerDTOReturn);
    }

    @Test
    void convertDealerDTOToDealerTest() {
        CarDealership dealerReturn = converterDTO.convertDealerDTOToDealer(dealerDTO);
        System.out.println(dealerReturn);
        Assertions.assertNotNull(dealerReturn);
        Assertions.assertEquals(dealerDTO.getId(), dealerReturn.getId());
        Assertions.assertEquals(dealerDTO.getName(), dealerReturn.getName());
        Assertions.assertEquals(dealer.getAddress(), dealerReturn.getAddress());
        Assertions.assertInstanceOf(CarDealership.class, dealerReturn);
    }

    @Test
    void convertCarToCarDTOTest() {
        CarDTO carDTOReturn = converterDTO.convertCarToCarDTO(car);
        System.out.println(carDTOReturn);
        Assertions.assertNotNull(carDTOReturn);
        Assertions.assertEquals(carDTO.getId(), carDTOReturn.getId());
        Assertions.assertEquals(carDTO.getDealerId(), carDTOReturn.getDealerId());
        Assertions.assertEquals(carDTO.getName(), carDTOReturn.getName());
        Assertions.assertEquals(carDTO.getDate(), carDTOReturn.getDate());
        Assertions.assertEquals(carDTO.getColor(), carDTOReturn.getColor());
        Assertions.assertEquals(carDTO.isAfterCrash(), carDTOReturn.isAfterCrash());
        Assertions.assertInstanceOf(CarDTO.class, carDTOReturn);
    }

    @Test
    void convertCarDTOToCarTest() {
        Car carReturn = converterDTO.convertCarDTOToCar(carDTO);
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