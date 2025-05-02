package com.example.lutemonapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.lutemonapp.storage.Storage;
import com.example.lutemonapp.models.Lutemon;


public class MainActivity extends AppCompatActivity {

    private Button btnHome, btnTraining, btnBattle, btnStats, btnCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnHome = findViewById(R.id.buttonViewHome);
        btnTraining = findViewById(R.id.buttonTraining);
        btnBattle = findViewById(R.id.buttonBattle);
        btnStats = findViewById(R.id.buttonStats);
        btnCreate = findViewById(R.id.buttonCreateLutemon);

        btnHome.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, HomeActivity.class)));
        btnTraining.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, TrainingActivity.class)));
        btnBattle.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, BattleActivity.class)));
        btnStats.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, StatsActivity.class)));
        btnCreate.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, CreateLutemonActivity.class)));
    }

    @Override
    protected void onResume() {
        super.onResume();

        TextView homeText = findViewById(R.id.textHomeStats);
        TextView trainingText = findViewById(R.id.textTrainingStats);
        TextView battleText = findViewById(R.id.textBattleStats);

        int homeCount = Storage.getInstance().getHomeLutemons().size();

        int trainingCount = 0;
        int battleCount = 0;

        for (Lutemon l : Storage.getInstance().getHomeLutemons()) {
            if (l.isTraining()) trainingCount++;
            if (l.isBattling()) battleCount++;
        }

        homeText.setText("You have " + homeCount + " Lutemons at home");
        trainingText.setText("You have " + trainingCount + " Lutemons training");
        battleText.setText("You have " + battleCount + " Lutemons ready to battle");
    }

}

