package com.dmitriyevseyev.carmanagerspringboot.repositories;

import com.dmitriyevseyev.carmanagerspringboot.models.entity.CarDealershipEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DealerRepository extends JpaRepository<CarDealershipEntity, Integer> {
    List<CarDealershipEntity> findByNameStartingWith(String dealerName);

    List<CarDealershipEntity> findByAddressStartingWith(String dealerAddress);

    List<CarDealershipEntity> findByOrderByNameAsc();

    List<CarDealershipEntity> findByOrderByNameDesc();

    List<CarDealershipEntity> findByOrderByAddressAsc();

    List<CarDealershipEntity> findByOrderByAddressDesc();

    CarDealershipEntity getCarDealershipEntityByName(String dealerName);

    CarDealershipEntity getCarDealershipEntityById(Integer id);
    // Integer deleteById(Integer id);

    //  long deleteById(String id);
    int deleteCarDealershipEntityById(Integer id);
}
