package com.dmitriyevseyev.carmanagerspringboot.models;

import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Car implements Serializable {
    private Integer id;
    private Integer dealerId;
    private String name;
    private Date date;
    private String color;
    private boolean isAfterCrash;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return isAfterCrash == car.isAfterCrash &&
                Objects.equals(id, car.id) &&
                Objects.equals(dealerId, car.dealerId) &&
                Objects.equals(name, car.name) &&
                Objects.equals(date, car.date) &&
                Objects.equals(color, car.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dealerId, name, date, color, isAfterCrash);
    }
}


