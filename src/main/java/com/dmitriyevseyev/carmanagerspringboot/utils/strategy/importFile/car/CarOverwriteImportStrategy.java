package com.dmitriyevseyev.carmanagerspringboot.utils.strategy.importFile.car;

import com.dmitriyevseyev.carmanagerspringboot.models.dto.CarDTO;
import com.dmitriyevseyev.carmanagerspringboot.models.entity.CarEntity;
import com.dmitriyevseyev.carmanagerspringboot.repositories.CarRepository;
import com.dmitriyevseyev.carmanagerspringboot.repositories.DealerRepository;
import com.dmitriyevseyev.carmanagerspringboot.utils.ConverterDTO;
import com.dmitriyevseyev.carmanagerspringboot.utils.strategy.importFile.exeption.ImportExeption;
import com.dmitriyevseyev.carmanagerspringboot.utils.strategy.importFile.ImportStrategy;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CarOverwriteImportStrategy implements ImportStrategy<CarDTO> {
    private CarRepository carRepository;
    private DealerRepository dealerRepository;
    private ConverterDTO converterDTO;
    @Override
    public void store(CarDTO carDTO) throws ImportExeption {
        CarEntity carEntity = converterDTO.convertCarDTOToCarEntity(carDTO);
        carEntity.setDealer(dealerRepository.getCarDealershipEntityById(carDTO.getDealerId()));


        System.out.println("CarOverwriteImportStrategy 111 - " + carEntity);

        carRepository.save(carEntity);
    }
}
