package com.dmitriyevseyev.carmanagerspringboot.restControllers;

import com.dmitriyevseyev.carmanagerspringboot.models.Car;
import com.dmitriyevseyev.carmanagerspringboot.models.CarDealership;
import com.dmitriyevseyev.carmanagerspringboot.services.DealerService;
import com.dmitriyevseyev.carmanagerspringboot.services.ExportService;
import com.dmitriyevseyev.carmanagerspringboot.services.ImportService;
import com.dmitriyevseyev.carmanagerspringboot.utils.Constants;
import com.dmitriyevseyev.carmanagerspringboot.utils.ExportDTO;
import com.dmitriyevseyev.carmanagerspringboot.utils.NotFoundException;
import com.dmitriyevseyev.carmanagerspringboot.utils.strategy.StrategyNotFoundException;
import com.dmitriyevseyev.carmanagerspringboot.utils.strategy.export.ExportExeption;
import com.dmitriyevseyev.carmanagerspringboot.utils.strategy.importFile.ImportExeption;
import com.dmitriyevseyev.carmanagerspringboot.utils.strategy.importFile.JSONValidatorExeption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
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

    @ExceptionHandler
    private ResponseEntity<String> handleExeption(NotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create")
    public ResponseEntity<HttpStatus> addDealer(@RequestBody CarDealership dealer) {

        System.out.println("NEW REST DEALER - " + dealer);

        dealerService.addDealer(dealer);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PostMapping("/update")
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
//
//    @GetMapping("/export")
//    public @ResponseBody ResponseEntity<ExportDTO> exportDealer
//            (@RequestParam("dealerId") String dealerId,
//             @RequestParam("fileName") String fileName) throws ExportExeption, StrategyNotFoundException {
//
//        String carId = null;
//
//        try {
//            return ResponseEntity
//                    .ok()
//                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename= " + fileName + ".json")
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .body(exportService.create(dealerId, carId));
//        } catch (NotFoundException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @PostMapping(value = "/import", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
//    public String importDealer(@RequestParam("importFile") MultipartFile importFile) throws IOException, JSONValidatorExeption, ImportExeption {
//        String json = new String(importFile.getBytes());
//
//
//        System.out.println(json);
//
//
//        importService.importFile(json);
//        return "redirect:/dealer/getDealers";
//    }
//}


}
