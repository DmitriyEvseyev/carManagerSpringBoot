package com.dmitriyevseyev.carmanagerspringboot;

import com.dmitriyevseyev.carmanagerspringboot.models.CarDealership;
import com.dmitriyevseyev.carmanagerspringboot.models.entity.CarDealershipEntity;
import com.dmitriyevseyev.carmanagerspringboot.repositories.DealerRepository;
import com.dmitriyevseyev.carmanagerspringboot.services.DealerService;
import com.dmitriyevseyev.carmanagerspringboot.utils.ConverterEntity;
import com.dmitriyevseyev.carmanagerspringboot.utils.exeptions.CreatedExeption;
import com.dmitriyevseyev.carmanagerspringboot.utils.exeptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import org.assertj.core.api.Assertions;

import java.util.ArrayList;
import java.util.Arrays;
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
    CarDealershipEntity dealerEntity;
    CarDealership dealer;

    List<CarDealershipEntity> dealerEntitiesList;
    List<CarDealership> dealersList;

    @BeforeEach
    private void init() {
        dealerEntity = CarDealershipEntity.builder().
                id(1).
                name("AAA").
                address("BBB").build();
        dealer = CarDealership.builder().
                id(1).
                name("AAA").
                address("BBB").build();

        dealerEntitiesList = new ArrayList<>();
        dealersList = new ArrayList<>();

        dealersList.add(dealer);
        dealerEntitiesList.add(dealerEntity);
    }

    @Test
    public void getdealer_ReturnsValidDealer() {
        when(dealerRepository.findById(dealerEntity.getId())).
                thenReturn(Optional.of(dealerEntity));
        when(converterEntity.convertDealerEntityToDealer(dealerEntity)).thenReturn(dealer);
        CarDealership dealerReturn = dealerService.getDealer(dealerEntity.getId());
        System.out.println(dealerReturn);

        Assertions.assertThat(dealerReturn).isInstanceOf(CarDealership.class);
        Assertions.assertThat(dealerReturn).isNotNull();
        Assertions.assertThat(dealer.getId()).isEqualTo(dealerReturn.getId());
        Assertions.assertThat(dealer.getName()).isEqualTo(dealerReturn.getName());
        Assertions.assertThat(dealer.getAddress()).isEqualTo(dealerReturn.getAddress());
    }

    @Test
    void getDealerEntity_ReturnsNotfounExeption() {
        when(dealerRepository.findById(dealerEntity.getId())).
                thenReturn(Optional.empty());
        NotFoundException e = assertThrows(NotFoundException.class,
                () -> dealerService.getDealer(dealerEntity.getId()));
        System.out.println(e.getMessage());
        Assertions.assertThat(e).isInstanceOf(NotFoundException.class);
        Assertions.assertThat(e.getMessage()).isNotNull();
    }

    @Test
    public void getdealersList_ReturnsValidDealersList() {
        when(dealerRepository.findAll()).thenReturn(dealerEntitiesList);
        when(converterEntity.convertDealerEntitiesListToDealersList(dealerEntitiesList)).
                thenReturn(dealersList);
        List<CarDealership> dealersListReturn = dealerService.getDealersList();
        System.out.println(dealersListReturn);

        Assertions.assertThat(dealersListReturn).isNotNull();
        Assertions.assertThat(dealersListReturn.size()).isEqualTo(dealersList.size());
        Assertions.assertThat(dealersListReturn).isEqualTo(dealersList);
    }

    @Test
    public void addDealer_ReturnsSaveValidDealer() {
        when(converterEntity.convertDealerToDealerEntity(dealer)).thenReturn(dealerEntity);
        when(dealerRepository.save(dealerEntity)).thenReturn(dealerEntity);
        dealerService.addDealer(dealer);
        verify(dealerRepository, times(1)).save(dealerEntity);
    }

    @Test
    public void updateDealer_ReturnsUpdateDealer() {
        when(dealerRepository.existsById(dealer.getId())).thenReturn(true);
        Boolean exist = dealerRepository.existsById(dealer.getId());
        Assertions.assertThat(true).isEqualTo(exist);

        when(dealerRepository.save(dealerEntity)).thenReturn(dealerEntity);
        CarDealershipEntity returndealer = dealerRepository.save(dealerEntity);
        when(converterEntity.convertDealerToDealerEntity(dealer)).thenReturn(dealerEntity);
        dealerService.updateDealer(dealer);

        Assertions.assertThat(returndealer).isNotNull();
        verify(dealerRepository, times(2)).save(dealerEntity);
    }

    @Test
    public void updateDealer_ReturnsNotFoundExeption() {
        when(dealerRepository.existsById(dealerEntity.getId())).thenReturn(false);
        NotFoundException e = assertThrows(NotFoundException.class,
                () -> dealerService.updateDealer(dealer));

        System.out.println(e.getMessage());
        Assertions.assertThat(e).isInstanceOf(NotFoundException.class);
        Assertions.assertThat(e.getMessage()).isNotNull();
    }

    @Test
    public void delOnlyOneDealer_ReturnsDealeteDealer() {
        when(dealerRepository.deleteCarDealershipEntityById(dealer.getId())).
                thenReturn(1);
        dealerService.delOnlyOneDealer(dealer.getId());

        verify(dealerRepository).deleteCarDealershipEntityById(dealer.getId());
        Assertions.assertThat(dealerRepository.deleteCarDealershipEntityById(dealer.getId()))
                .isEqualTo(1);
    }

    @Test
    public void delOnlyOneDealer_ReturnsNotFoundExeption() {
        when(dealerRepository.deleteCarDealershipEntityById(dealer.getId())).
                thenReturn(0);
        NotFoundException e = assertThrows(NotFoundException.class,
                () -> dealerService.delOnlyOneDealer(dealer.getId()));

        System.out.println(e.getMessage());
        Assertions.assertThat(e).isInstanceOf(NotFoundException.class);
        Assertions.assertThat(e.getMessage()).isNotNull();
    }
}

//    when(dealerRepository.findById(dealerEntity.getId())).
//        thenReturn(Optional.empty());
//        NotFoundException e = assertThrows(NotFoundException.class,
//        () -> dealerService.getDealer(dealerEntity.getId()));
//        System.out.println(e.getMessage());
//        Assertions.assertThat(e).isInstanceOf(NotFoundException.class);
//        Assertions.assertThat(e.getMessage()).isNotNull();
