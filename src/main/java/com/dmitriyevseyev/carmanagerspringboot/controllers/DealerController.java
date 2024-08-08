package com.dmitriyevseyev.carmanagerspringboot.controllers;

import com.dmitriyevseyev.carmanagerspringboot.models.Car;
import com.dmitriyevseyev.carmanagerspringboot.models.CarDealership;
import com.dmitriyevseyev.carmanagerspringboot.models.entity.CarDealershipEntity;
import com.dmitriyevseyev.carmanagerspringboot.services.DealerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/dealer")
public class DealerController {
    private DealerService dealerService;

    @RequestMapping(value = "/getAllDealer", method = {RequestMethod.GET, RequestMethod.POST})
    public String getAllDealer(Model model) {
        List<CarDealership> dealerList = dealerService.getAllDealer();
        model.addAttribute("carDealerships", dealerList);
        return "dealer/getDealers";
    }

    @GetMapping("/new")
    public String newDealer(Model model) {
        model.addAttribute("dealer", new CarDealership());
        return "dealer/newDealer";
    }

    @PostMapping("/create")
    public String addDealer(@ModelAttribute("dealer") CarDealership dealer) {
        dealerService.addDealer(dealer);
        return "redirect:/dealer/getAllDealer";
    }

    @GetMapping("/edit")
    public String editDealer(@RequestParam("idDealer") String idDealerString, Model model) {
        model.addAttribute("dealer", dealerService.getDealer(Integer.parseInt(idDealerString)));
        return "dealer/updateDealer";
    }

    @PostMapping("/edit")
    public String updateDealer(@ModelAttribute("dealer") CarDealership dealer) {
        System.out.println("dealer EDIT - " + dealer);

        dealerService.updateDealer(dealer);
        return "redirect:/dealer/getAllDealer";
    }

    @GetMapping("/del")
    public String delDealer(@RequestParam("idDealer") String idDealerString) {
        dealerService.delDealer(idDealerString);
        return "redirect:/dealer/getAllDealer";
    }

    @GetMapping("/select")
    public String getAllCars(@RequestParam("idDealer") String idDealerString, Model model) {
        List<Car> carList = dealerService.getAllCars(idDealerString);

        System.out.println("CaRLIST - " + carList);


        model.addAttribute("carList", carList);
        model.addAttribute("dealer", dealerService.getDealer(Integer.parseInt(idDealerString)));
        return "car/cars";
    }

    @GetMapping("/search")
    public String searchDealer(@RequestParam("column") String column,
                               @RequestParam("pattern") String pattern,
                               Model model) {
        List<CarDealership> dealerList = new ArrayList<>();
        dealerList =  dealerService.findCarDealershipEntities (column, pattern);

        System.out.println("SEARCH - " + dealerList);

        model.addAttribute("carDealerships", dealerList);
        return "dealer/getDealers";
    }

    @GetMapping("/sort")
    public String sortDealers(@RequestParam("sort") String criteria,
                              Model model) {
        List<CarDealership> dealerList = dealerService.sortDealer(criteria);


        System.out.println("SORT - " + dealerList);


        model.addAttribute("carDealerships", dealerList);
        return "dealer/getDealers";
    }

//    public void addDealer(CarDealership dealer) throws AddDealerExeption {
//        dealerDAO.createDealer(dealer);
//    }
//
//    public CarDealership getDealer(Integer id) throws NotFoundException {
//        return dealerDAO.getDealer(id);
//    }
//
//    public CarDealership getDealerByName(String name) throws NotFoundException {
//        return dealerDAO.getDealerByName(name);
//    }
//
//    public List<CarDealership> getAllDealers() throws GetAllDealerExeption {
//        return Collections.unmodifiableList(new ArrayList<>(dealerDAO.getAll()));
//    }
//
//    public void updateDealer(CarDealership dealer) throws UpdateDealerException {
//        dealerDAO.update(dealer);
//    }
//
//    public void removeDealer(Integer id) throws DeleteDealerExeption {
//        dealerDAO.delete(id);
//    }
//
//    public List<CarDealership> getDealers(List<Integer> ids) throws NotFoundException {
//        List<CarDealership> dealersList = new ArrayList<>();
//        for (Integer id : ids) {
//            dealersList.add(getDealer(id));
//        }
//        return Collections.unmodifiableList(dealersList);
//    }
//
//    public List<CarDealership> getSortedByCriteria(String column, String criteria) throws GetAllDealerExeption {
//        return Collections.unmodifiableList(new ArrayList<>(dealerDAO.getSortedByCriteria(column, criteria)));
//    }
//
//    public List<CarDealership> getFilteredByPattern(String column, String pattern, String criteria) throws GetAllDealerExeption {
//        return Collections.unmodifiableList(new ArrayList<>(dealerDAO.getFilteredByPattern(column, pattern, criteria)));
//    }
}
