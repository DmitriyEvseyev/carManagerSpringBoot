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
@EqualsAndHashCode
@Builder
public class Car implements Serializable {
    private Integer id;
    private Integer dealerId;
    private String name;
    private Date date;
    private String color;
    private boolean isAfterCrash;
}


