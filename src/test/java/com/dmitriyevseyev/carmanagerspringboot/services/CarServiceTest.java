package com.dmitriyevseyev.carmanagerspringboot.services;

import com.dmitriyevseyev.carmanagerspringboot.models.Car;
import com.dmitriyevseyev.carmanagerspringboot.models.CarDealership;
import com.dmitriyevseyev.carmanagerspringboot.models.entity.CarDealershipEntity;
import com.dmitriyevseyev.carmanagerspringboot.models.entity.CarEntity;
import com.dmitriyevseyev.carmanagerspringboot.repositories.CarRepository;
import com.dmitriyevseyev.carmanagerspringboot.repositories.DealerRepository;
import com.dmitriyevseyev.carmanagerspringboot.utils.ConverterEntity;
import com.dmitriyevseyev.carmanagerspringboot.utils.exeptions.NotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarServiceTest {
    @Mock
    ConverterEntity converterEntity;
    @Mock
    CarRepository carRepository;
    @InjectMocks
    CarService carService;
    @Mock
    DealerRepository dealerRepository;
    @InjectMocks
    DealerService dealerService;

    Car car;
    CarEntity carEntity;
    private CarDealership dealer;
    private CarDealershipEntity dealerEntity;
    List<Car> carsList;
    List<CarEntity> carEntitiesList;

    @BeforeEach
    void setUp() throws ParseException {
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

        carsList = new ArrayList<>();
        carEntitiesList = new ArrayList<>();
        carsList.add(car);
        carEntitiesList.add(carEntity);
    }

    @Test
    void getAllCarsList() {
        //  when(carRepository.getCarEntitiesByDealer(dealerEntity)).thenReturn()

    }

    @Test
    void getCarById() {
        when(carRepository.findById(carEntity.getId())).thenReturn(Optional.of(carEntity));
        when(converterEntity.converterCarEntityToCar(carEntity)).thenReturn(car);
        Car carReturn = carService.getCarById(car.getId());
        System.out.println(carReturn);

        Assertions.assertThat(carReturn).isInstanceOf(Car.class);
        Assertions.assertThat(carReturn).isNotNull();
        Assertions.assertThat(car.getId()).isEqualTo(carReturn.getId());
        Assertions.assertThat(car.getDealerId()).isEqualTo(carReturn.getDealerId());
        Assertions.assertThat(car.getName()).isEqualTo(carReturn.getName());
        Assertions.assertThat(car.getDate()).isEqualTo(carReturn.getDate());
        Assertions.assertThat(car.getColor()).isEqualTo(carReturn.getColor());
        Assertions.assertThat(car.isAfterCrash()).isEqualTo(carReturn.isAfterCrash());
    }

    @Test
    void getCarEntity_ReturnsNotfounExeption() {
        when(carRepository.findById(carEntity.getId())).
                thenReturn(Optional.empty());
        NotFoundException e = assertThrows(NotFoundException.class,
                () -> carService.getCarById(carEntity.getId()));
        System.out.println(e.getMessage());
        Assertions.assertThat(e).isInstanceOf(NotFoundException.class);
        Assertions.assertThat(e.getMessage()).isNotNull();
    }

    @Test
    void addCar() {
        when(dealerRepository.findById(dealerEntity.getId())).
                thenReturn(Optional.of(dealerEntity));
        when(converterEntity.convertDealerToDealerEntity(dealer)).thenReturn(dealerEntity);
        when(converterEntity.converterCarToCarEntity(car, dealerEntity)).thenReturn(carEntity);
        when(carRepository.save(carEntity)).thenReturn(carEntity);

        CarDealershipEntity dealerEntity = converterEntity.convertDealerToDealerEntity(dealerService.getDealer(car.getDealerId()));
        carService.addCar(car);
        verify(carRepository).save(carEntity);

    }

    @Test
    void updateCar() {
    }

    @Test
    void delOnlyOneCar_ReturnsDealeteCar() {
        when(carRepository.deleteCarEntitiyById(car.getId())).thenReturn(1);
        carService.delOnlyOneCar(car.getId());

        verify(carRepository).deleteCarEntitiyById(car.getId());
        Assertions.assertThat(carRepository.deleteCarEntitiyById(car.getId()))
                .isEqualTo(1);
    }

    @Test
    public void delOnlyOneCar_ReturnsNotFoundExeption() {
        when(carRepository.deleteCarEntitiyById(car.getId())).thenReturn(0);
        NotFoundException e = assertThrows(NotFoundException.class,
                () -> carService.delOnlyOneCar(car.getId()));

        System.out.println(e.getMessage());
        Assertions.assertThat(e).isInstanceOf(NotFoundException.class);
        Assertions.assertThat(e.getMessage()).isNotNull();

    }

}