package com.dmitriyevseyev.carmanagerspringboot;

import com.dmitriyevseyev.carmanagerspringboot.models.dto.CarDTO;
import com.dmitriyevseyev.carmanagerspringboot.models.entity.CarEntity;
import com.dmitriyevseyev.carmanagerspringboot.repositories.CarRepository;
import com.dmitriyevseyev.carmanagerspringboot.restControllers.CarRESTController;
import com.dmitriyevseyev.carmanagerspringboot.services.CarService;
import com.dmitriyevseyev.carmanagerspringboot.utils.ConverterDTO;
import com.dmitriyevseyev.carmanagerspringboot.utils.exeptions.NotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.web.servlet.function.RequestPredicates.GET;
import static org.springframework.web.servlet.function.RequestPredicates.contentType;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
//@AutoConfigureMockMvc(printOnlyOnFailure = false)
//@RunWith(SpringRunner.class)
@WebMvcTest (CarRESTController.class)
//@DataJpaTest
class CarEntityManagerSpringBootApplicationTests {
    @MockBean
    MockMvc mockMvc;
    private final CarService carService;

    @MockBean
    CarRepository carRepository;

    ConverterDTO converterDTO;

    CarRESTController carRESTController;

    @Autowired
    public CarEntityManagerSpringBootApplicationTests(CarService carService, CarRepository carRepository, ConverterDTO converterDTO, CarRESTController carRESTController) {
        this.carService = carService;
        this.carRepository = carRepository;
        this.converterDTO = converterDTO;
        this.carRESTController = carRESTController;
    }



    @Test
    void findCarByIdFromService_ReturnNotfoundEx() {
        NotFoundException exception = Assertions.assertThrows(NotFoundException.class, () -> {
            this.carService.getCarById(12);
        });
        Assertions.assertEquals("The car was not found! carId = 12", exception.getMessage());
    }

    @Test
    void findCarByIdFromController_ReturnNotfoundExeption() throws Exception {

        mockMvc.perform(get("/cars/12")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void findCarByIdTest() {
        Optional<CarEntity> carEntity = this.carRepository.findById(156);
        assertTrue(carEntity.isPresent());
    }
//    @Test
//    public void handleGetCarDTOList_ReturnsValidCarDTOList() throws Exception {
//        var requestBuilder = MockMvcRequestBuilders.get("/cars");
////        mockMvc.perform(requestBuilder)
////                .andExpectAll(
////                        status().isOk(),
////                        (ResultMatcher) content().contentType(MediaType.APPLICATION_JSON),
////                        (ResultMatcher) jsonPath(""));
//        mockMvc.perform(requestBuilder)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content()
//                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$[0].name", is("bob")));
//
//                andExpect
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$[0].name", is("bob")));
//    }
}
//this.mockMvc.perform(requestBuilder)
//        // then
//        .andExpectAll(
//        status().isOk(),
//        content().contentType(MediaType.APPLICATION_JSON),
//        content().json("""
//                                [