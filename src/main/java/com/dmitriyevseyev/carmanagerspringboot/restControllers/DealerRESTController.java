package com.dmitriyevseyev.carmanagerspringboot.restControllers;

import com.dmitriyevseyev.carmanagerspringboot.models.CarDealership;
import com.dmitriyevseyev.carmanagerspringboot.services.DealerService;
import com.dmitriyevseyev.carmanagerspringboot.utils.exeptions.NotFoundException;
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

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> delDealer(@PathVariable("id") Integer dealerId) {

        System.out.println("REST del - " + dealerId);

        dealerService.delOnlyOneDealer(dealerId);
        return ResponseEntity.ok(HttpStatus.OK);
    }
//
//    @GetMapping("/select")
//    public String getAllCars(@RequestParam("dealerId") String dealerId, Model model) {
//        List<Car> carsList = null;
//        try {
//            carsList = dealerService.getCarsList(dealerId);
//        } catch (NotFoundException e) {
//            model.addAttribute("error", Constants.NOT_FOUND_DEALER_EXCEPTION_MESSAGE);
//            return "error";
//        }
//
//        System.out.println("CarsList - " + carsList);
//
//        model.addAttribute("carsList", carsList);
//        model.addAttribute("dealerId", dealerId);
//        return "car/cars";
//    }
//
//    @GetMapping("/search")
//    public String searchDealer(@RequestParam("column") String column,
//                               @RequestParam("pattern") String pattern,
//                               Model model) {
//        List<CarDealership> dealersList = new ArrayList<>();
//        dealersList = dealerService.findCarDealershipEntities(column, pattern);
//
//        System.out.println("SEARCH - " + dealersList);
//
//        model.addAttribute("carDealerships", dealersList);
//        return "dealer/dealers";
//    }
//
//    @GetMapping("/sort")
//    public String sortDealers(@RequestParam("sort") String criteria,
//                              Model model) {
//        List<CarDealership> dealersList = dealerService.sortDealer(criteria);
//
//        System.out.println("SORT - " + dealersList);
//
//        model.addAttribute("carDealerships", dealersList);
//        return "dealer/dealers";
//    }

}
