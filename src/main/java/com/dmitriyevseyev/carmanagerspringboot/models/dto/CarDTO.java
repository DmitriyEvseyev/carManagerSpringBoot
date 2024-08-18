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
@Builder
public class CarDTO implements Serializable {
    private Integer id;
    private Integer dealerId;
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
                Objects.equals(dealerId, carDTO.dealerId) &&
                Objects.equals(name, carDTO.name) &&
                Objects.equals(date, carDTO.date) &&
                Objects.equals(color, carDTO.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dealerId, name, date, color, isAfterCrash);
    }
}


