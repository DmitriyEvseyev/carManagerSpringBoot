package com.dmitriyevseyev.carmanagerspringboot.models.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class CarDealershipDTO implements Serializable {

    @Positive(message = "Id should be greater than 0.")
    private Integer id;

    @NotEmpty(message = "Name should be empty.")
    @Size (min = 2, max = 30, message = "Name should be between 2 and 30 characters.")
    private String name;

    @NotEmpty(message = "Address should be empty.")
    @Size (min = 2, max = 30, message = "Address should be between 2 and 30 characters.")
    private String address;
}