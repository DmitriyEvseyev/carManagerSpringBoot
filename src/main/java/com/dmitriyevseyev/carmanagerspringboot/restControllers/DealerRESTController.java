package com.dmitriyevseyev.carmanagerspringboot.restControllers;

import com.dmitriyevseyev.carmanagerspringboot.models.dto.CarDTO;
import com.dmitriyevseyev.carmanagerspringboot.models.dto.CarDealershipDTO;
import com.dmitriyevseyev.carmanagerspringboot.services.DealerService;
import com.dmitriyevseyev.carmanagerspringboot.utils.ConverterDTO;
import com.dmitriyevseyev.carmanagerspringboot.utils.exeptions.CreatedExeption;
import com.dmitriyevseyev.carmanagerspringboot.utils.exeptions.NotFoundException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dealers")
@Slf4j
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
        List<CarDealershipDTO> dealerDTOList = converterDTO.convertDealersListToDealersDTOList(dealerService.getDealersList());
        log.info("DealerDTOList - {}", dealerDTOList);
        return dealerDTOList;
    }

    @GetMapping("/{id}")
    public CarDealershipDTO getDealer(@PathVariable("id") Integer dealerId) throws NotFoundException {
        CarDealershipDTO dealerDTO = converterDTO.convertDealerToDealerDTO(dealerService.getDealer(dealerId));
        log.info("DealerDTO - {}", dealerDTO);
        return dealerDTO;
    }

    @PostMapping()
    public ResponseEntity<HttpStatus> addDealer(@RequestBody @Valid CarDealershipDTO dealerDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMes = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMes.append("-").append(error.getDefaultMessage()).append("; ");
            }
            throw new CreatedExeption(errorMes.toString());
        }
        log.info("NEW,  dealerDTO -  {}", dealerDTO);
        dealerService.addDealer(converterDTO.convertDealerDTOToDealer(dealerDTO));
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<HttpStatus> updateDealer(@RequestBody CarDealershipDTO dealerDTO) {

        log.info("UPDATE, dealerDTO - {}", dealerDTO);

        dealerService.updateDealer(converterDTO.convertDealerDTOToDealer(dealerDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delDealer(@PathVariable("id") Integer dealerId) {

        log.info("DELETE, dealer.id - {}", dealerId);

        dealerService.delOnlyOneDealer(dealerId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/{id}/cars")
    public List<CarDTO> getAllCarsByDealerId(@PathVariable("id") Integer dealerId) {
        List<CarDTO> carsDTOList = converterDTO.convertCarsListToCarsDTOList(
                dealerService.getCarsList(String.valueOf(dealerId)));

        log.info("CarsDTOList - {}", carsDTOList);
        return carsDTOList;
    }

    @GetMapping("/search")
    public List<CarDealershipDTO> searchDealer(@RequestParam("column") String column,
                                               @RequestParam("pattern") String pattern) {
        List<CarDealershipDTO> dealersDTOList = converterDTO.convertDealersListToDealersDTOList(
                dealerService.findCarDealershipEntities(column, pattern));

        log.info("The found dealer -  {}", dealersDTOList);

        return dealersDTOList;
    }

    @GetMapping("/sort")
    public List<CarDealershipDTO> sortDealers(@RequestParam("criteria") String criteria) {
        List<CarDealershipDTO> dealersDTOList = converterDTO.convertDealersListToDealersDTOList(
                dealerService.sortDealer(criteria));

        log.info("Sorted dealerDTOlist -  {}", dealersDTOList);
        return dealersDTOList;
    }
}
