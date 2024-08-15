package com.dmitriyevseyev.carmanagerspringboot.utils.strategy.importFile.car;


import com.dmitriyevseyev.carmanagerspringboot.models.dto.CarDTO;
import com.dmitriyevseyev.carmanagerspringboot.models.entity.CarEntity;
import com.dmitriyevseyev.carmanagerspringboot.repositories.CarRepository;
import com.dmitriyevseyev.carmanagerspringboot.repositories.DealerRepository;
import com.dmitriyevseyev.carmanagerspringboot.utils.ConverterDTO;
import com.dmitriyevseyev.carmanagerspringboot.utils.strategy.importFile.ImportExeption;
import com.dmitriyevseyev.carmanagerspringboot.utils.strategy.importFile.ImportStrategy;
import com.dmitriyevseyev.carmanagerspringboot.utils.strategy.importFile.exeption.CarIdAlreadyExistException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CarConflictImportStrategy implements ImportStrategy<CarDTO> {
    private CarRepository carRepository;
    private DealerRepository dealerRepository;
    private ConverterDTO converterDTO;

    @Override
    public void store(CarDTO carDTO) throws ImportExeption {
        CarEntity carEntity = converterDTO.converterCarDTOToCarEntity(carDTO);
        if (carRepository.getCarEntitiyById(carEntity.getId()) != null) {
            try {
                throw new CarIdAlreadyExistException("Car with this id already exist: id = " + carDTO.getId());
            } catch (CarIdAlreadyExistException e) {
                throw new ImportExeption(e.getMessage());
            }
        } else {
            carEntity.setDealer(dealerRepository.getCarDealershipEntityById(carDTO.getIdDealer()));


            System.out.println("CarConflictImportStrategy 333 - " + carEntity);

            carRepository.save(carEntity);
        }
    }
}