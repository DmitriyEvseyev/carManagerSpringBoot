package com.dmitriyevseyev.carmanagerspringboot.services;

import com.dmitriyevseyev.carmanagerspringboot.models.Car;
import com.dmitriyevseyev.carmanagerspringboot.models.CarDealership;
import com.dmitriyevseyev.carmanagerspringboot.models.entity.CarDealershipEntity;
import com.dmitriyevseyev.carmanagerspringboot.models.entity.CarEntity;
import com.dmitriyevseyev.carmanagerspringboot.repositories.CarRepository;
import com.dmitriyevseyev.carmanagerspringboot.utils.Constants;
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
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarServiceTest {
    @Mock
    ConverterEntity converterEntity;
    @Mock
    CarRepository carRepository;
    @Mock
    DealerService dealerService;
    @InjectMocks
    CarService carService;

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
    public void getAllCarsList_ReturnsValidAllCarsList() {
        when(dealerService.getDealersList()).thenReturn(Collections.singletonList(dealer));
        when(converterEntity.convertDealerToDealerEntity(dealer)).thenReturn(dealerEntity);
        when(carRepository.getCarEntitiesByDealer(dealerEntity)).thenReturn(carEntitiesList);
        when(converterEntity.convertCarEntitiesListToCarsList(carEntitiesList)).thenReturn(carsList);

        List<Car> returnCarsList = carService.getAllCarsList();

        Assertions.assertThat(returnCarsList).isNotNull();
        Assertions.assertThat(returnCarsList.size()).isEqualTo(carsList.size());
        Assertions.assertThat(returnCarsList).isEqualTo(carsList);
    }

    @Test
    public void getCarsListByDealerId_ReturnsSaveValidCarsList() throws NotFoundException {
        when(dealerService.getDealer(dealer.getId())).thenReturn(dealer);
        when(converterEntity.convertDealerToDealerEntity(dealer)).thenReturn(dealerEntity);
        when(carRepository.getCarEntitiesByDealer(dealerEntity)).thenReturn(carEntitiesList);
        when(converterEntity.convertCarEntitiesListToCarsList(carEntitiesList)).thenReturn(carsList);

        List<Car> returnCarsList = carService.getCarsListByDealerId(dealer.getId());

        Assertions.assertThat(returnCarsList).isNotNull();
        Assertions.assertThat(returnCarsList.size()).isEqualTo(carsList.size());
        Assertions.assertThat(returnCarsList).isEqualTo(carsList);
    }

    @Test
    public void getCarById_ReturnsValidCar() {
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
    public void getCarEntity_ReturnsNotfounExeption() {
        when(carRepository.findById(carEntity.getId())).
                thenReturn(Optional.empty());
        NotFoundException e = assertThrows(NotFoundException.class,
                () -> carService.getCarById(carEntity.getId()));
        System.out.println(e.getMessage());
        Assertions.assertThat(e).isInstanceOf(NotFoundException.class);
        Assertions.assertThat(e.getMessage()).isNotNull();
    }

    @Test
    public void addCar_ReturnsSaveValidCar() {
        when(dealerService.getDealer(car.getDealerId())).thenReturn(dealer);
        when(converterEntity.convertDealerToDealerEntity(dealer)).thenReturn(dealerEntity);
        when(converterEntity.converterCarToCarEntity(car, dealerEntity)).thenReturn(new CarEntity());
        when(carRepository.save(new CarEntity())).thenReturn(carEntity);

        carService.addCar(car);
        verify(carRepository).save(any(CarEntity.class));
    }

    @Test
    public void UpdateCar_ReturnsUpdateCar() {
        when(dealerService.getDealer(car.getDealerId())).thenReturn(dealer);
        when(converterEntity.convertDealerToDealerEntity(dealer)).thenReturn(dealerEntity);
        when(carRepository.existsById(car.getId())).thenReturn(true);
        when(converterEntity.converterCarToCarEntity(car, dealerEntity)).thenReturn(carEntity);
        when(carRepository.save(carEntity)).thenReturn(carEntity);

        carService.updateCar(car);
        verify(carRepository).save(carEntity);
    }

    @Test
    public void UpdateCar_ReturnsNotFound() {
        when(carRepository.existsById(car.getId())).thenReturn(false);
        NotFoundException e = assertThrows(NotFoundException.class, () -> {
            carService.updateCar(car);
        });
        Assertions.assertThat(e).isInstanceOf(NotFoundException.class);
        assertEquals(Constants.NOT_FOUND_DEALER_EXCEPTION_MESSAGE + car.getId(), e.getMessage());
    }

    @Test
    public void delOnlyOneCar_ReturnsDealeteCar() {
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