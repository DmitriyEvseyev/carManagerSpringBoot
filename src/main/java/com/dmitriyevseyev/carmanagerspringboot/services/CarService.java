package com.dmitriyevseyev.carmanagerspringboot.services;

import com.dmitriyevseyev.carmanagerspringboot.exceptions.car.NotFoundException;
import com.dmitriyevseyev.carmanagerspringboot.models.Car;
import com.dmitriyevseyev.carmanagerspringboot.models.entity.CarDealershipEntity;
import com.dmitriyevseyev.carmanagerspringboot.models.entity.CarEntity;
import com.dmitriyevseyev.carmanagerspringboot.repositories.CarRepository;
import com.dmitriyevseyev.carmanagerspringboot.repositories.DealerRepository;
import com.dmitriyevseyev.carmanagerspringboot.utils.ConverterEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class CarService {
    private CarRepository carRepository;
    private DealerRepository dealerRepository;
    private ConverterEntity converterEntity;

    @Autowired
    public CarService(CarRepository carRepository, DealerRepository dealerRepository, ConverterEntity converterEntity) {
        this.carRepository = carRepository;
        this.dealerRepository = dealerRepository;
        this.converterEntity = converterEntity;
    }

    public List<Car> getCarsListByDealerId(Integer dealerId) {
        Optional<CarDealershipEntity> dealerOptional = dealerRepository.findById(dealerId);
        CarDealershipEntity dealerEntity = dealerOptional.orElse(null);
        List<Car> carList = converterEntity.convertCarEntitiesListToCarsList(carRepository.getCarEntitiesByDealer(dealerEntity));

        return carList;
    }

    public Car getCarById(Integer carId) throws NotFoundException {
        Optional<CarEntity> carEntity = carRepository.findById(carId);
        return converterEntity.converterCarEntityToCar(carEntity.orElseThrow(NotFoundException ::new));
    }

    @Transactional
    public void addCar(Car car) {
        Optional<CarDealershipEntity> dealerOptional = dealerRepository.findById(car.getDealerId());
        CarDealershipEntity dealer = dealerOptional.orElse(null);
        carRepository.save(converterEntity.converterCarToCarEntity(car, dealer));
    }

    @Transactional
    public void updateCar(Car car) {
        Optional<CarDealershipEntity> dealerOptional = dealerRepository.findById(car.getDealerId());
        CarDealershipEntity dealer = dealerOptional.orElse(null);
        CarEntity carEntity = converterEntity.converterCarToCarEntity(car, dealer);
        carRepository.save(carEntity);
    }

    @Transactional
    public void deleteCar(String carId) {
        List<Integer> carIdsList = new ArrayList<>();
        String carIdsArr[] = carId.split(",");
        for (int i = 0; i < carIdsArr.length; i++) {
            carIdsList.add(Integer.parseInt(carIdsArr[i]));
        }
        for (Integer id : carIdsList) {
            carRepository.deleteById(id);
        }
    }

    public List<Car> searchCar(String dealerId, String column, String pattern) {
        List<Car> carsList = new ArrayList<>();
        Optional<CarDealershipEntity> dealerOptional = dealerRepository.findById(Integer.parseInt(dealerId));
        CarDealershipEntity dealer = dealerOptional.orElse(null);

        switch (column) {
            case ("name"):
                carsList = converterEntity.convertCarEntitiesListToCarsList
                        (carRepository.findByDealerAndNameStartingWith(dealer, pattern));
                break;
            case ("color"):
                carsList = converterEntity.convertCarEntitiesListToCarsList
                        (carRepository.findByDealerAndColorStartingWith(dealer, pattern));
                break;
            case ("isAfterCrash"):
                carsList = converterEntity.convertCarEntitiesListToCarsList
                        (carRepository.getCarEntitiesByDealerAndIsAfterCrash(dealer, Boolean.parseBoolean(pattern)));
                break;
        }
        return carsList;
    }

    public List<Car> searchDateCar(String dealerId, String startDate, String endDate) {
        List<Car> carsList = new ArrayList<>();
        Optional<CarDealershipEntity> dealerOptional = dealerRepository.findById(Integer.parseInt(dealerId));
        CarDealershipEntity dealer = dealerOptional.orElse(null);

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
                (carRepository.findByDealerAndDateBetween(dealer, startD, endD));

        return carsList;
    }

    public List<Car> sortCars(String dealerId, String criteria) {
        List<Car> carsList = new ArrayList<>();
        Optional<CarDealershipEntity> dealerOptional = dealerRepository.findById(Integer.parseInt(dealerId));
        CarDealershipEntity dealer = dealerOptional.orElse(null);

        switch (criteria) {
            case ("nameAsc"):
                carsList = converterEntity.convertCarEntitiesListToCarsList(carRepository.findByDealerOrderByNameAsc(dealer));
                break;
            case ("nameDesc"):
                carsList = converterEntity.convertCarEntitiesListToCarsList(carRepository.findByDealerOrderByNameDesc(dealer));
                break;
            case ("dateAsc"):
                carsList = converterEntity.convertCarEntitiesListToCarsList(carRepository.findByDealerOrderByDateAsc(dealer));
                break;
            case ("dateDesc"):
                carsList = converterEntity.convertCarEntitiesListToCarsList(carRepository.findByDealerOrderByDateDesc(dealer));
                break;
            case ("colorAsc"):
                carsList = converterEntity.convertCarEntitiesListToCarsList(carRepository.findByDealerOrderByColorAsc(dealer));
                break;
            case ("colorDesc"):
                carsList = converterEntity.convertCarEntitiesListToCarsList(carRepository.findByDealerOrderByColorDesc(dealer));
                break;
            case ("crashAsc"):
                carsList = converterEntity.convertCarEntitiesListToCarsList(carRepository.findByDealerOrderByIsAfterCrashAsc(dealer));
                break;
            case ("crashDesc"):
                carsList = converterEntity.convertCarEntitiesListToCarsList(carRepository.findByDealerOrderByIsAfterCrashDesc(dealer));
                break;
        }
        return carsList;
    }
}
