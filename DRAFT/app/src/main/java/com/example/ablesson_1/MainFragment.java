package com.example.ablesson_1;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import static com.example.ablesson_1.CityFragment.PARCEL;

public class MainFragment extends Fragment {

    private boolean isLandscape;    // true - планшетная ориентация
    private Parcel currentParcel;   // Текущая посылка (название города)

    //ClickListener на TextView поля списка RecyclerView
    private CitiesAdapter.RecyclerItemClickListener clickListener = new CitiesAdapter.RecyclerItemClickListener() {
        @Override
        public void onItemClick(String data) {
            //при каждом клике создается новая посылка с названием города
            currentParcel = new Parcel(data);
            //и запускается метод отображения с названием города
            showSecondFragment(currentParcel);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //подтягиваем наш список городов
        String[] data = getResources().getStringArray(R.array.items);
        //инициализируем RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        //подсказываем, что наш список конечный
        recyclerView.setHasFixedSize(true);
        //инициализируем LayoutManager (повторно, он был проинициализирован в fragment_main)
        if (getContext() != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        }
        //инициализируем адаптер
        CitiesAdapter citiesAdapter = new CitiesAdapter(data);
        //вешаем Listener на адаптер
        citiesAdapter.setClickListener(clickListener);
        //устанавливаем нашему списку адаптер
        recyclerView.setAdapter(citiesAdapter);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Определение, можно ли будет расположить фрагмет с погодой рядом с фрагментом городов
        isLandscape = getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;

        // Если это не первое создание, то восстановим текущую позицию
        //+ Здесь также востанавливаем Parcel
        if (savedInstanceState != null) {
            // Восстановление текущей позиции.
            currentParcel = (Parcel) savedInstanceState.getSerializable("CurrentCity");
        } else {
            //+ Если воcстановить не удалось, то сделаем пустой объект
            currentParcel = new Parcel(getResources().getStringArray(R.array.items)[0]);
        }

        // Если можно вставить фрагмент с погодой, то сделаем это
        if (isLandscape) {
            showSecondFragment(currentParcel);
        }
    }

    // Сохраним текущую позицию (вызывается перед выходом из фрагмента)
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        //+ Также меняем текущую позицию на Parcel
        outState.putSerializable("CurrentCity", currentParcel);
        super.onSaveInstanceState(outState);
    }

    // Показать 2 фрагмент. Ecли возможно, то показать рядом с первым,
    // если нет, то открыть вторую activity
    private void showSecondFragment(Parcel parcel) {
        if (isLandscape) {
            // Проверим, что фрагмент с погодой существует в activity
            CityFragment detail = (CityFragment)
                    getFragmentManager().findFragmentById(R.id.frame_for_fragment_2);
            // Если есть необходимость, то выведем 2 фрагмент
            //+ Здесь также применяем Parcel
            if (detail == null || !parcel.getCityName().equals(detail.getParcel().getCityName())) {
                // Создаем новый фрагмент с текущей позицией для вывода герба
                detail = CityFragment.create(parcel);

                // Выполняем транзакцию по замене фрагмента
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.frame_for_fragment_2, detail);  // замена фрагмента
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }
        } else {
            // Если нельзя вывести герб рядом, откроем вторую activity
            FragmentActivity fragmentActivity = getActivity();
            if (fragmentActivity != null) {
                Intent intent = new Intent(fragmentActivity, SecondActivity.class);
                //+ и передадим туда Parcel
                intent.putExtra(PARCEL, parcel);
                startActivity(intent);
            }
        }
    }

/*
    //лисенер на кнопки выбора города
    private final View.OnClickListener cityListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent cityIntent = new Intent(MainFragment.this, CityFragment.class);
            EditText EnterCity = findViewById(R.id.ET_enter_city);
            switch (v.getId()) {
                case R.id.btn_enter_city:
                    cityIntent.putExtra(CITY, EnterCity.getText().toString());
                    break;
                default:
                    cityIntent.putExtra(CITY, ((Button) v).getText().toString());
                    break;
            }
            startActivity(cityIntent);
        }
    };

/*

    private static final String TEMPERATURE = "temp key";
    private int temp;
    private TextView tempValue;
    private TextView cityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);






        Toast.makeText(this, "Приложение запускается", Toast.LENGTH_SHORT).show();
        Log.d("MainFragment", "method onCreate is called");

        //вешаем Listener на кнопки
        Button buttonMsc = findViewById(R.id.button_Moscow);
        buttonMsc.setOnClickListener(cityListener);

        Button buttonSpb = findViewById(R.id.button_St_Petersburg);
        buttonSpb.setOnClickListener(cityListener);

        Button buttonSochi = findViewById(R.id.button_Sochi);
        buttonSochi.setOnClickListener(cityListener);

        Button buttonEnterCity = findViewById(R.id.btn_enter_city);
        buttonEnterCity.setOnClickListener(cityListener);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("MainFragment", "method onStart is called");
    }

    @Override
    protected void onRestoreInstanceState(Bundle inState) {
        super.onRestoreInstanceState(inState);
        temp = inState.getInt(TEMPERATURE);
        tempValue.setText(String.valueOf(temp));
        Log.d("MainFragment", "Data recovery");
        cityName.setText(MainPresenter.getInstance().getCity());
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(getApplicationContext(), "Приложение восстановлено", Toast.LENGTH_SHORT).show();
        Log.d("MainFragment", "method onResume is called");
    }



    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(getApplicationContext(), "Приложение свернуто", Toast.LENGTH_SHORT).show();
        Log.d("MainFragment", "method onStop is called");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("MainFragment", "method onRestart is called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(getApplicationContext(), "Приложение закрыто", Toast.LENGTH_SHORT).show();
        Log.d("MainFragment", "method onDestroy is called");
    }*/
}
