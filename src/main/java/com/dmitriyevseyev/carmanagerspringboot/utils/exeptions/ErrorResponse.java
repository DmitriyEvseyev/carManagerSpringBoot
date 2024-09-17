package com.dmitriyevseyev.carmanagerspringboot.utils.exeptions;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class ErrorResponse {
    private int statusCode;
    private String message;
}
