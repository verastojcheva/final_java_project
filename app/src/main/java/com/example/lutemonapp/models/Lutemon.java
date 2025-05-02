package com.example.lutemonapp.models;

import java.io.Serializable;

public abstract class Lutemon implements Serializable {

    protected String name, color;
    protected int attack, defense, experience;
    protected int maxHealth, currentHealth;

    private boolean training = false;
    private boolean battling = false;

    private int battlesFought = 0;
    private int battlesWon = 0;
    private int trainingSessions = 0;

    public Lutemon(String name, String color, int attack, int defense, int maxHealth) {
        this.name = name;
        this.color = color;
        this.attack = attack;
        this.defense = defense;
        this.experience = 0;
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
    }

    public String getName() { return name; }
    public String getColor() { return color; }
    public int getAttack() { return attack + experience; }
    public int getDefense() { return defense; }
    public int getExperience() { return experience; }
    public int getCurrentHealth() { return currentHealth; }
    public int getMaxHealth() { return maxHealth; }

    public void takeDamage(int damage) {
        this.currentHealth -= damage;
    }

    public void gainExperience() {
        this.experience++;
    }

    public boolean isAlive() {
        return currentHealth > 0;
    }

    public void restoreHealth() {
        this.currentHealth = maxHealth;
    }

    public void resetToInitialState() {
        this.experience = 0;
        this.currentHealth = this.maxHealth;
    }

    public void setTraining(boolean value) { this.training = value; }
    public boolean isTraining() { return training; }

    public void setBattling(boolean value) { this.battling = value; }
    public boolean isBattling() { return battling; }

    public void recordBattle(boolean won) {
        battlesFought++;
        if (won) battlesWon++;
    }

    public void recordTraining() {
        trainingSessions++;
    }

    public int getBattlesFought() { return battlesFought; }
    public int getBattlesWon() { return battlesWon; }
    public int getTrainingSessions() { return trainingSessions; }

    public String getStats() {
        return String.format("ATK: %d, DEF: %d, HP: %d, XP: %d", getAttack(), defense, currentHealth, experience);
    }

    @Override
    public String toString() {
        return name + " (" + color + ")";
    }
}
