package ru.mirea.gasanyan.notebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;

import java.io.FileOutputStream;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private EditText fileName;
    private EditText fileText;
    private SharedPreferences preferences;
    private final String FILE_NAME = "notebook";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fileName = findViewById(R.id.name);
        fileText = findViewById(R.id.text);
        preferences = getPreferences(MODE_PRIVATE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String name = preferences.getString(FILE_NAME, null);
        if (name != null){
            try(FileInputStream fileInputStream = openFileInput(name)){
                byte[] bytes = new byte[fileInputStream.available()];
                fileInputStream.read(bytes);
                String content = new String(bytes);
                fileName.setText(name);
                fileText.setText(content);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        String name = fileName.getText().toString();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(FILE_NAME, name);
        editor.apply();
        try(FileOutputStream fos = openFileOutput(name, Context.MODE_PRIVATE)){
            fos.write(fileText.getText().toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}