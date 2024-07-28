package com.dmitriyevseyev.carmanagerspringboot.models.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CarDTO implements Serializable {
    private Integer id;
    private Integer idDealer;
    private String name;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date date;
    private String color;
    private boolean isAfterCrash;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarDTO carDTO = (CarDTO) o;
        return isAfterCrash == carDTO.isAfterCrash &&
                Objects.equals(id, carDTO.id) &&
                Objects.equals(idDealer, carDTO.idDealer) &&
                Objects.equals(name, carDTO.name) &&
                Objects.equals(date, carDTO.date) &&
                Objects.equals(color, carDTO.color);
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

        public CarDTO build() {
            return new CarDTO(
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


