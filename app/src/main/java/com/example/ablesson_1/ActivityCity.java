package com.example.ablesson_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityCity extends AppCompatActivity implements Constants {

    private static final int SETTINGS_CODE = 555;

    public boolean sunrise = true;
    boolean pressure = true;
    public boolean wind = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        Log.d("AppState", "Activity City onCreate is called");

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
    protected void onStop() {
        super.onStop();
        Toast.makeText(getApplicationContext(), "Приложение свернуто", Toast.LENGTH_SHORT).show();
        Log.d("AppState", "Activity City onStop is called");
    }
}
