package ru.mirea.gasanyan.mireaproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class OwnerView extends AppCompatActivity {
    private AppDatabase appDatabase;
    private OwnerDao ownerDao;
    private EditText jobField,nameField,ageField;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.owner_view);
        nameField = findViewById(R.id.name_owner);
        ageField = findViewById(R.id.age_owner);
        jobField = findViewById(R.id.job_owner);
        appDatabase = App.getInstance().getDatabase();
        ownerDao = appDatabase.ownerDao();
    }

    public void onSave(View view) {
        Owner owner = new Owner();

        owner.age = ageField.getText().toString();
        owner.job = jobField.getText().toString();
        owner.name = nameField.getText().toString();

        ownerDao.insert(owner);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
