package com.dmitriyevseyev.carmanagerspringboot.utils.strategy.importFile.dealer;

import com.dmitriyevseyev.carmanagerspringboot.exceptions.DAOFactoryActionException;
import com.dmitriyevseyev.carmanagerspringboot.exceptions.car.NotFoundException;
import com.dmitriyevseyev.carmanagerspringboot.exceptions.dealer.AddDealerExeption;
import com.dmitriyevseyev.carmanagerspringboot.exceptions.dealer.DealerIdAlreadyExistException;
import com.dmitriyevseyev.carmanagerspringboot.models.entity.CarDealershipEntity;
import com.dmitriyevseyev.carmanagerspringboot.repositories.DealerRepository;
import com.dmitriyevseyev.carmanagerspringboot.services.DealerService;
import com.dmitriyevseyev.carmanagerspringboot.utils.strategy.importFile.ImportExeption;
import com.dmitriyevseyev.carmanagerspringboot.utils.strategy.importFile.ImportStrategy;
import com.dmitriyevseyev.carmanagerspringboot.utils.strategy.importFile.exeption.DealerNameAlreadyExistException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DealerConflictImportStrategy implements ImportStrategy<CarDealershipEntity> {
    private DealerRepository dealerRepository;
    @Override
    public void store(CarDealershipEntity dealer) throws ImportExeption {

        if (dealerRepository.getCarDealershipEntityByName(dealer.getName()) != null) {
            try {
                throw new DealerNameAlreadyExistException("Dealer with this name already exist: name - " + dealer.getName());
            } catch (DealerNameAlreadyExistException e) {
                throw new ImportExeption(e.getMessage());
            }
        } else if (dealerRepository.findById(dealer.getId()) != null) {
            try {
                throw new DealerIdAlreadyExistException("Dealer with this id already exist: id = " + dealer.getId());
            } catch (DealerIdAlreadyExistException e) {
                throw new ImportExeption(e.getMessage());
            }
        } else {
            dealerRepository.save(dealer);
        }
    }
}