package com.dmitriyevseyev.carmanagerspringboot.restControllers;

import com.dmitriyevseyev.carmanagerspringboot.models.dto.CarDTO;
import com.dmitriyevseyev.carmanagerspringboot.models.dto.CarDealershipDTO;
import com.dmitriyevseyev.carmanagerspringboot.services.DealerService;
import com.dmitriyevseyev.carmanagerspringboot.utils.ConverterDTO;
import com.dmitriyevseyev.carmanagerspringboot.utils.exeptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dealers")
public class DealerRESTController {
    final private DealerService dealerService;
    final private ConverterDTO converterDTO;
    @Autowired
    public DealerRESTController(DealerService dealerService, ConverterDTO converterDTO) {
        this.dealerService = dealerService;
        this.converterDTO = converterDTO;
    }

    @GetMapping()
    public List<CarDealershipDTO> getDealersList() {
        return converterDTO.convertDealersListToDealersDTOList(dealerService.getDealersList());
    }

    @GetMapping("/{id}")
    public CarDealershipDTO getDealer(@PathVariable("id") Integer dealerId) throws NotFoundException {
        return converterDTO.convertDealerToDealerDTO(dealerService.getDealer(dealerId));
    }

    @PostMapping()
    public ResponseEntity<HttpStatus> addDealer(@RequestBody CarDealershipDTO dealerDTO) {

        System.out.println("NEW REST DEALERDTO - " + dealerDTO);

        dealerService.addDealer(converterDTO.convertDealerDTOToDealer(dealerDTO));
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<HttpStatus> updateDealer(@RequestBody CarDealershipDTO dealerDTO) {

        System.out.println("REST dealerDTO update - " + dealerDTO);

        dealerService.updateDealer(converterDTO.convertDealerDTOToDealer(dealerDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delDealer(@PathVariable("id") Integer dealerId) {

        System.out.println("REST del - " + dealerId);

        dealerService.delOnlyOneDealer(dealerId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/{id}/cars")
    public List<CarDTO> getAllCarsByDealerId(@PathVariable("id") Integer dealerId) {
        List<CarDTO> carsDTOList = converterDTO.convertCarsListToCarsDTOList(
                dealerService.getCarsList(String.valueOf(dealerId)));

        System.out.println("CarsDTOList - " + carsDTOList);

        return carsDTOList;
    }

    @GetMapping("/search")
    public List<CarDealershipDTO> searchDealer(@RequestParam("column") String column,
                                            @RequestParam("pattern") String pattern) {
        List<CarDealershipDTO> dealersDTOList = converterDTO.convertDealersListToDealersDTOList(
                dealerService.findCarDealershipEntities(column, pattern));

        System.out.println("SEARCH - " + dealersDTOList);

        return dealersDTOList;
    }

    @GetMapping("/sort")
    public List<CarDealershipDTO> sortDealers(@RequestParam("criteria") String criteria) {
        List<CarDealershipDTO> dealersDTOList = converterDTO.convertDealersListToDealersDTOList(
                dealerService.sortDealer(criteria));

        System.out.println("SORT - " + dealersDTOList);

        return dealersDTOList;
    }
}
