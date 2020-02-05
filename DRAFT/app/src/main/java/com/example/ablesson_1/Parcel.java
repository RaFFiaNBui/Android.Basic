package com.example.ablesson_1;

import java.io.Serializable;

class Parcel implements Serializable {
    boolean ParSun;
    boolean ParPressure;
    boolean ParWind;
    private String cityName;

    String getCityName() {
        return cityName;
    }

    /*void setCityName(String cityName) {
        this.cityName = cityName;
    }*/

    Parcel(String cityName) {
        this.cityName = cityName;
    }
}
