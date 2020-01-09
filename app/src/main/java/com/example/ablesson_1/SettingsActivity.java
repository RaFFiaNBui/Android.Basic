package com.example.ablesson_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Switch;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity implements Constants {

    private Switch darkSwitch;
    private CheckBox checkBoxSun;
    private CheckBox checkBoxPressure;
    private CheckBox checkBoxWind;

    SharedPreferences sPrefSettings;
    private boolean darkTheme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        //вытаскиваем значения чекбоксов
        loadSettings();

        final Parcel parcel = (Parcel) getIntent().getExtras().getSerializable(PARCEL);
        checkBoxSun = findViewById(R.id.checkBox_sun);
        checkBoxSun.setChecked(parcel.ParSun);

        checkBoxPressure = findViewById(R.id.checkBox_pressure);
        checkBoxPressure.setChecked(parcel.ParPressure);

        checkBoxWind = findViewById(R.id.checkBox_wind);
        checkBoxWind.setChecked(parcel.ParWind);

        //Listener на кнопку Сохранить
        Button btnSave = findViewById(R.id.button_save);
        btnSave.setOnClickListener(save);
    }

    private final View.OnClickListener save = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            saveSettings();
            Intent result = new Intent();
            //передаем значения чекбоксов
            result.putExtra(SUN, (checkBoxSun.isChecked()));
            result.putExtra(PRESSURE, (checkBoxPressure.isChecked()));
            result.putExtra(WIND, (checkBoxWind.isChecked()));
            setResult(RESULT_OK, result);
            finish();
        }
    };

    void loadSettings() {
        sPrefSettings = getSharedPreferences(MY_SETTINGS, MODE_PRIVATE);
        darkTheme = sPrefSettings.getBoolean(DARK_THEME, false);
        darkSwitch = findViewById(R.id.switch_theme);
        darkSwitch.setChecked(darkTheme);
    }

    void saveSettings() {
        darkTheme = darkSwitch.isChecked();
        Log.d("AppState", Boolean.toString(darkTheme));
        sPrefSettings = getSharedPreferences(MY_SETTINGS, MODE_PRIVATE);
        SharedPreferences.Editor ed = sPrefSettings.edit();
        ed.putBoolean(DARK_THEME, darkTheme);
        ed.apply();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(getApplicationContext(), "Настройки сохранены", Toast.LENGTH_SHORT).show();
        Log.d("SettingsActivity", "Настройки сохранены, ActivitySettings закрыта");
    }
}
