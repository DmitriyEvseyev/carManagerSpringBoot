package com.dmitriyevseyev.carmanagerspringboot.restControllers;

import com.dmitriyevseyev.carmanagerspringboot.services.DealerService;
import com.dmitriyevseyev.carmanagerspringboot.models.Car;
import com.dmitriyevseyev.carmanagerspringboot.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carREST")
public class CarRESTController {
    final private CarService carService;
    final private DealerService dealerService;

    @Autowired
    public CarRESTController(CarService carService, DealerService dealerService) {
        this.carService = carService;
        this.dealerService = dealerService;
    }

    @GetMapping("/{id}")
    public List<Car> getCarsList(@PathVariable("id") Integer dealerId) {
        return carService.getCarsListByDealerId(dealerId);
    }

    @GetMapping("/getCar/{id}")
    public Car getCar(@PathVariable("id") Integer carId) {
        return carService.getCarById(carId);
    }

    @PostMapping("/create")
    public ResponseEntity<HttpStatus> newCar(@RequestBody Car car) {

        System.out.println("NEW REST CAR - " + car);

        dealerService.getDealer(car.getDealerId());
        carService.addCar(car);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<HttpStatus> updateCar(@RequestBody Car car) {

        System.out.println("REST car update - " + car);

        carService.updateCar(car);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delCar(@PathVariable("id") Integer carId) {

        System.out.println("REST del car - " + carId);

        carService.delOnlyOneCar(carId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/search")
    public List<Car> searchCar(@RequestParam("column") String column,
                               @RequestParam("pattern") String pattern,
                               @RequestParam("dealerId") String dealerId) {
        List<Car> carsList = carService.searchCar(dealerId, column, pattern);

        System.out.println("SEARCH CAR - " + carsList);

        return carsList;
    }

    @GetMapping("/searchDate")
    public List<Car> searchDateCar(@RequestParam("startDate") String startDate,
                                   @RequestParam("endDate") String endDate,
                                   @RequestParam("dealerId") String dealerId) {

        List<Car> carsList = carService.searchDateCar(dealerId, startDate, endDate);

        System.out.println("SEARCH DATE CAR - " + carsList);

        return carsList;
    }

    @GetMapping("/sort")
    public List<Car> sortCars(@RequestParam("criteria") String criteria,
                              @RequestParam("dealerId") String dealerId) {
        List<Car> carsList = carService.sortCars(dealerId, criteria);

        System.out.println("SORT CARS - " + carsList);

        return carsList;
    }
}



