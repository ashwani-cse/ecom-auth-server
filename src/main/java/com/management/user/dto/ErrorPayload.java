package com.management.user.dto;

import lombok.Data;

@Data
public class ErrorPayload {

    private int statusCode;
    private String message;

}