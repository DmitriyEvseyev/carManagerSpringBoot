package com.dmitriyevseyev.carmanagerspringboot.models.dto;

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
    private Integer id;
    private String name;
    private String address;
}