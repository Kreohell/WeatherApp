package ru.geekbrains.weatherapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerDataAdapter extends RecyclerView.Adapter<RecyclerDataAdapter.ViewHolder> {

    private ArrayList<SelectedWeather> data;

    public RecyclerDataAdapter(ArrayList<SelectedWeather> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.weather_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerDataAdapter.ViewHolder holder, int position) {
        SelectedWeather selectedWeather = data.get(position);
        holder.textDay.setText(selectedWeather.getDay());
        holder.textTemperature.setText(selectedWeather.getTemperature());
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textDay, textTemperature;

        public ViewHolder (@NonNull View view){
            super(view);
            textDay = view.findViewById(R.id.textViewDayTwo);
            textTemperature = view.findViewById(R.id.textViewTemperatureTwo);
        }
    }

}
