package com.dmitriyevseyev.carmanagerspringboot.services;

import com.dmitriyevseyev.carmanagerspringboot.models.Car;
import com.dmitriyevseyev.carmanagerspringboot.models.CarDealership;
import com.dmitriyevseyev.carmanagerspringboot.models.entity.CarDealershipEntity;
import com.dmitriyevseyev.carmanagerspringboot.repositories.DealerRepository;
import com.dmitriyevseyev.carmanagerspringboot.utils.ConverterEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class DealerService {
    private DealerRepository dealerRepository;
    private ConverterEntity converterEntity;

    @Autowired
    public DealerService(DealerRepository dealerRepository, ConverterEntity converterEntity) {
        this.dealerRepository = dealerRepository;
        this.converterEntity = converterEntity;
    }

    public List<CarDealership> getDealersList() {
        List<CarDealership> dealersList;
        dealersList = converterEntity.convertDealerEntitiesListToDealersList(dealerRepository.findAll());
        return dealersList;
    }

    public CarDealership getDealer(Integer id) {
        Optional<CarDealershipEntity> dealerEntity = dealerRepository.findById(id);
        return converterEntity.convertDealerEntityToDealer(dealerEntity.orElse(null));
    }

    @Transactional
    public void addDealer(CarDealership dealer) {
        dealerRepository.save(converterEntity.convertDealerToDealerEntity(dealer));
    }

    @Transactional
    public void updateDealer(CarDealership dealer) {
        System.out.println("upd - " + dealer);
        dealerRepository.save(converterEntity.convertDealerToDealerEntity(dealer));
    }

    @Transactional
    public void delDealer(String dealerId) {
        List<Integer> dealerIdArrList = new ArrayList<>();
        String dealerIdArr[] = dealerId.split(",");
        for (int i = 0; i < dealerIdArr.length; i++) {
            dealerIdArrList.add(Integer.parseInt(dealerIdArr[i]));
        }
        for (Integer id : dealerIdArrList) {
            dealerRepository.deleteById(id);
        }
    }

    public List<Car> getCarsList(String idDealer) {

        Optional<CarDealershipEntity> dealerOptional = dealerRepository.findById(Integer.parseInt(idDealer));
        CarDealershipEntity dealer = dealerOptional.orElse(null);
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

