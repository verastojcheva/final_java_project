package com.example.lutemonapp.storage;

import android.content.Context;
import com.example.lutemonapp.models.Lutemon;

import java.io.*;
import java.util.ArrayList;

public class Storage {
    private static Storage instance = null;

    private ArrayList<Lutemon> home = new ArrayList<>();
    private ArrayList<Lutemon> training = new ArrayList<>();
    private ArrayList<Lutemon> battle = new ArrayList<>();

    private Storage() {}

    public static Storage getInstance() {
        if (instance == null) {
            instance = new Storage();
        }
        return instance;
    }

    // Getter methods
    public ArrayList<Lutemon> getHomeLutemons() {
        return home;
    }

    public ArrayList<Lutemon> getTrainingLutemons() {
        return training;
    }

    public ArrayList<Lutemon> getBattleLutemons() {
        return battle;
    }

    // Add new Lutemon to home
    public void addLutemon(Lutemon l) {
        home.add(l);
    }

    // Save data
    public void saveLutemons(Context context) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(
                    context.openFileOutput("lutemons.data", Context.MODE_PRIVATE));
            oos.writeObject(home);
            oos.writeObject(training);
            oos.writeObject(battle);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load data
    public void loadLutemons(Context context) {
        try {
            ObjectInputStream ois = new ObjectInputStream(
                    context.openFileInput("lutemons.data"));
            home = (ArrayList<Lutemon>) ois.readObject();
            training = (ArrayList<Lutemon>) ois.readObject();
            battle = (ArrayList<Lutemon>) ois.readObject();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
