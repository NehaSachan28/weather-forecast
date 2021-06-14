package com.example.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class WeatherForecast {
    List<WeatherDataList> list = new ArrayList();
}
