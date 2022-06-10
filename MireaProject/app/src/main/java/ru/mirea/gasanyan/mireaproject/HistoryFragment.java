package ru.mirea.gasanyan.mireaproject;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

public class HistoryFragment extends Fragment {
    private List<Owner> owners;
    private RecyclerView recyclerView;
    private OwnerDao ownerDao;
    private AppDatabase appDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        appDatabase = App.getInstance().getDatabase();

        ownerDao = appDatabase.ownerDao();
        owners = ownerDao.getAll();

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        Button button = view.findViewById(R.id.floatingActionButton);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), OwnerView.class);
                startActivity(intent);
            }
        });

        HistoryAdapter adapter = new HistoryAdapter(owners);
        recyclerView.setAdapter(adapter);

        return view;
    }
}