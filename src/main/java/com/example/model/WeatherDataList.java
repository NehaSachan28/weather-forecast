package com.example.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class WeatherDataList {

    Main main;
    List<Weather> weather = new ArrayList<Weather>();
    private String dt_txt;

}