package ru.geekbrains.weatherapp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import ru.geekbrains.weatherapp.R;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;

import ru.geekbrains.weatherapp.R;
import ru.geekbrains.weatherapp.SelectCityListener;
import ru.geekbrains.weatherapp.fragments.ShowCityFragment;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private SelectCityListener selectCityListener;
    private TextInputEditText cityNameText;
    private MaterialButton findBtn;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        cityNameText = root.findViewById(R.id.inputTextFindCity);
        findBtn = root.findViewById(R.id.btnFindCity);
        findBtn.setOnClickListener(v -> {
            String s = Objects.requireNonNull(cityNameText.getText()).toString();
            Snackbar.make(root, "Finding city...", Snackbar.LENGTH_SHORT).show();
            selectCityListener.onOpenFragmentWeatherMain(s);
        });
//        setRetainInstance(true);

        return root;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof SelectCityListener){
            selectCityListener = (SelectCityListener) context;
        }else {
            throw new RuntimeException(context.toString());
        }
    }
}