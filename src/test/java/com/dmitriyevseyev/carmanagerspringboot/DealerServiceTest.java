package com.dmitriyevseyev.carmanagerspringboot;

import com.dmitriyevseyev.carmanagerspringboot.models.CarDealership;
import com.dmitriyevseyev.carmanagerspringboot.models.entity.CarDealershipEntity;
import com.dmitriyevseyev.carmanagerspringboot.repositories.DealerRepository;
import com.dmitriyevseyev.carmanagerspringboot.services.DealerService;
import com.dmitriyevseyev.carmanagerspringboot.utils.ConverterDTO;
import com.dmitriyevseyev.carmanagerspringboot.utils.ConverterEntity;
import com.dmitriyevseyev.carmanagerspringboot.utils.exeptions.NotFoundException;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.awaitility.Awaitility.given;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class DealerServiceTest {
    @Mock
    DealerRepository dealerRepository;

    @Mock
    ConverterEntity converterEntity;
    @InjectMocks
    DealerService dealerService;
    CarDealershipEntity dealershipEntity;
    List<CarDealershipEntity> dealersList;

    @BeforeEach
    private void init() {
        dealershipEntity = CarDealershipEntity.builder().
                id(1).
                name("AAA").
                address("BBB").build();
        dealersList = new ArrayList<>();
    }

    @Test
    public void getdealerEntity_ReturnsValidDealerEntity() {
        when(dealerRepository.findById(dealershipEntity.getId())).
                thenReturn(Optional.of(dealershipEntity));
        CarDealershipEntity dealershipEntityReturn = dealerService.getDealer2(dealershipEntity.getId());
        System.out.println(dealershipEntityReturn);
        Assertions.assertThat(dealershipEntityReturn).isNotNull();
        Assertions.assertThat(dealershipEntity.getId()).isEqualTo(dealershipEntityReturn.getId());
    }

    @Test
    void getdealerEntity_ReturnsNotfounExeption() {
        when(dealerRepository.findById(dealershipEntity.getId())).
                thenReturn(Optional.empty());
        NotFoundException e = assertThrows(NotFoundException.class,
                () -> dealerService.getDealer(dealershipEntity.getId()));
        System.out.println(e.getMessage());
        Assertions.assertThat(e).isInstanceOf(NotFoundException.class);
        Assertions.assertThat(e.getMessage()).isNotNull();
    }


    @Test
    public void getdealerEntityList_ReturnsValidDealerEntity() {
        when(dealerRepository.findAll()).thenReturn(dealersList);
        List<CarDealership> list = dealerService.getDealersList();

        Assertions.assertThat(list).isNotNull();
    }


    @Test
    public void getdealerQQQQQQQEntity_ReturnsValidDealerEntity() {
        when(dealerRepository.findById(dealershipEntity.getId())).
                thenReturn(Optional.of(dealershipEntity));
        CarDealership dealershipReturn = dealerService.getDealer(dealershipEntity.getId());
        System.out.println(dealershipReturn);

        // Assertions.assertThat(dealershipReturn).isInstanceOf(CarDealership.class);
        Assertions.assertThat(dealershipReturn).isNotNull();
        //     Assertions.assertThat(dealershipEntity.getId()).isEqualTo(dealershipEntityReturn.getId());
    }

}

