package com.example.exception;

import lombok.Data;

@Data
public class ExceptionMessageDetail {

    private String errorMessage;

    public ExceptionMessageDetail(String errorMessage){
        this.errorMessage=errorMessage;
    }
}
