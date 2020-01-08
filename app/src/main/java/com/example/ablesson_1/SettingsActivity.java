package com.example.ablesson_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class SettingsActivity extends AppCompatActivity implements Constants {

    private CheckBox checkBoxSun;
    private CheckBox checkBoxPressure;
    private CheckBox checkBoxWind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        //вытаскиваем значения чекбоксов
        final Parcel parcel = (Parcel) getIntent().getExtras().getSerializable(PARCEL);
        checkBoxSun = findViewById(R.id.checkBox_sun);
        checkBoxSun.setChecked(parcel.ParSun);

        checkBoxPressure = findViewById(R.id.checkBox_pressure);
        checkBoxPressure.setChecked(parcel.ParPressure);

        checkBoxWind = findViewById(R.id.checkBox_wind);
        checkBoxWind.setChecked(parcel.ParWind);

        //Listener на кнопку Сохранить
        Button btnSave = findViewById(R.id.button_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent result = new Intent();
                    //передаем значения чекбоксов
                    if(checkBoxSun.isChecked()) {
                        Log.d("AppState", "onClick: true ");
                        result.putExtra(SUN, true);
                    } else {
                        Log.d("AppState", "onClick: false ");
                        result.putExtra(SUN, false);
                    }
                    if(checkBoxPressure.isChecked()) {
                        result.putExtra(PRESSURE, true);
                    } else {
                        result.putExtra(PRESSURE, false);
                    }
                    if(checkBoxWind.isChecked()) {
                        result.putExtra(WIND, true);
                    } else {
                        result.putExtra(WIND, false);
                    }
                setResult(RESULT_OK, result);
                finish();
            }
        });
    }
}
