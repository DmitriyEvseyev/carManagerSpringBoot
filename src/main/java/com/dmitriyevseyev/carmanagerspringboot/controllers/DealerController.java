package com.dmitriyevseyev.carmanagerspringboot.controllers;

import com.dmitriyevseyev.carmanagerspringboot.models.Car;
import com.dmitriyevseyev.carmanagerspringboot.models.CarDealership;
import com.dmitriyevseyev.carmanagerspringboot.services.DealerService;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.SerializationUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
@RequestMapping("/dealer")
public class DealerController {
    private DealerService dealerService;
    private ExportService exportService;
    private ImportService importService;

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
        dealerList = dealerService.findCarDealershipEntities(column, pattern);

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

    @GetMapping("/export")
    public @ResponseBody ResponseEntity<ExportDTO> exportDealer
            (@RequestParam("idDealer") String idDealerString,
             @RequestParam("fileName") String fileName) throws ExportExeption, StrategyNotFoundException {

        String idCarsString = null;

        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename= " + fileName + ".json")
                .contentType(MediaType.APPLICATION_JSON)
                .body(exportService.create(idDealerString, idCarsString));
    }

    @PostMapping(value = "/import", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public String importDealer(@RequestParam("importFile") MultipartFile importFile) throws IOException, JSONValidatorExeption, ImportExeption {
        String json = new String(importFile.getBytes());
        System.out.println(json);

        ObjectMapper objectMapper = new ObjectMapper();
        ExportDTO exportDTO = objectMapper.readValue(json, ExportDTO.class);
        System.out.println("exportDTO - " + exportDTO);

        JsonValidator jsonValidator = JsonValidator.getInstance();
        jsonValidator.isValidImport(json);

        importService.importFile(json);

        return "redirect:/dealer/getAllDealer";
    }
}
