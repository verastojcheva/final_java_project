package com.example.lutemonapp;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.lutemonapp.models.*;
import com.example.lutemonapp.storage.Storage;

public class CreateLutemonActivity extends AppCompatActivity {

    private EditText editName;
    private RadioGroup radioGroup;
    private ImageView imagePreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_lutemon);

        editName = findViewById(R.id.editTextLutemonName);
        radioGroup = findViewById(R.id.radioGroupColors);
        imagePreview = findViewById(R.id.imageViewPreview);

        Button buttonCreate = findViewById(R.id.buttonCreate);
        Button buttonCancel = findViewById(R.id.buttonCancel);

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radioWhite) {
                imagePreview.setImageResource(R.drawable.white_lutemon);
            } else if (checkedId == R.id.radioGreen) {
                imagePreview.setImageResource(R.drawable.green_lutemon);
            } else if (checkedId == R.id.radioPink) {
                imagePreview.setImageResource(R.drawable.pink_lutemon);
            } else if (checkedId == R.id.radioOrange) {
                imagePreview.setImageResource(R.drawable.orange_lutemon);
            } else if (checkedId == R.id.radioBlack) {
                imagePreview.setImageResource(R.drawable.black_lutemon);
            }
        });

        buttonCreate.setOnClickListener(v -> {
            String name = editName.getText().toString().trim();
            Lutemon newLutemon = null;

            int selectedId = radioGroup.getCheckedRadioButtonId();

            if (selectedId == R.id.radioWhite) {
                newLutemon = new WhiteLutemon(name);
            } else if (selectedId == R.id.radioGreen) {
                newLutemon = new GreenLutemon(name);
            } else if (selectedId == R.id.radioPink) {
                newLutemon = new PinkLutemon(name);
            } else if (selectedId == R.id.radioOrange) {
                newLutemon = new OrangeLutemon(name);
            } else if (selectedId == R.id.radioBlack) {
                newLutemon = new BlackLutemon(name);
            }

            if (newLutemon != null) {
                Storage.getInstance().getHomeLutemons().add(newLutemon);
                Storage.getInstance().saveLutemons(getApplicationContext());
                Toast.makeText(this, "Lutemon created!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Please select a color.", Toast.LENGTH_SHORT).show();
            }
        });

        buttonCancel.setOnClickListener(v -> finish());
    }
}

