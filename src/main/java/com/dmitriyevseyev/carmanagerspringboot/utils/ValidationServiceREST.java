package com.dmitriyevseyev.carmanagerspringboot.utils;
import com.dmitriyevseyev.carmanagerspringboot.models.CarDealership;
import com.dmitriyevseyev.carmanagerspringboot.models.dto.CarDealershipDTO;

import com.dmitriyevseyev.carmanagerspringboot.utils.exeptions.CreatedExeption;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ValidationServiceREST {
    public void validateInput(CarDealership dealer) throws IOException, SAXException {

        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<CarDealership>> violations = validator.validate(dealer);
        if (!violations.isEmpty()) {
            throw new CreatedExeption(violations.toString());
        }
    }
}