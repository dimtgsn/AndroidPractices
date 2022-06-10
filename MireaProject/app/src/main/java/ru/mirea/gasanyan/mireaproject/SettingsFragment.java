package ru.mirea.gasanyan.mireaproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class SettingsFragment extends Fragment {
    private EditText ageField;
    private EditText jobField;
    private EditText nameField;
    private SharedPreferences preferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        nameField = view.findViewById(R.id.nameEdit);
        ageField = view.findViewById(R.id.ageEdit);
        jobField = view.findViewById(R.id.jobEdit);

        Button button = view.findViewById(R.id.btn_update);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", "button-clicked");
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("name", nameField.getText().toString());
                editor.putString("age", ageField.getText().toString());
                editor.putString("job", jobField.getText().toString());

                editor.apply();
                Toast.makeText(getActivity(), "Update saved!", Toast.LENGTH_SHORT).show();
            }
        });

        preferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        try {
            ageField.setText(preferences.getString("age", "age"));
            jobField.setText(preferences.getString("job", "job"));
            nameField.setText(preferences.getString("name", "name"));
        } catch (Exception e) {
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return view;
    }
}