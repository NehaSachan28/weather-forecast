package com.example.service;

import com.example.config.WeatherForecastConfig;
import com.example.exception.DataNotFoundException;
import com.example.model.WeatherDataList;
import com.example.model.WeatherForecast;
import com.example.model.WeatherForecastDTO;
import com.example.utils.WeatherForecastUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Service class to fetch weather data.
 * It makes call to openweathermap.org to get the weather data and format the data into a user friendly format
 */

@Slf4j
@Service
public class WeatherForecastService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WeatherForecastConfig config;

    public List<WeatherForecastDTO> getWeatherForecast(String cityName) throws DataNotFoundException {

        String url = MessageFormat.format(config.getWeatherForecastApi(), cityName);
        WeatherForecast weatherForecast = restTemplate.getForObject(url,WeatherForecast.class );
        log.debug("Weather data received from openweathermap.org api");
        return convertToDTO(weatherForecast);
    }

    private List<WeatherForecastDTO> convertToDTO(WeatherForecast weatherForecast) throws DataNotFoundException {
        log.debug("Converting weather forcast data to user friendly format");
        List<WeatherForecastDTO> weatherForecasts = new LinkedList<>();
        if(Objects.isNull(weatherForecast.getList()) || weatherForecast.getList().size()==0){
            throw new DataNotFoundException("No data found for given city");
        }
        for(WeatherDataList weatherDataList : weatherForecast.getList()){
            String date = weatherDataList.getDt_txt().substring(0, weatherDataList.getDt_txt().indexOf(' '));
            Optional<WeatherForecastDTO> weatherForecastDTOOptional = weatherForecasts.stream().filter(forecast-> date.equals(forecast.getDate())).findAny();
            WeatherForecastDTO weatherForecastDTO;
            if(weatherForecastDTOOptional.isPresent()){
                weatherForecastDTO = weatherForecastDTOOptional.get();
                float max_temp = WeatherForecastUtils.convertToCelcius(weatherDataList.getMain().getTemp_max());
                if(max_temp>weatherForecastDTO.getMaxTemp()){
                    weatherForecastDTO.setMaxTemp(max_temp);
                }
                float min_temp = WeatherForecastUtils.convertToCelcius(weatherDataList.getMain().getTemp_min());
                if(min_temp<weatherForecastDTO.getMinTemp()){
                    weatherForecastDTO.setMinTemp(min_temp);
                }
            }else {
                weatherForecastDTO = new WeatherForecastDTO();

                weatherForecastDTO.setDate(date);
                weatherForecastDTO.setMaxTemp(WeatherForecastUtils.convertToCelcius(weatherDataList.getMain().getTemp_max()));
                weatherForecastDTO.setMinTemp(WeatherForecastUtils.convertToCelcius(weatherDataList.getMain().getTemp_min()));
                weatherForecasts.add(weatherForecastDTO);
            }
            if(weatherDataList.getWeather().get(0).getDescription().contains("rain"))
                weatherForecastDTO.setWarningMessage("Carry Umbrella");
            if(weatherForecastDTO.getMaxTemp()>=40)
                weatherForecastDTO.setWarningMessage("Use sunscreen lotion");
        }
        return weatherForecasts;
    }

}
