package com.dmitriyevseyev.carmanagerspringboot.restControllers;

import com.dmitriyevseyev.carmanagerspringboot.models.Car;
import com.dmitriyevseyev.carmanagerspringboot.models.CarDealership;
import com.dmitriyevseyev.carmanagerspringboot.services.DealerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dealerREST")
public class DealerRESTController {
    final private DealerService dealerService;

    @Autowired
    public DealerRESTController(DealerService dealerService) {
        this.dealerService = dealerService;
    }

    @GetMapping()
    public List<CarDealership> getDealersList() {
        return dealerService.getDealersList();
    }

    @GetMapping("/{id}")
    public CarDealership getDealer(@PathVariable("id") Integer dealerId) {
        return dealerService.getDealer(dealerId);
    }

    @PostMapping("/create")
    public ResponseEntity<HttpStatus> addDealer(@RequestBody CarDealership dealer) {

        System.out.println("NEW REST DEALER - " + dealer);

        dealerService.addDealer(dealer);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<HttpStatus> updateDealer(@RequestBody CarDealership dealer) {

        System.out.println("REST dealer update - " + dealer);

        dealerService.updateDealer(dealer);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delDealer(@PathVariable("id") Integer dealerId) {

        System.out.println("REST del - " + dealerId);

        dealerService.delOnlyOneDealer(dealerId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/getCars")
    public List<Car> getAllCars(@RequestParam("dealerId") Integer dealerId) {
        List<Car> carsList = dealerService.getCarsList(String.valueOf(dealerId));

        System.out.println("CarsList - " + carsList);

        return carsList;
    }

    @GetMapping("/search")
    public List<CarDealership> searchDealer(@RequestParam("column") String column,
                                            @RequestParam("pattern") String pattern) {
        List<CarDealership> dealersList = dealerService.findCarDealershipEntities(column, pattern);

        System.out.println("SEARCH - " + dealersList);

        return dealersList;
    }

    @GetMapping("/sort")
    public List<CarDealership> sortDealers(@RequestParam("criteria") String criteria) {
        List<CarDealership> dealersList = dealerService.sortDealer(criteria);

        System.out.println("SORT - " + dealersList);

        return dealersList;
    }
}
