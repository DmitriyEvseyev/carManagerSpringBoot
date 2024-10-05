package com.dmitriyevseyev.carmanagerspringboot.controllers;

import com.dmitriyevseyev.carmanagerspringboot.utils.Constants;
import com.dmitriyevseyev.carmanagerspringboot.utils.exeptions.NotFoundException;
import com.dmitriyevseyev.carmanagerspringboot.models.Car;
import com.dmitriyevseyev.carmanagerspringboot.models.CarDealership;
import com.dmitriyevseyev.carmanagerspringboot.services.DealerService;
import com.dmitriyevseyev.carmanagerspringboot.services.ExportService;
import com.dmitriyevseyev.carmanagerspringboot.services.ImportService;
import com.dmitriyevseyev.carmanagerspringboot.utils.ExportDTO;
import com.dmitriyevseyev.carmanagerspringboot.utils.strategy.importFile.exeption.StrategyNotFoundException;
import com.dmitriyevseyev.carmanagerspringboot.utils.strategy.export.ExportExeption;
import com.dmitriyevseyev.carmanagerspringboot.utils.strategy.importFile.exeption.ImportExeption;
import com.dmitriyevseyev.carmanagerspringboot.utils.strategy.importFile.exeption.JSONValidatorExeption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/dealer")
public class DealerController {
    final private DealerService dealerService;
    final private ExportService exportService;
    final private ImportService importService;

    @Autowired
    public DealerController(DealerService dealerService, ExportService exportService, ImportService importService) {
        this.dealerService = dealerService;
        this.exportService = exportService;
        this.importService = importService;
    }

    @RequestMapping(value = "/getDealers", method = {RequestMethod.GET, RequestMethod.POST})
    public String getDealersList(Model model) {
        List<CarDealership> dealersList = dealerService.getDealersList();
        model.addAttribute("carDealerships", dealersList);
        return "dealer/dealers";
    }

    @GetMapping("/create")
    public String newDealer(Model model) {
        model.addAttribute("dealer", new CarDealership());
        return "dealer/newDealer";
    }

    @PostMapping("/create")
    public String addDealer(@ModelAttribute("dealer") CarDealership dealer) {
        dealerService.addDealer(dealer);
        return "redirect:/dealer/getDealers";
    }

    @GetMapping("/update")
    public String updateDealer(@RequestParam("dealerId") String dealerId, Model model) {
        try {
            model.addAttribute("dealer", dealerService.getDealer(Integer.parseInt(dealerId)));
        } catch (NotFoundException e) {
            model.addAttribute("error", Constants.NOT_FOUND_DEALER_EXCEPTION_MESSAGE);
            return "error";
        }
        return "dealer/updateDealer";
    }

    @PostMapping("/update")
    public String updateDealer(@ModelAttribute("dealerId") CarDealership dealer) {

        System.out.println("dealer update - " + dealer);

        dealerService.updateDealer(dealer);
        return "redirect:/dealer/getDealers";
    }

    @GetMapping("/delete")
    public String delDealer(@RequestParam("dealerId") String dealerId) {
        dealerService.delDealer(dealerId);
        return "redirect:/dealer/getDealers";
    }

    @GetMapping("/select")
    public String getAllCars(@RequestParam("dealerId") String dealerId, Model model) {
        List<Car> carsList = null;
        try {
            carsList = dealerService.getCarsList(dealerId);
        } catch (NotFoundException e) {
            model.addAttribute("error", Constants.NOT_FOUND_DEALER_EXCEPTION_MESSAGE);
            return "error";
        }

        System.out.println("CarsList - " + carsList);

        model.addAttribute("carsList", carsList);
        model.addAttribute("dealerId", dealerId);
        return "car/cars";
    }

    @GetMapping("/search")
    public String searchDealer(@RequestParam("column") String column,
                               @RequestParam("pattern") String pattern,
                               Model model) {
        List<CarDealership> dealersList = new ArrayList<>();
        dealersList = dealerService.findCarDealershipEntities(column, pattern);

        System.out.println("SEARCH - " + dealersList);

        model.addAttribute("carDealerships", dealersList);
        return "dealer/dealers";
    }

    @GetMapping("/sort")
    public String sortDealers(@RequestParam("sort") String criteria,
                              Model model) {
        List<CarDealership> dealersList = dealerService.sortDealer(criteria);

        System.out.println("SORT - " + dealersList);

        model.addAttribute("carDealerships", dealersList);
        return "dealer/dealers";
    }

    @GetMapping("/export")
    public @ResponseBody ResponseEntity<ExportDTO> exportDealer
            (@RequestParam("dealerId") String dealerId,
             @RequestParam("fileName") String fileName) throws ExportExeption, StrategyNotFoundException {

        String carId = null;

        try {
            return ResponseEntity
                    .ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename= " + fileName + ".json")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(exportService.create(dealerId, carId));
        } catch (NotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping(value = "/import", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public String importDealer(@RequestParam("importFile") MultipartFile importFile) throws IOException, JSONValidatorExeption, ImportExeption {
        String json = new String(importFile.getBytes());


        System.out.println(json);


        importService.importFile(json);
        return "redirect:/dealer/getDealers";
    }
}
