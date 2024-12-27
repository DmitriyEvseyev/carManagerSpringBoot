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
public class User implements Serializable {
    private Integer id;
    private String userName;
    private String password;
}


