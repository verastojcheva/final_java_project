package com.example.lutemonapp;

import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.lutemonapp.models.Lutemon;
import com.example.lutemonapp.storage.Storage;

import java.util.ArrayList;

public class TrainingActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TrainingAdapter adapter;
    private Button buttonTrain, buttonSendHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);

        recyclerView = findViewById(R.id.recyclerViewTraining);
        buttonTrain = findViewById(R.id.buttonStartTraining);
        buttonSendHome = findViewById(R.id.buttonSendHome);

        // Only show Lutemons that are marked as training
        ArrayList<Lutemon> trainingLutemons = new ArrayList<>();
        for (Lutemon l : Storage.getInstance().getHomeLutemons()) {
            if (l.isTraining()) {
                trainingLutemons.add(l);
            }
        }

        adapter = new TrainingAdapter(trainingLutemons);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        buttonTrain.setOnClickListener(v -> adapter.startTraining());

        buttonSendHome.setOnClickListener(v -> {
            for (Lutemon l : trainingLutemons) {
                l.setTraining(false); // ✅ Mark as done training
            }
            Storage.getInstance().saveLutemons(getApplicationContext());
            adapter.resetTraining();
            finish();
        });
    }
}
