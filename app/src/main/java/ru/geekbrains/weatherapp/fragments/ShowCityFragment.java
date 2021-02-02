package ru.geekbrains.weatherapp.fragments;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;

import ru.geekbrains.weatherapp.R;
import ru.geekbrains.weatherapp.RecyclerDataAdapter;
import ru.geekbrains.weatherapp.SelectedWeather;
import ru.geekbrains.weatherapp.model.WeatherRequest;

public class ShowCityFragment extends Fragment {

    public final static String TAG = "ru.geekbrains.weather_lesson_one.ShowCityFragment";
    public final static String KEY = "text";
    private RecyclerView showCityRecyclerView;
    private RecyclerDataAdapter adapter;
    ArrayList<SelectedWeather> selectedWeathers = new ArrayList<>();

    private TextView city;
    private TextView temperature;
    private TextView pressure;
    private TextView humidity;
    private TextView windSpeed;

    private static final String TAG_ONE = "WEATHER";
    private static final String WEATHER_URL = "https://api.openweathermap.org/data/2.5/weather?q=";
    private static final String WEATHER_API = "03997d5fb263cc4913f64d028e13564b";
    private static String textFromActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        createWeatherList();
        View view = inflater.inflate(R.layout.fragment_show_city, container, false);
        showCityRecyclerView = view.findViewById(R.id.selectedCityRecyclerView);
        adapter = new RecyclerDataAdapter(selectedWeathers);
        showCityRecyclerView.setAdapter(adapter);
        initViews(view);

        Button refresh = view.findViewById(R.id.refreshButton);
        refresh.setOnClickListener(clickListener);

        assert getArguments() != null;
        textFromActivity = getArguments().getString(KEY);
        city.setText(textFromActivity);

        return view;
    }
    private void initViews(View view){
        temperature = view.findViewById(R.id.textViewTemperature);
        pressure = view.findViewById(R.id.textViewPressure);
        humidity = view.findViewById(R.id.textViewHumidity);
        windSpeed = view.findViewById(R.id.textViewWind);
        city = view.findViewById(R.id.textViewCity);
    }

    private void createWeatherList() {
        selectedWeathers.add(new SelectedWeather("Day1", "temperature1"));
        selectedWeathers.add(new SelectedWeather("Day2", "temperature2"));
        selectedWeathers.add(new SelectedWeather("Day3", "temperature3"));
        selectedWeathers.add(new SelectedWeather("Day4", "temperature4"));
        selectedWeathers.add(new SelectedWeather("Day5", "temperature5"));
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onClick(View v) {
            try {
                final URL uri = new URL(WEATHER_URL + textFromActivity + "&units=metric&appid=" + WEATHER_API);
                final Handler handler = new Handler(Looper.getMainLooper()); // Запоминаем основной поток
                new Thread(() -> {
                    HttpsURLConnection urlConnection = null;
                    try {
                        urlConnection = (HttpsURLConnection) uri.openConnection();
                        urlConnection.setRequestMethod("GET");
                        urlConnection.setReadTimeout(10000);
                        BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                        String result = getLines(in);
                        Gson gson = new Gson();
                        final WeatherRequest weatherRequest = gson.fromJson(result, WeatherRequest.class);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                displayWeather(weatherRequest);
                            }
                        });
                    } catch (Exception e) {
                        Log.e(TAG_ONE, "Fail connection", e);
                        e.printStackTrace();
                    } finally {
                        if (null != urlConnection) {
                            urlConnection.disconnect();
                        }
                    }
                }).start();
            } catch (MalformedURLException e) {
                Log.e(TAG_ONE, "Fail URI", e);
                e.printStackTrace();
            }
        }
    };

    public void displayWeather(WeatherRequest weatherRequest){

        city.setText(weatherRequest.getName());

        String temperatureValue = String.format(Locale.getDefault(), "%.2f", weatherRequest.getMain().getTemp());
        temperature.setText(temperatureValue);

        String pressureText = String.format(Locale.getDefault(),"%d", weatherRequest.getMain().getPressure());
        pressure.setText(pressureText);

        String humidityStr = String.format(Locale.getDefault(), "%d", weatherRequest.getMain().getHumidity());
        humidity.setText(humidityStr);

        String windSpeedStr = String.format(Locale.getDefault(), "%f", weatherRequest.getWind().getSpeed());
        windSpeed.setText(windSpeedStr);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private String getLines(BufferedReader in) {
        return in.lines().collect(Collectors.joining("\n"));
    }
}