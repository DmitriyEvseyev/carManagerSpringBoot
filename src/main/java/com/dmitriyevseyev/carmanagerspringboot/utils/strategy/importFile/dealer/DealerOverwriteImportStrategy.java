package com.dmitriyevseyev.carmanagerspringboot.utils.strategy.importFile.dealer;

import com.dmitriyevseyev.carmanagerspringboot.models.entity.CarDealershipEntity;
import com.dmitriyevseyev.carmanagerspringboot.repositories.DealerRepository;
import com.dmitriyevseyev.carmanagerspringboot.utils.strategy.importFile.exeption.ImportExeption;
import com.dmitriyevseyev.carmanagerspringboot.utils.strategy.importFile.ImportStrategy;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DealerOverwriteImportStrategy implements ImportStrategy<CarDealershipEntity> {
    private DealerRepository dealerRepository;

    @Override
    public void store(CarDealershipEntity dealer) throws ImportExeption {

        System.out.println("DealerOverwriteImportStrategy 111 - " + dealer);

        dealerRepository.save(dealer);
    }
}