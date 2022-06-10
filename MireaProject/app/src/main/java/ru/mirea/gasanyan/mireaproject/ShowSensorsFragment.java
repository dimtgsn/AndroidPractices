package ru.mirea.gasanyan.mireaproject;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ShowSensorsFragment extends Fragment implements SensorEventListener {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public ShowSensorsFragment() { }

    public static ShowSensorsFragment newInstance(String param1, String param2) {
        ShowSensorsFragment fragment = new ShowSensorsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private SensorManager sensorManager;
    private TextView lightSensorValue;
    private TextView magneticText;
    private TextView azimuthTextView;
    private TextView pitchTextView;
    private TextView rollTextView;
    private Sensor lightSensor;
    private Sensor magneticSensor;
    private Sensor accelerometerSensor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_sensors, container, false);

        lightSensorValue = view.findViewById(R.id.light_sensor);
        magneticText =  view.findViewById(R.id.magnetic_sensor);
        azimuthTextView = view.findViewById(R.id.acceler_x);

        pitchTextView = view.findViewById(R.id.acceler_y);
        rollTextView = view.findViewById(R.id.acceler_z);

        if (getActivity() != null){
            sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);

            lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
            magneticSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
            accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, magneticSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            float valueMagnetic = event.values[0];
            magneticText.setText("Magnetic field: " + valueMagnetic);
        }
        else  if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float valueAzimuth = event.values[0];
            float valuePitch = event.values[1];
            float valueRoll = event.values[2];

            azimuthTextView.setText("Azimuth: " + valueAzimuth);
            pitchTextView.setText("Pitch: " + valuePitch);
            rollTextView.setText("Roll: " + valueRoll);
        }
        else if(event.sensor.getType() == Sensor.TYPE_LIGHT){
            String newValue = "Light: "+event.values[0] + " лк";
            lightSensorValue.setText(newValue);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}