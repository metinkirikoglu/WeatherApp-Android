package com.metinhocam.weatherapp.service;

import com.metinhocam.weatherapp.model.Weather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherAPI {

    @GET("weather")
    Call<Weather> getWeather(@Query("q") String city, @Query("appid") String apiKey);

}
