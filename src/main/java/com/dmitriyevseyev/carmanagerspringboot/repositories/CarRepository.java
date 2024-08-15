package com.dmitriyevseyev.carmanagerspringboot.repositories;

import com.dmitriyevseyev.carmanagerspringboot.models.entity.CarDealershipEntity;
import com.dmitriyevseyev.carmanagerspringboot.models.entity.CarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<CarEntity, Integer> {
    List<CarEntity> getCarEntitiesByDealer(CarDealershipEntity dealerEntity);

    List<CarEntity> findByDealerAndNameStartingWith(CarDealershipEntity dealerEntity, String carName);

    List<CarEntity> findByDealerAndColorStartingWith(CarDealershipEntity dealerEntity, String carColor);

    List<CarEntity> getCarEntitiesByDealerAndIsAfterCrash(CarDealershipEntity dealerEntity, boolean isAfterCrash);

    List<CarEntity> findByDealerAndDateBetween(CarDealershipEntity dealerEntity, Date startDate, Date endDate);

    List<CarEntity> findByDealerOrderByNameAsc(CarDealershipEntity dealer);

    List<CarEntity> findByDealerOrderByNameDesc(CarDealershipEntity dealer);

    List<CarEntity> findByDealerOrderByDateAsc(CarDealershipEntity dealer);

    List<CarEntity> findByDealerOrderByDateDesc(CarDealershipEntity dealer);

    List<CarEntity> findByDealerOrderByColorAsc(CarDealershipEntity dealer);

    List<CarEntity> findByDealerOrderByColorDesc(CarDealershipEntity dealer);

    List<CarEntity> findByDealerOrderByIsAfterCrashAsc(CarDealershipEntity dealer);

    List<CarEntity> findByDealerOrderByIsAfterCrashDesc(CarDealershipEntity dealer);

    CarEntity getCarEntitiyById(Integer idCarEntity);


}