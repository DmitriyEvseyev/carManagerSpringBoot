package com.dmitriyevseyev.carmanagerspringboot.services;

import com.dmitriyevseyev.carmanagerspringboot.models.Car;
import com.dmitriyevseyev.carmanagerspringboot.models.CarDealership;
import com.dmitriyevseyev.carmanagerspringboot.models.entity.CarDealershipEntity;
import com.dmitriyevseyev.carmanagerspringboot.repositories.DealerRepository;
import com.dmitriyevseyev.carmanagerspringboot.utils.Constants;
import com.dmitriyevseyev.carmanagerspringboot.utils.ConverterEntity;

import com.dmitriyevseyev.carmanagerspringboot.utils.ValidationServiceREST;
import com.dmitriyevseyev.carmanagerspringboot.utils.exeptions.NotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(readOnly = true)
public class DealerService {
    private final DealerRepository dealerRepository;
    private final ConverterEntity converterEntity;
    private final Validator validator;

    @Autowired
    public DealerService(DealerRepository dealerRepository, ConverterEntity converterEntity, Validator validator) {
        this.dealerRepository = dealerRepository;
        this.converterEntity = converterEntity;
        this.validator = validator;
    }

    public List<CarDealership> getDealersList() {
        List<CarDealership> dealersList;
        dealersList = converterEntity.convertDealerEntitiesListToDealersList(dealerRepository.findAll());
        return dealersList;
    }

    public CarDealership getDealer(Integer id) throws NotFoundException {
        Optional<CarDealershipEntity> dealerEntity = dealerRepository.findById(id);
        return converterEntity.convertDealerEntityToDealer(dealerEntity.orElseThrow(
                () -> new NotFoundException(Constants.NOT_FOUND_DEALER_EXCEPTION_MESSAGE + id)));
    }

    @Transactional
    public void addDealer(CarDealership dealer) {
//        Set<ConstraintViolation<CarDealership>> violations = validator.validate(dealer);
//        if (!violations.isEmpty()) {
//            throw new ConstraintViolationException(violations);
//        }
        dealerRepository.save(converterEntity.convertDealerToDealerEntity(dealer));

    }

    @Transactional
    public void updateDealer(CarDealership dealer) throws NotFoundException {
        if (dealerRepository.existsById(dealer.getId())) {
            dealerRepository.save(converterEntity.convertDealerToDealerEntity(dealer));
        } else throw new NotFoundException(Constants.NOT_FOUND_DEALER_EXCEPTION_MESSAGE + dealer.getId());
    }

    @Transactional
    public void delOnlyOneDealer(Integer dealerId) throws NotFoundException {
        int deleteStatus = dealerRepository.deleteCarDealershipEntityById(dealerId);
        System.out.println("delStatus - " + deleteStatus);
        if (deleteStatus == 0)
            throw new NotFoundException(Constants.NOT_FOUND_DEALER_EXCEPTION_MESSAGE + dealerId);
    }

    @Transactional
    public void delDealer(String dealerId) {
        List<Integer> dealerIdArrList = Arrays.stream(dealerId.split(",")).
                map(Integer::parseInt).
                toList();

        System.out.println("dealerIdArrList - " + dealerIdArrList);

        for (Integer id : dealerIdArrList) {
            dealerRepository.deleteById(id);
        }
    }


    public List<Car> getCarsList(String idDealer) throws NotFoundException {
        Optional<CarDealershipEntity> dealerOptional = dealerRepository.findById(Integer.parseInt(idDealer));
        CarDealershipEntity dealer = dealerOptional.orElseThrow(
                () -> new NotFoundException(Constants.NOT_FOUND_DEALER_EXCEPTION_MESSAGE + idDealer));
        List<Car> carsList = converterEntity.convertCarEntitiesListToCarsList(dealer.getCarEntities());
        return carsList;
    }

    public List<CarDealership> findCarDealershipEntities(String column, String pattern) {
        List<CarDealership> dealerList = new ArrayList<>();
        if (column.equals("name")) {
            dealerList = converterEntity.convertDealerEntitiesListToDealersList
                    (dealerRepository.findByNameStartingWith(pattern));
        } else if (column.equals("address")) {
            dealerList = converterEntity.convertDealerEntitiesListToDealersList
                    (dealerRepository.findByAddressStartingWith(pattern));
        }
        return dealerList;
    }

    public List<CarDealership> sortDealer(String criteria) {
        List<CarDealership> dealerList = new ArrayList<>();
        switch (criteria) {
            case ("nameDesc"):
                dealerList = converterEntity.convertDealerEntitiesListToDealersList(dealerRepository.findByOrderByNameDesc());
                break;
            case ("nameAsc"):
                dealerList = converterEntity.convertDealerEntitiesListToDealersList(dealerRepository.findByOrderByNameAsc());
                break;
            case ("addressDesc"):
                dealerList = converterEntity.convertDealerEntitiesListToDealersList(dealerRepository.findByOrderByAddressDesc());
                break;
            case ("addressAsc"):
                dealerList = converterEntity.convertDealerEntitiesListToDealersList(dealerRepository.findByOrderByAddressAsc());
                break;
        }
        return dealerList;
    }
}

