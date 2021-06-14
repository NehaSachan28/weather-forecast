package com.example.model;

import lombok.Data;

/**
 * External DTO class for returning data
 */

@Data
public class WeatherForecastDTO{

    private String date;
    private float minTemp;
    private float maxTemp;
    private String warningMessage;

}
