package com.dmitriyevseyev.carmanagerspringboot.utils.strategy.importFile.car;


import com.dmitriyevseyev.carmanagerspringboot.models.dto.CarDTO;
import com.dmitriyevseyev.carmanagerspringboot.utils.strategy.importFile.ImportExeption;
import com.dmitriyevseyev.carmanagerspringboot.utils.strategy.importFile.ImportStrategy;

public class CarOverwriteImportStrategy implements ImportStrategy<CarDTO> {
    @Override
    public void store(CarDTO carDTO) throws ImportExeption {
//        ConverterDTO converterDTO = ConverterDTO.getInstance();
//        Car car = converterDTO.converterCarDTOToCar(carDTO);
//        CarDealership dealer = null;
//        Car oldCar = null;
//        try {
//            DealerController dealerController = DealerController.getInstance();
//            CarController carController = CarController.getInstance();
//            dealer = dealerController.getDealer(carDTO.getIdDealer());
//            car.setDealer(dealer);
//            oldCar = carController.getCar(car.getId());
//            if (oldCar != null) {
//                carController.updateCar(car);
//            } else {
//                carController.addCar(car);
//            }
//        } catch (AddCarExeption | UpdateCarException | NotFoundException | DAOFactoryActionException e) {
//            throw new ImportExeption(e.getMessage());
//        }
    }
}
