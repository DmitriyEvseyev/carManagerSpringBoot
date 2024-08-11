package com.dmitriyevseyev.carmanagerspringboot.services;

import com.dmitriyevseyev.carmanagerspringboot.models.Car;
import com.dmitriyevseyev.carmanagerspringboot.models.CarDealership;
import com.dmitriyevseyev.carmanagerspringboot.models.entity.CarDealershipEntity;
import com.dmitriyevseyev.carmanagerspringboot.models.entity.CarEntity;
import com.dmitriyevseyev.carmanagerspringboot.repositories.CarRepository;
import com.dmitriyevseyev.carmanagerspringboot.repositories.DealerRepository;
import com.dmitriyevseyev.carmanagerspringboot.utils.ConverterEntity;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class CarService {
    private CarRepository carRepository;
    private DealerRepository dealerRepository;
    private ConverterEntity converterEntity;

    public List<Car> getCarList(Integer idDealer) {
        Optional<CarDealershipEntity> dealerOptional = dealerRepository.findById(idDealer);
        CarDealershipEntity dealerEntity = dealerOptional.orElse(null);
        List<Car> carList = converterEntity.convertCarListEntityToCarList(carRepository.getCarEntitiesByDealer(dealerEntity));

        return carList;
    }

    public List<CarEntity> getCarEntityList(Integer idDealer) {
        Optional<CarDealershipEntity> dealerOptional = dealerRepository.findById(idDealer);
        CarDealershipEntity dealerEntity = dealerOptional.orElse(null);

        List<CarEntity> carEntityList = carRepository.getCarEntitiesByDealer(dealerEntity);

        return carEntityList;
    }

    public Car getCar(Integer idCar) {
        Optional<CarEntity> carEntity = carRepository.findById(idCar);
        return converterEntity.converterCarEntityToCar(carEntity.orElse(null));
    }

    public CarEntity getCarEntity(Integer idCar) {
        return carRepository.findById(idCar).orElse(null);
    }


    public CarDealership getDealer(Integer idDealer) {
        Optional<CarDealershipEntity> dealerOptional = dealerRepository.findById(idDealer);
        CarDealershipEntity dealerEntity = dealerOptional.orElse(null);
        return converterEntity.convertDealerEntityToDealer(dealerEntity);
    }

    @Transactional
    public void addCar(Car car) {
        Optional<CarDealershipEntity> dealerOptional = dealerRepository.findById(car.getIdDealer());
        CarDealershipEntity dealer = dealerOptional.orElse(null);

        System.out.println("public void addCar service - " + car);

        carRepository.save(converterEntity.converterCarToCarEntity(car, dealer));
    }


    @Transactional
    public void updateCar(Car car) {
        Optional<CarDealershipEntity> dealerOptional = dealerRepository.findById(car.getIdDealer());
        CarDealershipEntity dealer = dealerOptional.orElse(null);


        System.out.println("public void updateCar service - " + car);


        System.out.println(dealer);
        CarEntity carEntity = converterEntity.converterCarToCarEntity(car, dealer);


        System.out.println("carEntity EDIT + " + carEntity);

        carRepository.save(carEntity);
    }

    @Transactional
    public void delCar(String idCarString) {
        List<Integer> idCarsList = new ArrayList<>();
        String idCarsArr[] = idCarString.split(",");
        for (int i = 0; i < idCarsArr.length; i++) {
            idCarsList.add(Integer.parseInt(idCarsArr[i]));
        }
        for (Integer idCar : idCarsList) {
            carRepository.deleteById(idCar);
        }
    }

    public List<Car> searchCar(String idDealer, String column, String pattern) {
        List<Car> carList = new ArrayList<>();
        Optional<CarDealershipEntity> dealerOptional = dealerRepository.findById(Integer.parseInt(idDealer));
        CarDealershipEntity dealer = dealerOptional.orElse(null);

        switch (column) {
            case ("name"):
                carList = converterEntity.convertCarListEntityToCarList
                        (carRepository.findByDealerAndNameStartingWith(dealer, pattern));
                break;
            case ("color"):
                carList = converterEntity.convertCarListEntityToCarList
                        (carRepository.findByDealerAndColorStartingWith(dealer, pattern));
                break;
            case ("isAfterCrash"):
                carList = converterEntity.convertCarListEntityToCarList
                        (carRepository.getCarEntitiesByDealerAndIsAfterCrash(dealer, Boolean.parseBoolean(pattern)));
                break;
        }
        return carList;
    }

    public List<Car> searchDateCar(String idDealer, String startDate, String endDate) {
        List<Car> carList = new ArrayList<>();
        Optional<CarDealershipEntity> dealerOptional = dealerRepository.findById(Integer.parseInt(idDealer));
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

        carList = converterEntity.convertCarListEntityToCarList
                (carRepository.findByDealerAndDateBetween(dealer, startD, endD));

        return carList;
    }

    public List<Car> sortCars(String idDealer, String criteria) {
        List<Car> carList = new ArrayList<>();
        Optional<CarDealershipEntity> dealerOptional = dealerRepository.findById(Integer.parseInt(idDealer));
        CarDealershipEntity dealer = dealerOptional.orElse(null);

        switch (criteria) {
            case ("nameAsc"):
                carList = converterEntity.convertCarListEntityToCarList(carRepository.findByDealerOrderByNameAsc(dealer));
                break;
            case ("nameDesc"):
                carList = converterEntity.convertCarListEntityToCarList(carRepository.findByDealerOrderByNameDesc(dealer));
                break;
            case ("dateAsc"):
                carList = converterEntity.convertCarListEntityToCarList(carRepository.findByDealerOrderByDateAsc(dealer));
                break;
            case ("dateDesc"):
                carList = converterEntity.convertCarListEntityToCarList(carRepository.findByDealerOrderByDateDesc(dealer));
                break;
            case ("colorAsc"):
                carList = converterEntity.convertCarListEntityToCarList(carRepository.findByDealerOrderByColorAsc(dealer));
                break;
            case ("colorDesc"):
                carList = converterEntity.convertCarListEntityToCarList(carRepository.findByDealerOrderByColorDesc(dealer));
                break;
            case ("crashAsc"):
                carList = converterEntity.convertCarListEntityToCarList(carRepository.findByDealerOrderByIsAfterCrashAsc(dealer));
                break;
            case ("crashDesc"):
                carList = converterEntity.convertCarListEntityToCarList(carRepository.findByDealerOrderByIsAfterCrashDesc(dealer));
                break;
        }
        return carList;
    }
}
