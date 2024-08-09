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

    // List<CarEntity> findByDealerAndAfterCrashStartingWith(CarDealershipEntity dealerEntity, boolean carAfterCrash);

    List<CarEntity> findByDealerAndDateBetween(CarDealershipEntity dealerEntity, Date date1, Date date2);
}