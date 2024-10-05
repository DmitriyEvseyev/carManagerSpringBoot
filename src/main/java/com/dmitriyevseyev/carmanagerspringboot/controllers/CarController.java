package com.dmitriyevseyev.carmanagerspringboot.controllers;

import com.dmitriyevseyev.carmanagerspringboot.utils.exeptions.NotFoundException;
import com.dmitriyevseyev.carmanagerspringboot.models.Car;
import com.dmitriyevseyev.carmanagerspringboot.services.CarService;
import com.dmitriyevseyev.carmanagerspringboot.services.ExportService;
import com.dmitriyevseyev.carmanagerspringboot.services.ImportService;
import com.dmitriyevseyev.carmanagerspringboot.utils.ExportDTO;
import com.dmitriyevseyev.carmanagerspringboot.utils.strategy.importFile.exeption.StrategyNotFoundException;
import com.dmitriyevseyev.carmanagerspringboot.utils.strategy.export.ExportExeption;
import com.dmitriyevseyev.carmanagerspringboot.utils.strategy.importFile.exeption.ImportExeption;
import com.dmitriyevseyev.carmanagerspringboot.utils.strategy.importFile.exeption.JSONValidatorExeption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/car")
public class CarController {
    final private CarService carService;
    final private ExportService exportService;
    final private ImportService importService;

    @Autowired
    public CarController(CarService carService, ExportService exportService, ImportService importService) {
        this.carService = carService;
        this.exportService = exportService;
        this.importService = importService;
    }

    public void getCarsList(Integer dealerId, Model model) throws NotFoundException {
        List<Car> carsList = carService.getCarsListByDealerId(dealerId);
        model.addAttribute("dealerId", dealerId);
        model.addAttribute("carsList", carsList);

        System.out.println("carsList - " + carsList);
    }

    @GetMapping("/create")
    public String newCar(@RequestParam("dealerId") String dealerId,
                         Model model) {
        model.addAttribute("car", new Car());
        model.addAttribute("dealerId", Integer.parseInt(dealerId));
        return "car/newCar";
    }

    @PostMapping("/create")
    public String addCar(@ModelAttribute("car") Car car,
                         @RequestParam(value = "isAfterCrash", required = false) String isAfterCrashString,
                         Model model) {
        Boolean isAfterCrash = (isAfterCrashString == null) ? false : true;

        System.out.println("isAfterCrash - " + isAfterCrash);
        System.out.println("CAR NEW 1111 - " + car);

        car.setAfterCrash(isAfterCrash);
        System.out.println("CAR NEW 222 - " + car);

        try {
            carService.addCar(car);
            getCarsList(car.getDealerId(), model);
        } catch (NotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
        return "car/cars";
    }

    @GetMapping("/update")
    public String updateCar(@RequestParam("check") String carId,
                            Model model) {

        System.out.println("Update check - " + carId);

        try {
            model.addAttribute("car", carService.getCarById(Integer.parseInt(carId)));
        } catch (NotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
        return "car/updateCar";
    }

    @PostMapping("/update")
    public String updateCar(@ModelAttribute("car") Car car,
                            @RequestParam(value = "isAfterCrash", required = false) String isAfterCrashString,
                            Model model) {

        System.out.println("isAfterCrashString - " + isAfterCrashString);

        Boolean isAfterCrash = (isAfterCrashString == null) ? false : true;

        System.out.println("isAfterCrash new - " + isAfterCrash);
        System.out.println("CAR EDIT 1111 - " + car);

        car.setAfterCrash(isAfterCrash);
        System.out.println("CAR EDIT 222 - " + car);

        try {
            carService.updateCar(car);
            getCarsList(car.getDealerId(), model);
        } catch (NotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
        return "car/cars";
    }

    @GetMapping("/delete")
    public String delCar(@RequestParam("dealerId") String dealerId,
                         @RequestParam("check") String carId,
                         Model model) {

        System.out.println("carId del - " + carId);

        carService.deleteCar(carId);
        try {
            getCarsList(Integer.parseInt(dealerId), model);
        } catch (NotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
        return "car/cars";
    }

    @GetMapping("/search")
    public String searchCar(@RequestParam("column") String column,
                            @RequestParam("pattern") String pattern,
                            @RequestParam("dealerId") String dealerId,
                            Model model) {
        System.out.println("dealerId - " + dealerId);
        System.out.println("String column - " + column);
        System.out.println("String pattern - " + pattern);

        List<Car> carsList = new ArrayList<>();
        try {
            carsList = carService.searchCar(dealerId, column, pattern);
        } catch (NotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }

        System.out.println("SEARCH CAR - " + carsList);

        model.addAttribute("carsList", carsList);
        model.addAttribute("dealerId", dealerId);
        return "car/cars";
    }

    @GetMapping("/searchDate")
    public String searchDateCar(@RequestParam("startDate") String startDate,
                                @RequestParam("endDate") String endDate,
                                @RequestParam("dealerId") String dealerId,
                                Model model) {
        System.out.println("dealerId - " + dealerId);
        System.out.println("startDate - " + startDate);
        System.out.println("endDate - " + endDate);

        List<Car> carsList = new ArrayList<>();
        try {
            carsList = carService.searchDateCar(dealerId, startDate, endDate);
        } catch (NotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }

        System.out.println("SEARCH CAR DATE - " + carsList);

        model.addAttribute("carsList", carsList);
        model.addAttribute("dealerId", dealerId);
        return "car/cars";
    }

    @GetMapping("/sort")
    public String sortCars(@RequestParam("sort") String criteria,
                           @RequestParam("dealerId") String dealerId,
                           Model model) {
        List<Car> carsList = new ArrayList<>();

        System.out.println("dealerId - " + dealerId);
        System.out.println("String criteria - " + criteria);

        try {
            carsList = carService.sortCars(dealerId, criteria);
        } catch (NotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }

        System.out.println("SORT CARS - " + carsList);

        model.addAttribute("carsList", carsList);
        model.addAttribute("dealerId", dealerId);
        return "car/cars";
    }

    @GetMapping("/export")
    public @ResponseBody ResponseEntity<ExportDTO> exportCars
            (@RequestParam("dealerId") String dealerId,
             @RequestParam("check") String carId,
             @RequestParam("fileName") String fileName) throws ExportExeption, StrategyNotFoundException {

        String idDealerString = null;

        System.out.println("idDealer - " + dealerId);
        System.out.println("check - " + carId);

        try {
            return ResponseEntity
                    .ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename= " + fileName + ".json")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(exportService.create(dealerId, carId));
        } catch (NotFoundException e) {
            return (ResponseEntity<ExportDTO>) ResponseEntity.status(404);
        }
    }

    @PostMapping(value = "/import", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public String importCar(@RequestParam("importFile") MultipartFile importFile,
                            @RequestParam("dealerId") String dealerId,
                            Model model) throws IOException, JSONValidatorExeption, ImportExeption {
        String json = new String(importFile.getBytes());
        System.out.println(json);
        System.out.println("String dealerId - " + dealerId);

//        ObjectMapper objectMapper = new ObjectMapper();
//        ExportDTO exportDTO = objectMapper.readValue(json, ExportDTO.class);
//        System.out.println("exportDTO - " + exportDTO);

        importService.importFile(json);

        try {
            getCarsList(Integer.parseInt(dealerId), model);
        } catch (NotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
        return "car/cars";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
}


