package com.example.lutemonapp;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.lutemonapp.models.Lutemon;
import com.example.lutemonapp.storage.Storage;

import java.util.ArrayList;
import java.util.HashSet;

public class TrainingAdapter extends RecyclerView.Adapter<TrainingAdapter.ViewHolder> {

    private ArrayList<Lutemon> lutemons;
    private HashSet<Lutemon> selectedForTraining = new HashSet<>();
    private boolean isTraining = false;

    public TrainingAdapter(ArrayList<Lutemon> lutemons) {
        this.lutemons = lutemons;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name, stats, percent;
        ProgressBar progressBar;
        CheckBox checkbox;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageLutemon);
            name = itemView.findViewById(R.id.textLutemonName);
            stats = itemView.findViewById(R.id.textLutemonStats);
            percent = itemView.findViewById(R.id.textTrainingPercent);
            progressBar = itemView.findViewById(R.id.progressBarTraining);
            checkbox = itemView.findViewById(R.id.checkboxTrain);
        }
    }

    @NonNull
    @Override
    public TrainingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_training_lutemon, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrainingAdapter.ViewHolder holder, int position) {
        Lutemon l = lutemons.get(position);
        holder.name.setText(l.getName() + " (" + l.getColor() + ")");
        holder.stats.setText(l.getStats());

        switch (l.getColor().toLowerCase()) {
            case "white": holder.image.setImageResource(R.drawable.white_lutemon); break;
            case "green": holder.image.setImageResource(R.drawable.green_lutemon); break;
            case "pink": holder.image.setImageResource(R.drawable.pink_lutemon); break;
            case "orange": holder.image.setImageResource(R.drawable.orange_lutemon); break;
            case "black": holder.image.setImageResource(R.drawable.black_lutemon); break;
        }

        holder.checkbox.setOnCheckedChangeListener(null);
        holder.checkbox.setChecked(selectedForTraining.contains(l));
        holder.checkbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) selectedForTraining.add(l);
            else selectedForTraining.remove(l);
        });

        // Reset progress UI
        holder.progressBar.setProgress(0);
        holder.percent.setText("0%");
    }

    @Override
    public int getItemCount() {
        return lutemons.size();
    }

    public void startTraining() {
        if (isTraining) return;
        isTraining = true;

        for (Lutemon l : selectedForTraining) {
            l.setTraining(true);
        }

        Handler handler = new Handler();
        int duration = 2000; // 2 seconds total
        int interval = 100;
        int steps = duration / interval;

        for (int i = 0; i <= steps; i++) {
            final int progress = i * (100 / steps);
            handler.postDelayed(() -> {
                for (int j = 0; j < lutemons.size(); j++) {
                    Lutemon l = lutemons.get(j);
                    if (selectedForTraining.contains(l)) {
                        notifyItemChanged(j);
                        ViewHolder holder = (ViewHolder) recyclerView.findViewHolderForAdapterPosition(j);
                        if (holder != null) {
                            holder.progressBar.setProgress(progress);
                            holder.percent.setText(progress + "%");
                        }
                    }
                }
            }, i * interval);
        }

        handler.postDelayed(() -> {
            for (Lutemon l : selectedForTraining) {
                l.gainExperience();
                l.recordTraining();
                l.setTraining(false);
            }
            Storage.getInstance().saveLutemons(recyclerView.getContext());
            isTraining = false;
        }, duration + 100);
    }

    private RecyclerView recyclerView;
    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void resetTraining() {
        selectedForTraining.clear();
        notifyDataSetChanged();
    }

    public void clearSelection() {
        selectedForTraining.clear();  // ✅ Correct
    }

}
