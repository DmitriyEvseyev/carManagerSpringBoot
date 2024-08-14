package com.dmitriyevseyev.carmanagerspringboot.utils.strategy.importFile.dealer;

import com.dmitriyevseyev.carmanagerspringboot.models.entity.CarDealershipEntity;
import com.dmitriyevseyev.carmanagerspringboot.repositories.DealerRepository;
import com.dmitriyevseyev.carmanagerspringboot.utils.strategy.importFile.ImportStrategy;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DealerIgnoreImportStrategy implements ImportStrategy<CarDealershipEntity> {
    private DealerRepository dealerRepository;

    @Override
    public void store(CarDealershipEntity dealer) {

        if (dealerRepository.getCarDealershipEntityByName(dealer.getName()) == null &&
                dealerRepository.findById(dealer.getId()) == null) {

            dealerRepository.save(dealer);
        }
    }
}