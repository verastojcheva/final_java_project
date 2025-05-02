package com.example.lutemonapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.lutemonapp.models.Lutemon;

import java.util.ArrayList;

public class StatsAdapter extends RecyclerView.Adapter<StatsAdapter.ViewHolder> {

    private ArrayList<Lutemon> lutemons;

    public StatsAdapter(ArrayList<Lutemon> lutemons) {
        this.lutemons = lutemons;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, stats;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textLutemonName);
            stats = itemView.findViewById(R.id.textStats);
        }
    }

    @NonNull
    @Override
    public StatsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_stats_lutemon, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StatsAdapter.ViewHolder holder, int position) {
        Lutemon l = lutemons.get(position);
        holder.name.setText(l.getName() + " (" + l.getColor() + ")");
        holder.stats.setText("Battles fought: " + l.getBattlesFought()
                + ", Wins: " + l.getBattlesWon()
                + ", Trainings: " + l.getTrainingSessions());
    }

    @Override
    public int getItemCount() {
        return lutemons.size();
    }
}
