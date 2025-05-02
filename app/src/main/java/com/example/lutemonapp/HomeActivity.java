package com.example.lutemonapp;

import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.lutemonapp.models.Lutemon;
import com.example.lutemonapp.storage.Storage;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LutemonAdapter adapter;
    private Button buttonMoveToTraining, buttonMoveToBattle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView = findViewById(R.id.recyclerViewHome);
        buttonMoveToTraining = findViewById(R.id.buttonMoveToTraining);
        buttonMoveToBattle = findViewById(R.id.buttonMoveToBattle);

        ArrayList<Lutemon> homeLutemons = Storage.getInstance().getHomeLutemons();

        adapter = new LutemonAdapter(homeLutemons);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        buttonMoveToTraining.setOnClickListener(v -> {
            ArrayList<Lutemon> selected = adapter.getSelectedLutemons();
            for (Lutemon l : selected) {
                l.setTraining(true); // ✅ Mark as training
            }
            Storage.getInstance().saveLutemons(getApplicationContext());
            adapter.clearSelection();
            adapter.notifyDataSetChanged();
        });

        buttonMoveToBattle.setOnClickListener(v -> {
            ArrayList<Lutemon> selected = adapter.getSelectedLutemons();
            for (Lutemon l : selected) {
                l.setBattling(true); // ✅ Mark as battling
            }
            Storage.getInstance().saveLutemons(getApplicationContext());
            adapter.clearSelection();
            adapter.notifyDataSetChanged();
        });
    }
}
