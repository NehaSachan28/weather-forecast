package com.example.service;

import com.example.config.WeatherForecastConfig;
import com.example.exception.DataNotFoundException;
import com.example.model.WeatherForecast;
import com.example.model.WeatherForecastDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.junit.runner.RunWith;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class WeatherForecastServiceTest {

    @InjectMocks
    private WeatherForecastService service;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private WeatherForecastConfig config;

    private String mockWeatherDate = "{\n" +
            "    \"list\": [\n" +
            "        {\n" +
            "            \"dt\": 1487246400,\n" +
            "            \"main\": {\n" +
            "                \"temp\": 286.67,\n" +
            "                \"temp_min\": 281.556,\n" +
            "                \"temp_max\": 286.67,\n" +
            "                \"temp_kf\": 5.11\n" +
            "            },\n" +
            "            \"weather\": [\n" +
            "                {\n" +
            "                    \"id\": 800,\n" +
            "                    \"main\": \"Clear\",\n" +
            "                    \"description\": \"clear sky\",\n" +
            "                    \"icon\": \"01d\"\n" +
            "                }\n" +
            "            ],\n" +
            "            \"dt_txt\": \"2017-02-16 12:00:00\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"dt\": 1487257200,\n" +
            "            \"main\": {\n" +
            "                \"temp\": 285.66,\n" +
            "                \"temp_min\": 281.821,\n" +
            "                \"temp_max\": 285.66,\n" +
            "                \"pressure\": 970.91,\n" +
            "                \"sea_level\": 1044.32,\n" +
            "                \"grnd_level\": 970.91,\n" +
            "                \"humidity\": 70,\n" +
            "                \"temp_kf\": 3.84\n" +
            "            },\n" +
            "            \"weather\": [\n" +
            "                {\n" +
            "                    \"id\": 800,\n" +
            "                    \"main\": \"Clear\",\n" +
            "                    \"description\": \"clear sky\",\n" +
            "                    \"icon\": \"01d\"\n" +
            "                }\n" +
            "            ],\n" +
            "            \"dt_txt\": \"2017-02-16 15:00:00\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"dt\": 1487300400,\n" +
            "            \"main\": {\n" +
            "                \"temp\": 275.568,\n" +
            "                \"temp_min\": 275.568,\n" +
            "                \"temp_max\": 275.568,\n" +
            "                \"pressure\": 966.6,\n" +
            "                \"sea_level\": 1041.39,\n" +
            "                \"grnd_level\": 966.6,\n" +
            "                \"humidity\": 89,\n" +
            "                \"temp_kf\": 0\n" +
            "            },\n" +
            "            \"weather\": [\n" +
            "                {\n" +
            "                    \"id\": 500,\n" +
            "                    \"main\": \"Rain\",\n" +
            "                    \"description\": \"light rain\",\n" +
            "                    \"icon\": \"10n\"\n" +
            "                }\n" +
            "            ],\n" +
            "            \"dt_txt\": \"2017-02-17 03:00:00\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"dt\": 1487311200,\n" +
            "            \"main\": {\n" +
            "                \"temp\": 276.478,\n" +
            "                \"temp_min\": 276.478,\n" +
            "                \"temp_max\": 276.478,\n" +
            "                \"temp_kf\": 0\n" +
            "            },\n" +
            "            \"weather\": [\n" +
            "                {\n" +
            "                    \"id\": 501,\n" +
            "                    \"main\": \"Rain\",\n" +
            "                    \"description\": \"moderate rain\",\n" +
            "                    \"icon\": \"10n\"\n" +
            "                }\n" +
            "            ],\n" +
            "            \"dt_txt\": \"2017-02-17 06:00:00\"\n" +
            "        },\n" +
            "\t\t{\n" +
            "            \"dt\": 1487386800,\n" +
            "            \"main\": {\n" +
            "                \"temp\": 274.965,\n" +
            "                \"temp_min\": 274.965,\n" +
            "                \"temp_max\": 274.965,\n" +
            "                \"temp_kf\": 0\n" +
            "            },\n" +
            "            \"weather\": [\n" +
            "                {\n" +
            "                    \"id\": 500,\n" +
            "                    \"main\": \"Rain\",\n" +
            "                    \"description\": \"light rain\",\n" +
            "                    \"icon\": \"10n\"\n" +
            "                }\n" +
            "            ],\n" +
            "            \"dt_txt\": \"2017-02-18 03:00:00\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"dt\": 1487397600,\n" +
            "            \"main\": {\n" +
            "                \"temp\": 274.562,\n" +
            "                \"temp_min\": 274.562,\n" +
            "                \"temp_max\": 274.562,\n" +
            "                \"temp_kf\": 0\n" +
            "            },\n" +
            "            \"weather\": [\n" +
            "                {\n" +
            "                    \"id\": 500,\n" +
            "                    \"main\": \"Rain\",\n" +
            "                    \"description\": \"light rain\",\n" +
            "                    \"icon\": \"10n\"\n" +
            "                }\n" +
            "            ],\n" +
            "            \"dt_txt\": \"2017-02-18 06:00:00\"\n" +
            "        }\n" +
            "    ]\n" +
            "}";

    @Before
    public void init(){
        Mockito.when(config.getWeatherForecastApi()).thenReturn("https://samples.openweathermap.org/data/2.5/forecast?q={0}&appid=d2929e9483efc82c82c32ee7e02d563e");
    }

    @Test
    public void getWeatherForecastTest() throws DataNotFoundException, JsonProcessingException {
        Mockito.when(restTemplate.getForObject(Mockito.anyString(), Mockito.any())).thenReturn(getWeatherForecast());
        List<WeatherForecastDTO> weatherForecastDTOList =  service.getWeatherForecast("London");
        Assert.assertTrue(weatherForecastDTOList.size()==3);
        Assert.assertEquals(weatherForecastDTOList.get(0).getMaxTemp(),13.520013809204102 ,0);
        Assert.assertEquals(weatherForecastDTOList.get(0).getMinTemp(), 8.406000137329102, 0);
        Assert.assertEquals(weatherForecastDTOList.get(0).getDate(), "2017-02-16");

    }

    @Test(expected = DataNotFoundException.class)
    public void getNullWeatherForecastTest() throws DataNotFoundException {
        Mockito.when(restTemplate.getForObject(Mockito.anyString(), Mockito.any())).thenReturn(new WeatherForecast());
        service.getWeatherForecast("London");
    }

    private WeatherForecast getWeatherForecast() throws JsonProcessingException {
        WeatherForecast weatherForecast;
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        weatherForecast = objectMapper.readValue(mockWeatherDate, WeatherForecast.class);
        return weatherForecast;
    }


}
