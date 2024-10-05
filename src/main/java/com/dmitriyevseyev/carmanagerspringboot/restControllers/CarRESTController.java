package com.dmitriyevseyev.carmanagerspringboot.restControllers;

import com.dmitriyevseyev.carmanagerspringboot.models.dto.CarDTO;
import com.dmitriyevseyev.carmanagerspringboot.services.DealerService;
import com.dmitriyevseyev.carmanagerspringboot.services.CarService;
import com.dmitriyevseyev.carmanagerspringboot.utils.ConverterDTO;
import com.dmitriyevseyev.carmanagerspringboot.utils.exeptions.CreatedExeption;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/cars")
@Slf4j
public class CarRESTController {
    final private CarService carService;
    final private DealerService dealerService;
    final private ConverterDTO converterDTO;

    @Autowired
    public CarRESTController(CarService carService, DealerService dealerService, ConverterDTO converterDTO) {
        this.carService = carService;
        this.dealerService = dealerService;
        this.converterDTO = converterDTO;
    }

    @GetMapping()
    public List<CarDTO> getCarsList() {
        List<CarDTO> carDTOList = converterDTO.convertCarsListToCarsDTOList(carService.getAllCarsList());
        log.info("CarDTOList - {}", carDTOList);
        return carDTOList;
    }

    @GetMapping("/{id}")
    public CarDTO getCar(@PathVariable("id") Integer carId) {
        CarDTO carDTO = converterDTO.convertCarToCarDTO(carService.getCarById(carId));
        log.info("CarDTO - {}", carDTO);
        return carDTO;
    }

    @PostMapping()
    public ResponseEntity<HttpStatus> newCar(@RequestBody @Valid CarDTO carDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMes = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMes.append("-").append(error.getDefaultMessage()).append("; ");
            }
            throw new CreatedExeption(errorMes.toString());
        }
        log.info("NEW,  carDTO -  {}", carDTO);
        dealerService.getDealer(carDTO.getDealerId());
        carService.addCar(converterDTO.convertCarDTOToCar(carDTO));
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<HttpStatus> updateCar(@RequestBody CarDTO carDTO) {

        log.info("UPDATE, carDTO - {}", carDTO);

        carService.updateCar(converterDTO.convertCarDTOToCar(carDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delCar(@PathVariable("id") Integer carId) {

        log.info("DELETE, car.id - {}", carId);

        carService.delOnlyOneCar(carId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/search")
    public List<CarDTO> searchCar(@RequestParam("column") String column,
                                  @RequestParam("pattern") String pattern,
                                  @RequestParam("startDate") String startDate,
                                  @RequestParam("endDate") String endDate,
                                  @RequestParam("dealerId") String dealerId) {
        List<CarDTO> carsDTOList = new ArrayList<>();
        if (column.equals("date")) {
            carsDTOList = converterDTO.convertCarsListToCarsDTOList(
                    carService.searchDateCar(dealerId, startDate, endDate));
        } else {
            carsDTOList = converterDTO.convertCarsListToCarsDTOList(
                    carService.searchCar(dealerId, column, pattern));
        }

        log.info("The found car -  {}", carsDTOList);

        return carsDTOList;
    }

    @GetMapping("/sort")
    public List<CarDTO> sortCars(@RequestParam("criteria") String criteria,
                                 @RequestParam("dealerId") String dealerId) {
        List<CarDTO> carsDTOList = converterDTO.convertCarsListToCarsDTOList(
                carService.sortCars(dealerId, criteria));

        log.info("Sorted carDTOList -  {}", carsDTOList);
        return carsDTOList;
    }
}



