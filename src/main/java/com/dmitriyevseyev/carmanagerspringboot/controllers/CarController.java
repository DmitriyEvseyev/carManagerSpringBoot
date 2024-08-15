package com.dmitriyevseyev.carmanagerspringboot.controllers;

import com.dmitriyevseyev.carmanagerspringboot.models.Car;
import com.dmitriyevseyev.carmanagerspringboot.models.CarDealership;
import com.dmitriyevseyev.carmanagerspringboot.services.CarService;
import com.dmitriyevseyev.carmanagerspringboot.services.ExportService;
import com.dmitriyevseyev.carmanagerspringboot.services.ImportService;
import com.dmitriyevseyev.carmanagerspringboot.utils.ExportDTO;
import com.dmitriyevseyev.carmanagerspringboot.utils.JsonValidator;
import com.dmitriyevseyev.carmanagerspringboot.utils.strategy.StrategyNotFoundException;
import com.dmitriyevseyev.carmanagerspringboot.utils.strategy.export.ExportExeption;
import com.dmitriyevseyev.carmanagerspringboot.utils.strategy.importFile.ImportExeption;
import com.dmitriyevseyev.carmanagerspringboot.utils.strategy.importFile.JSONValidatorExeption;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@RequestMapping("/car")
public class CarController {
    private CarService carService;
    private ExportService exportService;
    private ImportService importService;

    public void getCars(Integer idDealer, Model model) {
        List<Car> carList = carService.getCarList(idDealer);
        CarDealership dealer = carService.getDealer(idDealer);
        model.addAttribute("dealer", dealer);
        model.addAttribute("carList", carList);

        System.out.println("carList - " + carList);

    }

    @GetMapping("/new")
    public String newCar(@RequestParam("idDealerM") String idDealerString,
                         Model model) {
        model.addAttribute("car", new Car());
        model.addAttribute("idDealer", Integer.parseInt(idDealerString));
        return "car/newCar";
    }

    @PostMapping("/create")
    public String addDealer(@ModelAttribute("car") Car car,
                            @RequestParam(value = "isAfterCrash", required = false) String isAfterCrashString,
                            Model model) {
        System.out.println(111);
        Boolean isAfterCrash;
        if (isAfterCrashString == null) {
            System.out.println(7777777);
            isAfterCrash = false;
        } else {
            isAfterCrash = true;
            System.out.println(00000000);
        }
        System.out.println("isAfterCrash new - " + isAfterCrash);

        System.out.println("CAR NEW 1111 - " + car);

        car.setAfterCrash(isAfterCrash);

        System.out.println("CAR NEW 222 - " + car);

        carService.addCar(car);

        getCars(car.getIdDealer(), model);
        return "car/cars";
    }


    @GetMapping("/edit")
    public String editCar(@RequestParam("check") String idCar,
                          Model model) {
        System.out.println("EDIT");
        System.out.println("check - " + idCar);

        model.addAttribute("car", carService.getCar(Integer.parseInt(idCar)));
        return "car/updateCar";
    }

    @PostMapping("/edit")
    public String updateDealer(@ModelAttribute("car") Car car,
                               @RequestParam("idDealer") Integer idDealer,
                               @RequestParam(value = "isAfterCrash", required = false) String isAfterCrashString,
                               Model model) {
        System.out.println(11111);
        System.out.println("idDealer - " + idDealer);
        System.out.println("isAfterCrashString - " + isAfterCrashString);

        Boolean isAfterCrash;
        if (isAfterCrashString == null) {
            System.out.println("7777777");
            isAfterCrash = false;
        } else {
            isAfterCrash = true;
            System.out.println("00000000");
        }
        System.out.println("isAfterCrash new - " + isAfterCrash);

        System.out.println("CAR EDIT 1111 - " + car);

        car.setAfterCrash(isAfterCrash);

        System.out.println("CAR EDIT 222 - " + car);


        System.out.println("EDIT car = Car.builder(). - " + car);


        carService.updateCar(car);
        System.out.println("33333");

        getCars(idDealer, model);
        return "car/cars";
    }

