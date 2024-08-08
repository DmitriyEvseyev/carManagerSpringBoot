package com.dmitriyevseyev.carmanagerspringboot.controllers;


import com.dmitriyevseyev.carmanagerspringboot.models.Car;
import com.dmitriyevseyev.carmanagerspringboot.models.CarDealership;
import com.dmitriyevseyev.carmanagerspringboot.services.CarService;
import lombok.AllArgsConstructor;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/car")
public class CarController {
    private CarService carService;

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
//

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

        getCars(car.getIdDealer(), model);
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


        List<Car> carList = new ArrayList<>();
        carList = carService. searchCar(Integer.parseInt(idDealer), column, pattern);


        System.out.println("SEARCH CAR - " + carList);


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


//
//    public void addCar(Car car) throws AddCarExeption {
//        carDAO.createCar(car);
//    }
//
//    public Car getCar(Integer id) throws NotFoundException {
//        return carDAO.getCar(id);
//    }
//
//    public List<Car> getCarList(Integer idDealer) throws GetAllCarExeption {
//        return Collections.unmodifiableList(new ArrayList<>(carDAO.getCarListDealer(idDealer)));
//    }
//
//    public void updateCar(Car car) throws UpdateCarException {
//        carDAO.update(car);
//    }
//
//    public void removeCar(Integer id) throws NotFoundException, DeleteCarExeption {
//        carDAO.delete(id);
//    }
//
//    public List<Car> getSortedByCriteria(Integer idDealer, String column, String criteria) throws GetAllCarExeption {
//        return Collections.unmodifiableList(new ArrayList<>(carDAO.getSortedByCriteria(idDealer, column, criteria)));
//    }
//
//    public List<Car> getFilteredByPattern(Integer idDealer, String column, String pattern, String criteria) throws GetAllCarExeption {
//        return Collections.unmodifiableList(new ArrayList<>(carDAO.getFilteredByPattern(idDealer, column, pattern, criteria)));
//    }
//
//    public List<Car> getFilteredByDatePattern(Integer idDealer, String columnDate, Date startDatePattern, Date endDatePattern, String criteria) throws GetAllCarExeption {
//        return Collections.unmodifiableList(new ArrayList<>(carDAO.getFilteredByDatePattern(idDealer, columnDate, startDatePattern, endDatePattern, criteria)));
//    }
//
//    public List<Car> getFilteredByCrashPattern(Integer idDealer, String column, String pattern, String criteria) throws GetAllCarExeption {
//        return Collections.unmodifiableList(new ArrayList<>(carDAO.getFilteredByCrashPattern(idDealer, column, pattern, criteria)));
//    }
//
//    public List<Car> getCars(List<Integer> ids) throws NotFoundException {
//        List<Car> carList = new ArrayList<>();
//        for (Integer id : ids) {
//            carList.add(getCar(id));
//        }
//        return Collections.unmodifiableList(carList);
//    }
//
//    public List<Car> getCarsByDealersIds(List<Integer> ids) throws GetAllCarExeption {
//        List<Car> carList = new ArrayList<>();
//        for (Integer idDealer : ids) {
//            carList.addAll(carList.size(), getCarList(idDealer));
//        }
//        return Collections.unmodifiableList(carList);
//    }
}


