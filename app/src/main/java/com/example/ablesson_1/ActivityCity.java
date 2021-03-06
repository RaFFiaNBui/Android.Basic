package com.example.ablesson_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ActivityCity extends AppCompatActivity implements Constants {

    private static final int SETTINGS_CODE = 555;

    private boolean sunrise = true;
    private boolean pressure = true;
    private boolean wind = true;

    private SharedPreferences sPrefSettings;
    private boolean darkTheme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        Log.d("ActivityCity", "Activity City onCreate is called");

        //вытаскиваем данные о городе из предыдущей активити
        final TextView textViewCity = findViewById(R.id.city);
        textViewCity.setText(getIntent().getExtras().getString(CITY));

        //Listener на кнопку  О городе
        Button btn_about = findViewById(R.id.about);
        btn_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url;
                switch (textViewCity.getText().toString()) {
                    case ("Москва"):
                        url = "https://ru.wikipedia.org/wiki/%D0%9C%D0%BE%D1%81%D0%BA%D0%B2%D0%B0";
                        break;
                    case "Санкт-Петербург":
                        url = "https://ru.wikipedia.org/wiki/%D0%A1%D0%B0%D0%BD%D0%BA%D1%82-%D0%9F%D0%B5%D1%82%D0%B5%D1%80%D0%B1%D1%83%D1%80%D0%B3";
                        break;
                    case "Сочи":
                        url = "https://ru.wikipedia.org/wiki/%D0%A1%D0%BE%D1%87%D0%B8";
                        break;
                    default:
                        url = "https://yandex.ru/";
                }
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }

    //создаем меню
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    //привязываем активити настроек к меню
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(ActivityCity.this, SettingsActivity.class);
                //текущее состояние checkbox
                Parcel parcel = new Parcel();
                parcel.ParSun = sunrise;
                parcel.ParPressure = pressure;
                parcel.ParWind = wind;
                intent.putExtra(PARCEL, parcel);
                startActivityForResult(intent, SETTINGS_CODE);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //отображение настроек
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != SETTINGS_CODE) {
            super.onActivityResult(requestCode, resultCode, data);
            return;
        }
        if (resultCode == RESULT_OK) {
            final boolean tempSun = data.getBooleanExtra(SUN, true);
            final boolean tempPressure = data.getBooleanExtra(PRESSURE, true);
            final boolean tempWind = data.getBooleanExtra(WIND, true);
            //отрисовка восхода и заката
            TableRow TRSunset = findViewById(R.id.sunset_row);
            TableRow TRSunrise = findViewById(R.id.sunrise_row);
            sunrise = tempSun;
            TRSunset.setVisibility(tempSun ? View.VISIBLE : View.GONE);
            TRSunrise.setVisibility(tempSun ? View.VISIBLE : View.GONE);
            //отрисовка давления
            TableRow TRPressure = findViewById(R.id.pressure_row);
            pressure = tempPressure;
            TRPressure.setVisibility(tempPressure ? View.VISIBLE : View.GONE);
            //отрисовка ветра
            TableRow TRWind = findViewById(R.id.wind_row);
            wind = tempWind;
            TRWind.setVisibility(tempWind ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("ActivityCity", "Activity City method onStart is called");
    }

    @Override
    protected void onResume() {
        loadSettings();
        super.onResume();
        Log.d("ActivityCity", "Activity City method onResume is called");
    }

    @Override
    protected void onStop() {
        saveSettings();
        super.onStop();
        Log.d("ActivityCity", "Activity City onStop is called");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("ActivityCity", "Activity City method onRestart is called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("ActivityCity", "Activity City method onDestroy is called");
    }

    void saveSettings() {
        Log.d("ActivityCity", "Save Settings");
        sPrefSettings = getSharedPreferences(MY_SETTINGS, MODE_PRIVATE);
        SharedPreferences.Editor ed = sPrefSettings.edit();
        ed.putBoolean(DARK_THEME, false);
        ed.apply();
    }

    void loadSettings() {
        sPrefSettings = getSharedPreferences(MY_SETTINGS, Context.MODE_PRIVATE);
        darkTheme = sPrefSettings.getBoolean(DARK_THEME, false);
        chooseTheme();
        Log.d("ActivityCity", "load settings");
    }

    void chooseTheme() {
        LinearLayout first = findViewById(R.id.ac_ll_first);
        first.setBackground(ContextCompat.getDrawable(this, (darkTheme ? R.drawable.sky_night : R.drawable.sky_day)));
        Log.d("ActivityCity", "Тема установлена");
    }
}
