package ru.mirea.gasanyan.myapplication;

import android.content.Context;
import android.hardware.Sensor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SensorsAdapter extends RecyclerView.Adapter<SensorsAdapter.ViewHolder>{

    private final LayoutInflater inflater;
    private final List<Sensor> sensors;

    SensorsAdapter(Context context, List<Sensor> sensors) {

        this.sensors = sensors;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public SensorsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(SensorsAdapter.ViewHolder holder, int position) {
        Sensor sensor = sensors.get(position);
        holder.nameView.setText(sensor.getName());
    }

    @Override
    public int getItemCount() {
        return sensors.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        final TextView nameView;

        ViewHolder(View view){
            super(view);
            nameView = view.findViewById(R.id.name);
        }
    }
}