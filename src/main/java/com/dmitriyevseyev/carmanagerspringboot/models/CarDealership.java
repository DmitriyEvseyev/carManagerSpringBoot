package com.dmitriyevseyev.carmanagerspringboot.models;

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
public class CarDealership implements Serializable {
    private Integer id;
    private String name;
    private String address;
}