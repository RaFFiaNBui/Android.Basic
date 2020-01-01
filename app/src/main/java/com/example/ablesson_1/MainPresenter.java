package com.example.ablesson_1;

class MainPresenter {

    private static MainPresenter instance = null;

    private String city;

    private MainPresenter() {
        city = "Москва";
    }

    void changeCity() {
        city = "Санкт-Петербург";
    }

    public String getCity() {
        return city;
    }

    static MainPresenter getInstance() {
        if (instance == null) {
            instance = new MainPresenter();
        }
        return instance;
    }
}
