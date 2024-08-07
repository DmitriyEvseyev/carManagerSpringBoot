package com.dmitriyevseyev.carmanagerspringboot.repositories;

import com.dmitriyevseyev.carmanagerspringboot.models.entity.CarDealershipEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DealerRepository extends JpaRepository<CarDealershipEntity, Integer> {
    List<CarDealershipEntity> findCarDealershipEntitiesByName(String dealerName);

    List<CarDealershipEntity> findCarDealershipEntitiesByAddress(String dealerAddress);
}
