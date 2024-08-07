package com.dmitriyevseyev.carmanagerspringboot.services;

import com.dmitriyevseyev.carmanagerspringboot.models.Car;
import com.dmitriyevseyev.carmanagerspringboot.models.CarDealership;
import com.dmitriyevseyev.carmanagerspringboot.models.entity.CarDealershipEntity;
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
public class DealerService {
    private DealerRepository dealerRepository;
    private ConverterEntity converterEntity;

    public List<CarDealership> getAllDealer() {
        List<CarDealership> dealerList;
        dealerList = converterEntity.convertDealerEntityLisToDealerList(dealerRepository.findAll());
        return dealerList;
    }

    @Transactional
    public void addDealer(CarDealership dealer) {
        dealerRepository.save(converterEntity.convertDealerToDealerEntity(dealer));
    }

    @Transactional
    public void delDealer(String idDealerString) {
        List<Integer> idDealerList = new ArrayList<>();
        String idDealerArr[] = idDealerString.split(",");
        for (int i = 0; i < idDealerArr.length; i++) {
            idDealerList.add(Integer.parseInt(idDealerArr[i]));
        }
        for (Integer idDealer : idDealerList) {
            dealerRepository.deleteById(idDealer);
        }
    }

    public CarDealership getDealer(Integer id) {
        Optional<CarDealershipEntity> dealerEntity = dealerRepository.findById(id);
        return converterEntity.convertDealerEntityToDealer(dealerEntity.orElse(null));
    }

    @Transactional
    public void updateDealer(CarDealership dealer) {
        dealerRepository.save(converterEntity.convertDealerToDealerEntity(dealer));
    }

    public List<Car> getAllCars(String idDealer) {

        Optional<CarDealershipEntity> dealerOptional = dealerRepository.findById(Integer.parseInt(idDealer));
        CarDealershipEntity dealer = dealerOptional.orElse(null);
        List<Car> carList = converterEntity.convertCarListEntityToCarList(dealer.getCarEntities());

        System.out.println("CARLIST service - " + carList);


        return carList;
    }
}

