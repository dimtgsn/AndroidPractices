package ru.mirea.gasanyan.notebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;

import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    public static String APP_PREFERENCES;
    SharedPreferences mSettings;

    //    private String fileName;
    EditText editFileName;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editFileName = findViewById(R.id.editFileName);
        editText = findViewById(R.id.editText);
    }
}}