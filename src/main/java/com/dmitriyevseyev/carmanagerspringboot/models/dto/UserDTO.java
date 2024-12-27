package com.dmitriyevseyev.carmanagerspringboot.models.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class UserDTO implements Serializable {
    @Positive(message = "Id should be greater than 0.")
    private Integer id;

    @NotEmpty(message = "Name should be empty.")
    @Size(min = 1, max = 30, message = "UserName should be between 2 and 30 characters.")
    private String userName;

    @NotEmpty(message = "Password should be empty.")
    @Size (min = 1, max = 30, message = "Password should be between 2 and 30 characters.")
    private String password;
}
