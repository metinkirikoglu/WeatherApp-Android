package com.metinhocam.weatherapp.model;


import com.google.gson.annotations.SerializedName;

public class Weather {
    @SerializedName("main")
    Main main;

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }




}
