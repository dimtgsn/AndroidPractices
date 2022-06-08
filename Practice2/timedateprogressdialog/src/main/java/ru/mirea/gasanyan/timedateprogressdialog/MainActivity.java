package ru.mirea.gasanyan.timedateprogressdialog;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickShowDialogTime(View view) {
        MyTimeDialogFragment dialogFragmentTime = new MyTimeDialogFragment();
        dialogFragmentTime.show(getSupportFragmentManager(), "time");
    }

    public void onClickShowDialogDate(View view) {
        MyDateDialogFragment dialogFragmentDate = new MyDateDialogFragment();
        dialogFragmentDate.show(getSupportFragmentManager(), "date");
    }

    public void onClickShowDialogProgress(View view) {
        MyProgressDialogFragment dialogFragmentProgress = new MyProgressDialogFragment();
        dialogFragmentProgress.show(getSupportFragmentManager(), "progress");
    }

    public void onOkClicked() {
        Toast.makeText(getApplicationContext(), "Спасибо, что выбрали \"Да\"!",
                Toast.LENGTH_LONG).show();
    }
    public void onCancelClicked() {
        Toast.makeText(getApplicationContext(), "Очень жаль, что вы выбрали \"Нет\"!",
                Toast.LENGTH_LONG).show();
    }
}