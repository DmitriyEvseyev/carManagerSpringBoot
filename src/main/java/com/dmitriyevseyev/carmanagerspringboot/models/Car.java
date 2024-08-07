package com.dmitriyevseyev.carmanagerspringboot.models;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Car implements Serializable {
    private Integer id;
    private Integer idDealer;
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
                Objects.equals(idDealer, car.idDealer) &&
                Objects.equals(name, car.name) &&
                Objects.equals(date, car.date) &&
                Objects.equals(color, car.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idDealer, name, date, color, isAfterCrash);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Integer id;
        private Integer idDealer;
        private String name;

        @Temporal(TemporalType.DATE)
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private Date date;
        private String color;
        private boolean isAfterCrash;

        Builder() {
        }

        public Builder id(Integer id) {
            this.id = id;
            return this;
        }

        public Builder idDealer(Integer idDealer) {
            this.idDealer = idDealer;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder date(Date date) {
            this.date = date;
            return this;
        }

        public Builder color(String color) {
            this.color = color;
            return this;
        }

        public Builder isAfterCrash(boolean isAfterCrash) {
            this.isAfterCrash = isAfterCrash;
            return this;
        }

        public Car build() {
            return new Car(
                    id,
                    idDealer,
                    name,
                    date,
                    color,
                    isAfterCrash
            );
        }
    }
}


