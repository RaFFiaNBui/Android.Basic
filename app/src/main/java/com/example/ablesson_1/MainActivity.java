package com.example.ablesson_1;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements Constants {

    //лисенер на кнопку "Запустить"
    private final View.OnClickListener onClickListenerLoading = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            frameLayoutFirstPage.setVisibility(View.VISIBLE);
            linearLayoutLoading.setVisibility(View.GONE);
        }
    };

    //лисенер на кнопки выбора города
    private final View.OnClickListener CityListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent CityIntent = new Intent(MainActivity.this, ActivityCity.class);
            EditText EnterCity = findViewById(R.id.ET_enter_city);
            switch (v.getId()) {
                case R.id.btn_enter_city:
                    CityIntent.putExtra(CITY, EnterCity.getText().toString());
                    break;
                default:
                    CityIntent.putExtra(CITY, ((Button) v).getText().toString());
                    break;
            }
            startActivity(CityIntent);
        }
    };

    private LinearLayout linearLayoutLoading;
    private FrameLayout frameLayoutFirstPage;

    private static final String TEMPERATURE = "temp key";
    private int temp;
    private TextView tempValue;
    private TextView cityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        frameLayoutFirstPage = findViewById(R.id.FrameLayout_first_page);
        linearLayoutLoading = findViewById(R.id.LinearLayout_loading);
        Button buttonLoading = findViewById(R.id.button_loading);
        buttonLoading.setOnClickListener(onClickListenerLoading);

        Toast.makeText(this, "Приложение запускается", Toast.LENGTH_SHORT).show();
        Log.d("AppState", "method onCreate is called");

        //вешаем Listener на кнопки
        Button buttonMsc = findViewById(R.id.button_Moscow);
        buttonMsc.setOnClickListener(CityListener);

        Button buttonSpb = findViewById(R.id.button_St_Petersburg);
        buttonSpb.setOnClickListener(CityListener);

        Button buttonSochi = findViewById(R.id.button_Sochi);
        buttonSochi.setOnClickListener(CityListener);

        Button buttonEnterCity = findViewById(R.id.btn_enter_city);
        buttonEnterCity.setOnClickListener(CityListener);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("AppState", "method onStart is called");
    }

    @Override
    protected void onRestoreInstanceState(Bundle inState) {
        super.onRestoreInstanceState(inState);
        temp = inState.getInt(TEMPERATURE);
        tempValue.setText(String.valueOf(temp));
        Log.d("AppState", "Data recovery");
        cityName.setText(MainPresenter.getInstance().getCity());
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(getApplicationContext(), "Приложение восстановлено", Toast.LENGTH_SHORT).show();
        Log.d("AppState", "method onResume is called");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        temp = 20;
        outState.putInt(TEMPERATURE, temp);
        Log.d("AppState", "Data storage");
        MainPresenter.getInstance().changeCity();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(getApplicationContext(), "Приложение свернуто", Toast.LENGTH_SHORT).show();
        Log.d("AppState", "method onStop is called");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("AppState", "method onRestart is called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(getApplicationContext(), "Приложение закрыто", Toast.LENGTH_SHORT).show();
        Log.d("AppState", "method onDestroy is called");
    }
}
