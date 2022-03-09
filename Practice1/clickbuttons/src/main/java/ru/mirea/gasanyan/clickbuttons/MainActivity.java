package ru.mirea.gasanyan.clickbuttons;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView tvOut;
    private Button buttonOk;
    private Button buttonCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvOut = findViewById(R.id.tvOut);
        buttonOk = findViewById(R.id.btnOk);
        buttonCancel = findViewById(R.id.btnCancel);

        View.OnClickListener oclBtnOk = new View.OnClickListener(){
            @Override
            public void onClick(View v){
                tvOut.setText("Нажата кнопка OK!");
            }
        };

        buttonOk.setOnClickListener(oclBtnOk);

        View.OnClickListener oclBtnCancel = new View.OnClickListener(){
            @Override
            public void onClick(View v){
                tvOut.setText("TextView");
            }
        };

        buttonCancel.setOnClickListener(oclBtnCancel);

    }
}