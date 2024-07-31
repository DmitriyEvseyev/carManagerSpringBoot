package com.dmitriyevseyev.carmanagerspringboot.services;

import com.dmitriyevseyev.carmanagerspringboot.models.Car;
import com.dmitriyevseyev.carmanagerspringboot.models.CarDealership;
import com.dmitriyevseyev.carmanagerspringboot.models.entity.CarDealershipEntity;
import com.dmitriyevseyev.carmanagerspringboot.repositories.CarRepository;
import com.dmitriyevseyev.carmanagerspringboot.repositories.DealerRepository;
import com.dmitriyevseyev.carmanagerspringboot.utils.ConverterEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class CarService {
    private CarRepository carRepository;
    private DealerRepository dealerRepository;
    private ConverterEntity converterEntity;

    public List<Car> getCarList (Integer idDealer) {
        Optional<CarDealershipEntity> dealerOptional = dealerRepository.findById(idDealer);
        CarDealershipEntity dealerEntity = dealerOptional.orElse(null);
        List<Car> carList = converterEntity.convertCarEntityToCar(carRepository.getCarEntitiesByDealer(dealerEntity));

        return carList;
    }

    public CarDealership getDealer (Integer idDealer) {
        Optional<CarDealershipEntity> dealerOptional = dealerRepository.findById(idDealer);
        CarDealershipEntity dealerEntity = dealerOptional.orElse(null);
        return converterEntity.convertDealerEntityToDealer(dealerEntity);
    }

    @Transactional
    public void addCar (Car car) {
        Optional<CarDealershipEntity> dealerOptional = dealerRepository.findById(car.getIdDealer());
        CarDealershipEntity dealer = dealerOptional.orElse(null);

        System.out.println("public void addCar service - " + car);

        carRepository.save(converterEntity.converterCarToCarEntity(car, dealer));
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
}
