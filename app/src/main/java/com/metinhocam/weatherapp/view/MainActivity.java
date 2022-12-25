package com.metinhocam.weatherapp.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.metinhocam.weatherapp.databinding.ActivityMainBinding;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.buttonGetWeatherCondition.setOnClickListener(view1 -> {
            String city = getCity();

            getWeatherCondition(city);
        });

    }

    private void getWeatherCondition(String city) {

        Intent intent = new Intent(MainActivity.this, InfoActivity.class);
        intent.putExtra("city", city);
        startActivity(intent);
    }

    private String getCity() {

        return Objects.requireNonNull(binding.textInputEditTextCity.getText()).toString().trim();

    }
}