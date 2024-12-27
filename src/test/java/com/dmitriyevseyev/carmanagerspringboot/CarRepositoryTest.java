package com.dmitriyevseyev.carmanagerspringboot;

import com.dmitriyevseyev.carmanagerspringboot.models.Car;
import com.dmitriyevseyev.carmanagerspringboot.models.dto.CarDTO;
import com.dmitriyevseyev.carmanagerspringboot.models.entity.CarDealershipEntity;
import com.dmitriyevseyev.carmanagerspringboot.models.entity.CarEntity;
import com.dmitriyevseyev.carmanagerspringboot.repositories.CarRepository;
import com.dmitriyevseyev.carmanagerspringboot.restControllers.CarRESTController;
import com.dmitriyevseyev.carmanagerspringboot.services.CarService;
import com.dmitriyevseyev.carmanagerspringboot.services.DealerService;
import com.dmitriyevseyev.carmanagerspringboot.utils.ConverterDTO;
import com.dmitriyevseyev.carmanagerspringboot.utils.exeptions.NotFoundException;
import jakarta.persistence.EntityManagerFactory;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.BindingResult;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CarRepositoryTest {

    @Mock
    CarRepository carRepository;

    @InjectMocks
    CarService carService;

//    @Autowired
//    public CarRepositoryTest(CarRepository carRepository, CarService carService) {
//        this.carRepository = carRepository;
//        this.carService = carService;
//    }


    private List<Car> ETALON_CAR_LIST;
    private Car car;
//    private CarEntity carEntity;
//    private CarDealershipEntity dealerEntity;


    @BeforeEach
    void init() throws ParseException {
        ETALON_CAR_LIST = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        this.car = Car.builder().
                id(1).
                dealerId(1).
                name("BMW").
                date(formatter.parse("2023-01-01")).
                color("white").isAfterCrash(false).build();

//        this.dealerEntity = CarDealershipEntity.builder().
//                id(1).
//                name("AAA").
//                address("RRR").build();

//        this.carDTO = CarDTO.builder().
//                id(1).
//                dealerId(1).
//                name("BMW").
//                date(formatter.parse("2023-01-01")).
//                color("white").isAfterCrash(false).build();
//
//        this.carEntity = CarEntity.builder().id(2).dealer(dealerEntity).
//                name("FFF").
//                date(formatter.parse("2023-01-01")).color("GGG").isAfterCrash(false).
//                build();
        ETALON_CAR_LIST.add(car);
        carService.addCar(car);
    }

    @AfterEach
    void teardown() {
        ETALON_CAR_LIST.clear();
    }

//    @Test
//    @DisplayName("GET /cars return carDTOList")
//    void handleGetCarDTOList_ReturnsValidCarDTOList() {
//        when(carService.getAllCarsList()).thenReturn(List.of(car));
//
//        List<Car> carsList = this.carService.getAllCarsList();
//
//        Assertions.assertThat(carsList.size()).isEqualTo(1);
//        Assertions.assertThat(carsList).isNotNull();
//        verify(this.carRepository).findById(2);
//
//    }

    @Test
    @DisplayName("GET /{id} return  carDTO by id")
    void handleGetCarDTO_ReturnsValidCarDTO() {
        System.out.println("kkkkk");

        when(carService.getCarById(car.getId())).thenReturn(car);

        Car returneCar = this.carService.getCarById(car.getId());
        System.out.println(returneCar);
        Assertions.assertThat(returneCar).isNotNull();
        // verify(this.carRepository).findById(156);
    }


//    @Test
//    @DisplayName("POST return  ResponseEntity<OK>")
//    void handleAddCarDTO_ReturnsValidResponceEntity() {
//        carRepository.save(carEntity);
//        System.out.println(carEntity);
//
//        Assertions.assertThat(carEntity.getId()).isGreaterThan(0);
//
//    }

}
