package com.dmitriyevseyev.carmanagerspringboot.restControllers;

import com.dmitriyevseyev.carmanagerspringboot.services.DealerService;
import com.dmitriyevseyev.carmanagerspringboot.utils.exeptions.NotFoundException;
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
    public ResponseEntity<HttpStatus> delCar (@PathVariable("id") Integer carId) {

        System.out.println("REST del car - " + carId);

        carService.delOnlyOneCar(carId);
        return ResponseEntity.ok(HttpStatus.OK);
    }
//
//    @GetMapping("/search")
//    public String searchCar(@RequestParam("column") String column,
//                            @RequestParam("pattern") String pattern,
//                            @RequestParam("dealerId") String dealerId,
//                            Model model) {
//        System.out.println("dealerId - " + dealerId);
//        System.out.println("String column - " + column);
//        System.out.println("String pattern - " + pattern);
//
//        List<Car> carsList = new ArrayList<>();
//        try {
//            carsList = carService.searchCar(dealerId, column, pattern);
//        } catch (NotFoundException e) {
//            model.addAttribute("error", e.getMessage());
//            return "error";
//        }
//
//        System.out.println("SEARCH CAR - " + carsList);
//
//        model.addAttribute("carsList", carsList);
//        model.addAttribute("dealerId", dealerId);
//        return "car/cars";
//    }
//
//    @GetMapping("/searchDate")
//    public String searchDateCar(@RequestParam("startDate") String startDate,
//                                @RequestParam("endDate") String endDate,
//                                @RequestParam("dealerId") String dealerId,
//                                Model model) {
//        System.out.println("dealerId - " + dealerId);
//        System.out.println("startDate - " + startDate);
//        System.out.println("endDate - " + endDate);
//
//        List<Car> carsList = new ArrayList<>();
//        try {
//            carsList = carService.searchDateCar(dealerId, startDate, endDate);
//        } catch (NotFoundException e) {
//            model.addAttribute("error", e.getMessage());
//            return "error";
//        }
//
//        System.out.println("SEARCH CAR DATE - " + carsList);
//
//        model.addAttribute("carsList", carsList);
//        model.addAttribute("dealerId", dealerId);
//        return "car/cars";
//    }
//
//    @GetMapping("/sort")
//    public String sortCars(@RequestParam("sort") String criteria,
//                           @RequestParam("dealerId") String dealerId,
//                           Model model) {
//        List<Car> carsList = new ArrayList<>();
//
//        System.out.println("dealerId - " + dealerId);
//        System.out.println("String criteria - " + criteria);
//
//        try {
//            carsList = carService.sortCars(dealerId, criteria);
//        } catch (NotFoundException e) {
//            model.addAttribute("error", e.getMessage());
//            return "error";
//        }
//
//        System.out.println("SORT CARS - " + carsList);
//
//        model.addAttribute("carsList", carsList);
//        model.addAttribute("dealerId", dealerId);
//        return "car/cars";
//    }
//
//    @GetMapping("/export")
//    public @ResponseBody ResponseEntity<ExportDTO> exportCars
//            (@RequestParam("dealerId") String dealerId,
//             @RequestParam("check") String carId,
//             @RequestParam("fileName") String fileName) throws ExportExeption, StrategyNotFoundException {
//
//        String idDealerString = null;
//
//        System.out.println("idDealer - " + dealerId);
//        System.out.println("check - " + carId);
//
//        try {
//            return ResponseEntity
//                    .ok()
//                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename= " + fileName + ".json")
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .body(exportService.create(dealerId, carId));
//        } catch (NotFoundException e) {
//            return (ResponseEntity<ExportDTO>) ResponseEntity.status(404);
//        }
//    }
//
//    @PostMapping(value = "/import", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
//    public String importCar(@RequestParam("importFile") MultipartFile importFile,
//                            @RequestParam("dealerId") String dealerId,
//                            Model model) throws IOException, JSONValidatorExeption, ImportExeption {
//        String json = new String(importFile.getBytes());
//        System.out.println(json);
//        System.out.println("String dealerId - " + dealerId);
//
////        ObjectMapper objectMapper = new ObjectMapper();
////        ExportDTO exportDTO = objectMapper.readValue(json, ExportDTO.class);
////        System.out.println("exportDTO - " + exportDTO);
//
//        importService.importFile(json);
//
//        try {
//            getCarsList(Integer.parseInt(dealerId), model);
//        } catch (NotFoundException e) {
//            model.addAttribute("error", e.getMessage());
//            return "error";
//        }
//        return "car/cars";
//    }
//
//    @InitBinder
//    public void initBinder(WebDataBinder binder) {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        dateFormat.setLenient(false);
//        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
//    }
}



