package com.example.controller;

import com.example.exception.DataNotFoundException;
import com.example.exception.ExceptionMessageDetail;
import com.example.model.WeatherForecastDTO;
import com.example.service.WeatherForecastService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller to fetch weather data for given city
 */

@Slf4j
@RestController
@RequestMapping("weather")
public class WeatherForecastController {

    @Autowired
    private WeatherForecastService weatherForecastService;

    @GetMapping(value = "cityForecast")
    public ResponseEntity<List<WeatherForecastDTO>> getWeatherForecast(@RequestParam(name = "city", required = true) String city) throws DataNotFoundException {
        log.debug("Request received to fetch weather data for city {}", city);
        return new ResponseEntity<>(weatherForecastService.getWeatherForecast(city), HttpStatus.OK);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionMessageDetail> exceptionHandler(Exception exception){
        return new ResponseEntity<ExceptionMessageDetail>(new ExceptionMessageDetail(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ExceptionMessageDetail> handleMissingParams(MissingServletRequestParameterException exception) {
        return new ResponseEntity<ExceptionMessageDetail>(new ExceptionMessageDetail(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