    @GetMapping("/del")
    public String delCar(@RequestParam("idDealerM") String idDealer,
                         @RequestParam("check") String idCar,
                         Model model) {

        System.out.println("idCar del - " + idCar);


        carService.delCar(idCar);
        getCars(Integer.parseInt(idDealer), model);
        return "car/cars";
    }

    @GetMapping("/search")
    public String searchCar(@RequestParam("column") String column,
                            @RequestParam("pattern") String pattern,
                            @RequestParam("idDealer") String idDealer,
                            Model model) {
        System.out.println("111");
        System.out.println("idDealer - " + idDealer);
        System.out.println("String column - " + column);
        System.out.println("String pattern - " + pattern);

        List<Car> carList = new ArrayList<>();
        carList = carService.searchCar(idDealer, column, pattern);


        System.out.println("SEARCH CAR - " + carList);


        model.addAttribute("carList", carList);
        model.addAttribute("dealer", carService.getDealer(Integer.parseInt(idDealer)));
        return "car/cars";
    }

    @GetMapping("/searchDate")
    public String searchDateCar(@RequestParam("startDate") String startDate,
                                @RequestParam("endDate") String endDate,
                                @RequestParam("idDealer") String idDealer,
                                Model model) {
        System.out.println("111");
        System.out.println("idDealer - " + idDealer);
        System.out.println("startDate - " + startDate);
        System.out.println("endDate - " + endDate);

        List<Car> carList = new ArrayList<>();
        carList = carService.searchDateCar(idDealer, startDate, endDate);


        System.out.println("SEARCH CAR DATE - " + carList);


        model.addAttribute("carList", carList);
        model.addAttribute("dealer", carService.getDealer(Integer.parseInt(idDealer)));
        return "car/cars";
    }

    @GetMapping("/sort")
    public String sortCars(@RequestParam("sort") String criteria,
                           @RequestParam("idDealer") String idDealer,
                           Model model) {
        List<Car> carList = new ArrayList<>();
        System.out.println("111");
        System.out.println("idDealer - " + idDealer);
        System.out.println("String criteria - " + criteria);

        carList = carService.sortCars(idDealer, criteria);

        System.out.println("SORT CARS - " + carList);


        model.addAttribute("carList", carList);
        model.addAttribute("dealer", carService.getDealer(Integer.parseInt(idDealer)));
        return "car/cars";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @GetMapping("/export")
    public @ResponseBody ResponseEntity<ExportDTO> exportCar
            (@RequestParam("idDealer") String idDealer,
             @RequestParam("check") String idCarsString,
             @RequestParam("fileName") String fileName) throws ExportExeption, StrategyNotFoundException {

        String idDealerString = null;


        System.out.println("idDealer - " + idDealer);
        System.out.println("check - " + idCarsString);

        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename= " + fileName + ".json")
                .contentType(MediaType.APPLICATION_JSON)
                .body(exportService.create(idDealerString, idCarsString));
    }

    @PostMapping(value = "/import", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public String importCar(@RequestParam("importFile") MultipartFile importFile,
                            @RequestParam("idDealer") String idDealer,
                            Model model) throws IOException, JSONValidatorExeption, ImportExeption {
        String json = new String(importFile.getBytes());
        System.out.println(json);
        System.out.println("String idDealer - " + idDealer);


        ObjectMapper objectMapper = new ObjectMapper();
        ExportDTO exportDTO = objectMapper.readValue(json, ExportDTO.class);
        System.out.println("exportDTO - " + exportDTO);

        JsonValidator jsonValidator = JsonValidator.getInstance();
        jsonValidator.isValidImport(json);

        importService.importFile(json);

        getCars(Integer.parseInt(idDealer), model);
        return "car/cars";
    }
}


