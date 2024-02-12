package com.management.user.dto;

import lombok.Data;

/**
 * @author Ashwani Kumar
 * Created on 11/02/24.
 */
@Data
public class ErrorPayload {

    private int statusCode;
    private String message;

}