package com.dmitriyevseyev.carmanagerspringboot.controllers;



import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class CarController {
//    private static CarController instance;
//    private CarDAO carDAO;
//
//    public static CarController getInstance() throws DAOFactoryActionException {
//        if (instance == null) {
//            instance = new CarController();
//        }
//        return instance;
//    }
//
//    private CarController() {
//        ManagerDAO managerDAO = HibernateManagerDAO.getInstance();
//        this.carDAO = managerDAO.getCarDAO();
//    }
//
//
//    public void addCar(Car car) throws AddCarExeption {
//        carDAO.createCar(car);
//    }
//
//    public Car getCar(Integer id) throws NotFoundException {
//        return carDAO.getCar(id);
//    }
//
//    public List<Car> getCarList(Integer idDealer) throws GetAllCarExeption {
//        return Collections.unmodifiableList(new ArrayList<>(carDAO.getCarListDealer(idDealer)));
//    }
//
//    public void updateCar(Car car) throws UpdateCarException {
//        carDAO.update(car);
//    }
//
//    public void removeCar(Integer id) throws NotFoundException, DeleteCarExeption {
//        carDAO.delete(id);
//    }
//
//    public List<Car> getSortedByCriteria(Integer idDealer, String column, String criteria) throws GetAllCarExeption {
//        return Collections.unmodifiableList(new ArrayList<>(carDAO.getSortedByCriteria(idDealer, column, criteria)));
//    }
//
//    public List<Car> getFilteredByPattern(Integer idDealer, String column, String pattern, String criteria) throws GetAllCarExeption {
//        return Collections.unmodifiableList(new ArrayList<>(carDAO.getFilteredByPattern(idDealer, column, pattern, criteria)));
//    }
//
//    public List<Car> getFilteredByDatePattern(Integer idDealer, String columnDate, Date startDatePattern, Date endDatePattern, String criteria) throws GetAllCarExeption {
//        return Collections.unmodifiableList(new ArrayList<>(carDAO.getFilteredByDatePattern(idDealer, columnDate, startDatePattern, endDatePattern, criteria)));
//    }
//
//    public List<Car> getFilteredByCrashPattern(Integer idDealer, String column, String pattern, String criteria) throws GetAllCarExeption {
//        return Collections.unmodifiableList(new ArrayList<>(carDAO.getFilteredByCrashPattern(idDealer, column, pattern, criteria)));
//    }
//
//    public List<Car> getCars(List<Integer> ids) throws NotFoundException {
//        List<Car> carList = new ArrayList<>();
//        for (Integer id : ids) {
//            carList.add(getCar(id));
//        }
//        return Collections.unmodifiableList(carList);
//    }
//
//    public List<Car> getCarsByDealersIds(List<Integer> ids) throws GetAllCarExeption {
//        List<Car> carList = new ArrayList<>();
//        for (Integer idDealer : ids) {
//            carList.addAll(carList.size(), getCarList(idDealer));
//        }
//        return Collections.unmodifiableList(carList);
//    }
}


