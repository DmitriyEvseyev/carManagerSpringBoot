package com.dmitriyevseyev.carmanagerspringboot.utils;


import com.dmitriyevseyev.carmanagerspringboot.models.dto.CarDTO;
import com.dmitriyevseyev.carmanagerspringboot.models.dto.CarDealershipDTO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExportDTO {
    private List<CarDealershipDTO> dealers;
    private List<CarDTO> cars;

    public ExportDTO() {
        this.dealers = new ArrayList<>();
        this.cars = new ArrayList<>();
    }

    public void setDealers(List<CarDealershipDTO> dealers) {
        this.dealers = dealers;
    }

    public void setCars(List<CarDTO> cars) {
        this.cars = cars;
    }

    public void addDelers(List<CarDealershipDTO> dealers) {
        this.dealers.addAll(this.dealers.size(), dealers);
    }

    public void addCars(List<CarDTO> cars) {
        this.cars.addAll(this.cars.size(), cars);
    }

    public List<CarDealershipDTO> getDealers() {
        return Collections.unmodifiableList(this.dealers);
    }

    public List<CarDTO> getCars() {
        return Collections.unmodifiableList(this.cars);
    }

    @Override
    public String toString() {
        return "ExportDTO{" +
                "dealers=" + dealers +
                ", cars=" + cars +
                '}';
    }
}