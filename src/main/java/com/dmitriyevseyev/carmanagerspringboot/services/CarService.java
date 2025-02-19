package com.dmitriyevseyev.carmanagerspringboot.services;

import com.dmitriyevseyev.carmanagerspringboot.models.CarDealership;
import com.dmitriyevseyev.carmanagerspringboot.utils.Constants;
import com.dmitriyevseyev.carmanagerspringboot.utils.exeptions.NotFoundException;
import com.dmitriyevseyev.carmanagerspringboot.models.Car;
import com.dmitriyevseyev.carmanagerspringboot.models.entity.CarDealershipEntity;
import com.dmitriyevseyev.carmanagerspringboot.models.entity.CarEntity;
import com.dmitriyevseyev.carmanagerspringboot.repositories.CarRepository;
import com.dmitriyevseyev.carmanagerspringboot.utils.ConverterEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@Slf4j
public class CarService {
    private final CarRepository carRepository;
    private final DealerService dealerService;
    private final ConverterEntity converterEntity;

    @Autowired
    public CarService(CarRepository carRepository, DealerService dealerService, ConverterEntity converterEntity) {
        this.carRepository = carRepository;
        this.dealerService = dealerService;
        this.converterEntity = converterEntity;
    }

    public List<Car> getAllCarsList() {
        return dealerService.getDealersList().stream().
                flatMap(carDealership -> converterEntity.convertCarEntitiesListToCarsList(
                                carRepository.getCarEntitiesByDealer(
                                        converterEntity.convertDealerToDealerEntity(carDealership))).
                        stream()).
                collect(Collectors.toList());
    }

    public List<Car> getCarsListByDealerId(Integer dealerId) throws NotFoundException {
        CarDealershipEntity dealerEntity = converterEntity.convertDealerToDealerEntity(dealerService.getDealer(dealerId));
        return converterEntity.convertCarEntitiesListToCarsList(carRepository.getCarEntitiesByDealer(dealerEntity));
    }

    public Car getCarById(Integer carId) throws NotFoundException {
        Optional<CarEntity> carEntity = carRepository.findById(carId);
        return converterEntity.converterCarEntityToCar(carEntity.orElseThrow(
                () -> new NotFoundException(Constants.NOT_FOUND_CAR_EXCEPTION_MESSAGE + carId)));
    }

    @Transactional
    public void addCar(Car car) {
        CarDealershipEntity dealerEntity = converterEntity.convertDealerToDealerEntity(dealerService.getDealer(car.getDealerId()));
        carRepository.save(converterEntity.converterCarToCarEntity(car, dealerEntity));
    }

    @Transactional
    public void updateCar(Car car) {
        CarDealershipEntity dealerEntity = converterEntity.convertDealerToDealerEntity(dealerService.getDealer(car.getDealerId()));
        if (carRepository.existsById(car.getId())) {
            CarEntity carEntity = converterEntity.converterCarToCarEntity(car, dealerEntity);
            carRepository.save(carEntity);
        } else throw new NotFoundException(Constants.NOT_FOUND_DEALER_EXCEPTION_MESSAGE + car.getId());
    }

    @Transactional
    public void deleteCar(String carId) {
        List<Integer> carIdsList = Arrays.stream(carId.split(",")).
                map(Integer::parseInt).
                toList();
        for (Integer id : carIdsList) {
            carRepository.deleteById(id);
        }
    }

    @Transactional
    public void delOnlyOneCar(Integer carId) {
        int deleteStatus = carRepository.deleteCarEntitiyById(carId);
        System.out.println("delStatus - " + deleteStatus);
        if (deleteStatus == 0)
            throw new NotFoundException(Constants.NOT_FOUND_CAR_EXCEPTION_MESSAGE + carId);
    }

    public List<Car> searchCar(String dealerId, String column, String pattern) throws NotFoundException {
        List<Car> carsList = new ArrayList<>();
        CarDealershipEntity dealerEntity = converterEntity.convertDealerToDealerEntity
                (dealerService.getDealer(Integer.parseInt(dealerId)));

        switch (column) {
            case ("name"):
                carsList = converterEntity.convertCarEntitiesListToCarsList
                        (carRepository.findByDealerAndNameStartingWith(dealerEntity, pattern));
                break;
            case ("color"):
                carsList = converterEntity.convertCarEntitiesListToCarsList
                        (carRepository.findByDealerAndColorStartingWith(dealerEntity, pattern));
                break;
            case ("isAfterCrash"):
                carsList = converterEntity.convertCarEntitiesListToCarsList
                        (carRepository.getCarEntitiesByDealerAndIsAfterCrash(dealerEntity, Boolean.parseBoolean(pattern)));
                break;
        }
        return carsList;
    }

    public List<Car> searchDateCar(String dealerId, String startDate, String endDate) throws NotFoundException {
        List<Car> carsList = new ArrayList<>();
        CarDealershipEntity dealerEntity = converterEntity.convertDealerToDealerEntity
                (dealerService.getDealer(Integer.parseInt(dealerId)));

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date startD = null;
        Date endD = null;
        try {
            startD = formatter.parse(startDate);
            endD = formatter.parse(endDate);
        } catch (ParseException e) {
            System.out.println(" formatter.parse. " + e.getMessage());
        }

        carsList = converterEntity.convertCarEntitiesListToCarsList
                (carRepository.findByDealerAndDateBetween(dealerEntity, startD, endD));

        return carsList;
    }

    public List<Car> sortCars(String dealerId, String criteria) throws NotFoundException {
        List<Car> carsList = new ArrayList<>();
        CarDealershipEntity dealerEntity = converterEntity.convertDealerToDealerEntity
                (dealerService.getDealer(Integer.parseInt(dealerId)));

        switch (criteria) {
            case ("nameAsc"):
                carsList = converterEntity.convertCarEntitiesListToCarsList(carRepository.findByDealerOrderByNameAsc(dealerEntity));
                break;
            case ("nameDesc"):
                carsList = converterEntity.convertCarEntitiesListToCarsList(carRepository.findByDealerOrderByNameDesc(dealerEntity));
                break;
            case ("dateAsc"):
                carsList = converterEntity.convertCarEntitiesListToCarsList(carRepository.findByDealerOrderByDateAsc(dealerEntity));
                break;
            case ("dateDesc"):
                carsList = converterEntity.convertCarEntitiesListToCarsList(carRepository.findByDealerOrderByDateDesc(dealerEntity));
                break;
            case ("colorAsc"):
                carsList = converterEntity.convertCarEntitiesListToCarsList(carRepository.findByDealerOrderByColorAsc(dealerEntity));
                break;
            case ("colorDesc"):
                carsList = converterEntity.convertCarEntitiesListToCarsList(carRepository.findByDealerOrderByColorDesc(dealerEntity));
                break;
            case ("crashAsc"):
                carsList = converterEntity.convertCarEntitiesListToCarsList(carRepository.findByDealerOrderByIsAfterCrashAsc(dealerEntity));
                break;
            case ("crashDesc"):
                carsList = converterEntity.convertCarEntitiesListToCarsList(carRepository.findByDealerOrderByIsAfterCrashDesc(dealerEntity));
                break;
        }
        return carsList;
    }
}
