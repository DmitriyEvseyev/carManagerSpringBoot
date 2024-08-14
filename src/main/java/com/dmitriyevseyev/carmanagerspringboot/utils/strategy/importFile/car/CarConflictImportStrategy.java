package com.dmitriyevseyev.carmanagerspringboot.utils.strategy.importFile.car;


import com.dmitriyevseyev.carmanagerspringboot.models.dto.CarDTO;
import com.dmitriyevseyev.carmanagerspringboot.utils.strategy.importFile.ImportExeption;
import com.dmitriyevseyev.carmanagerspringboot.utils.strategy.importFile.ImportStrategy;

public class CarConflictImportStrategy implements ImportStrategy<CarDTO> {
    @Override
    public void store(CarDTO carDTO) throws ImportExeption {
  //      ConverterDTO converterDTO = ConverterDTO.getInstance();
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
//                try {
//                    throw new CarIdAlreadyExistException("Car with this id already exist: id = " + car.getId());
//                } catch (CarIdAlreadyExistException e) {
//                    throw new ImportExeption(e.getMessage());
//                }
//            } else {
//                try {
//                    carController.addCar(car);
//                } catch (AddCarExeption ex) {
//                    throw new ImportExeption(ex.getMessage());
//                }
//            }
//        } catch (NotFoundException | DAOFactoryActionException e) {
//            throw new ImportExeption(e.getMessage());
//        }
    }
}