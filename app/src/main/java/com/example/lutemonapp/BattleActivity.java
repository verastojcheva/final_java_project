package com.example.lutemonapp;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.lutemonapp.models.Lutemon;
import com.example.lutemonapp.storage.Storage;

import java.util.ArrayList;
import java.util.Random;

public class BattleActivity extends AppCompatActivity {

    Spinner spinner1, spinner2;
    Button buttonBattle;
    TextView textBattleLog;
    ImageView imageLutemon1, imageLutemon2, imageArrow;

    ArrayList<Lutemon> lutemons;
    Lutemon lutemonA, lutemonB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);

        spinner1 = findViewById(R.id.spinnerLutemon1);
        spinner2 = findViewById(R.id.spinnerLutemon2);
        buttonBattle = findViewById(R.id.buttonStartBattle);
        textBattleLog = findViewById(R.id.textBattleLog);
        imageLutemon1 = findViewById(R.id.imageLutemon1);
        imageLutemon2 = findViewById(R.id.imageLutemon2);
        imageArrow = findViewById(R.id.imageArrow);

        // Show all available Lutemons (from home)
        lutemons = Storage.getInstance().getHomeLutemons();

        ArrayAdapter<Lutemon> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, lutemons);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);
        spinner2.setAdapter(adapter);

        buttonBattle.setOnClickListener(v -> {
            int pos1 = spinner1.getSelectedItemPosition();
            int pos2 = spinner2.getSelectedItemPosition();

            if (pos1 == pos2) {
                textBattleLog.setText("Please choose two different Lutemons.");
                return;
            }

            lutemonA = lutemons.get(pos1);
            lutemonB = lutemons.get(pos2);

            imageLutemon1.setImageResource(getDrawableForColor(lutemonA.getColor()));
            imageLutemon2.setImageResource(getDrawableForColor(lutemonB.getColor()));

            StringBuilder log = new StringBuilder();
            log.append("Battle begins between ")
                    .append(lutemonA.getName()).append(" and ").append(lutemonB.getName()).append("\n\n");

            Lutemon attacker = lutemonA;
            Lutemon defender = lutemonB;

            Random rand = new Random();

            while (attacker.isAlive() && defender.isAlive()) {
                // Arrow visualization
                imageArrow.setRotation(attacker == lutemonA ? 0 : 180);

                int damage = attacker.getAttack() + rand.nextInt(4) - defender.getDefense();
                damage = Math.max(damage, 0);
                defender.takeDamage(damage);

                log.append(attacker.getName())
                        .append(" attacks ").append(defender.getName())
                        .append(" for ").append(damage).append(" damage.\n");

                if (!defender.isAlive()) {
                    log.append(defender.getName()).append(" has been defeated!\n");

                    // XP & stats
                    attacker.gainExperience();
                    attacker.recordBattle(true);
                    defender.recordBattle(false);

                    // Reset health
                    attacker.restoreHealth();
                    defender.resetToInitialState();

                    Storage.getInstance().saveLutemons(getApplicationContext());
                    break;
                }

                // Swap attacker/defender
                Lutemon temp = attacker;
                attacker = defender;
                defender = temp;
            }

            textBattleLog.setText(log.toString());
        });
    }

    private int getDrawableForColor(String color) {
        switch (color.toLowerCase()) {
            case "white": return R.drawable.white_lutemon;
            case "green": return R.drawable.green_lutemon;
            case "pink": return R.drawable.pink_lutemon;
            case "orange": return R.drawable.orange_lutemon;
            case "black": return R.drawable.black_lutemon;
            default: return R.drawable.ic_launcher_foreground;
        }
    }
}
