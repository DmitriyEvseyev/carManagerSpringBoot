package com.dmitriyevseyev.carmanagerspringboot.models.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class CarDTO implements Serializable {

    @Positive(message = "Id should be greater than 0.")
    private Integer id;

    @Positive(message = "Id should be greater than 0.")
    private Integer dealerId;

    @NotEmpty(message = "Name should be empty.")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters.")
    private String name;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @NotNull(message = "Date should be empty.")
    private Date date;

    @NotEmpty(message = "Name should be empty.")
    @Size(min = 2, max = 30, message = "Name color be between 2 and 30 characters.")
    private String color;

    @NotNull(message = "IsAfterCrash should be empty.")
    private boolean isAfterCrash;
}


