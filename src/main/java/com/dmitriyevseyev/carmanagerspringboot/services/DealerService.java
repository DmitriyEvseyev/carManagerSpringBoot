package com.dmitriyevseyev.carmanagerspringboot.services;

import com.dmitriyevseyev.carmanagerspringboot.models.CarDealership;
import com.dmitriyevseyev.carmanagerspringboot.models.entity.CarDealershipEntity;
import com.dmitriyevseyev.carmanagerspringboot.repositories.DealerRepository;
import com.dmitriyevseyev.carmanagerspringboot.utils.ConverterEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class DealerService {
    private DealerRepository dealerRepository;
    private ConverterEntity converterEntity;

    public List<CarDealership> getAllDealer() {
        List<CarDealership> dealerList;
        dealerList = converterEntity.convertDealerEntityToDealer(dealerRepository.findAll());
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

        System.out.println("delDealer - " + idDealerList);
        System.out.println(idDealerList.get(0));


        for (Integer idDealer : idDealerList) {
            dealerRepository.deleteById(idDealer);
        }
    }
}

