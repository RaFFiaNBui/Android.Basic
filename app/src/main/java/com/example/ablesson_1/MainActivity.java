package com.example.ablesson_1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    private final View.OnClickListener onClickListener_loading = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            frameLayout_first_page.setVisibility(View.VISIBLE);
            linearLayout_loading.setVisibility(View.GONE);
        }
    };


    private LinearLayout linearLayout_loading;
    private FrameLayout frameLayout_first_page;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);

        /*frameLayout_first_page = findViewById(R.id.FrameLayout_first_page);
        linearLayout_loading = findViewById(R.id.LinearLayout_loading);

        Button button_loading = findViewById(R.id.button_loading);
        button_loading.setOnClickListener(onClickListener_loading);*/


    }
}
