package com.metinhocam.weatherapp.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.metinhocam.weatherapp.R;
import com.metinhocam.weatherapp.databinding.ActivityInfoBinding;
import com.metinhocam.weatherapp.model.Main;
import com.metinhocam.weatherapp.model.Weather;
import com.metinhocam.weatherapp.service.WeatherAPI;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class InfoActivity extends AppCompatActivity {
    private ActivityInfoBinding binding;
    String baseUrl = "https://api.openweathermap.org/data/2.5/";

    String apiKey = "your_api_key";       //enter your personal api_key;
    WeatherAPI weatherAPI;
    Weather weather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInfoBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        Intent intent = getIntent();
        String city = intent.getStringExtra("city");
        getWeather(city);

    }

    private void getWeather(String city) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        weatherAPI = retrofit.create(WeatherAPI.class);
        Call<Weather> weatherCall = weatherAPI.getWeather(city, apiKey);


        try {
            weatherCall.enqueue(new Callback<Weather>() {
                @Override
                public void onResponse(@NonNull Call<Weather> call, @NonNull Response<Weather> response) {
                    StringBuffer temperature = new StringBuffer();
                    StringBuffer max_temperature = new StringBuffer();
                    StringBuffer min_temperature = new StringBuffer();
                    StringBuffer humidity = new StringBuffer();
                    StringBuffer pressure = new StringBuffer();

                    if (response.code() == 404) {
                        Toast.makeText(InfoActivity.this, "Invalid city name!", Toast.LENGTH_SHORT).show();
                        unknownWeather();
                    } else if (!(response.isSuccessful())) {
                        Toast.makeText(InfoActivity.this, response.code(), Toast.LENGTH_SHORT).show();
                        unknownWeather();
                    } else {
                        Objects.requireNonNull(getSupportActionBar()).setTitle(city.toUpperCase());
                        weather = response.body();
                        assert weather != null;
                        Main main = weather.getMain();


                        Integer temperature_degree = (int) (main.getTemp() - 273.15);
                        Integer max_temperature_degree = (int) (main.getTempMax() - 273.15);
                        Integer min_temperature_degree = (int) (main.getTempMin() - 273.15);
                        temperature.append(temperature_degree);
                        temperature.append(getResources().getString(R.string.degree_symbol));

                        max_temperature.append(max_temperature_degree);
                        max_temperature.append(getResources().getString(R.string.degree_symbol));

                        min_temperature.append(min_temperature_degree);
                        min_temperature.append(getResources().getString(R.string.degree_symbol));

                        humidity.append(main.getHumidity());
                        humidity.append(getResources().getString(R.string.humidity_symbol));

                        pressure.append(main.getPressure());
                        pressure.append(getResources().getString(R.string.pressure_symbol));


                        binding.textViewTemperature.setText(temperature);
                        binding.textViewPressure.setText(pressure);
                        binding.textViewHumidity.setText(humidity);
                        binding.textViewMaxTemp.setText(max_temperature);
                        binding.textViewMinTemp.setText(min_temperature);


                    }


                }

                @Override
                public void onFailure(@NonNull Call<Weather> call, @NonNull Throwable t) {
                    Toast.makeText(InfoActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void unknownWeather() {
        binding.textViewMinTemp.setText(getResources().getString(R.string.question_mark));
        binding.textViewPressure.setText(getResources().getString(R.string.question_mark));
        binding.textViewMaxTemp.setText(getResources().getString(R.string.question_mark));
        binding.textViewHumidity.setText(getResources().getString(R.string.question_mark));
        binding.textViewTemperature.setText(getResources().getString(R.string.question_mark));
    }
}