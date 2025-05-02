package com.example.lutemonapp;

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

public class LutemonAdapter extends RecyclerView.Adapter<LutemonAdapter.ViewHolder> {

    private ArrayList<Lutemon> lutemons;
    private HashSet<Lutemon> selectedLutemons = new HashSet<>();

    public LutemonAdapter(ArrayList<Lutemon> lutemons) {
        this.lutemons = lutemons;
    }

    public ArrayList<Lutemon> getSelectedLutemons() {
        return new ArrayList<>(selectedLutemons);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name, stats;
        ImageButton btnDelete;
        CheckBox checkbox;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageLutemon);
            name = itemView.findViewById(R.id.textLutemonName);
            stats = itemView.findViewById(R.id.textLutemonStats);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            checkbox = itemView.findViewById(R.id.checkboxSelect);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lutemon, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Lutemon l = lutemons.get(position);
        holder.name.setText(l.getName() + " (" + l.getColor() + ")");
        holder.stats.setText(l.getStats());

        // Set the correct Lutemon image
        switch (l.getColor().toLowerCase()) {
            case "white": holder.image.setImageResource(R.drawable.white_lutemon); break;
            case "green": holder.image.setImageResource(R.drawable.green_lutemon); break;
            case "pink": holder.image.setImageResource(R.drawable.pink_lutemon); break;
            case "orange": holder.image.setImageResource(R.drawable.orange_lutemon); break;
            case "black": holder.image.setImageResource(R.drawable.black_lutemon); break;
        }

        // Update checkbox state without triggering listener
        holder.checkbox.setOnCheckedChangeListener(null);
        holder.checkbox.setChecked(selectedLutemons.contains(l));

        holder.checkbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                selectedLutemons.add(l);
            } else {
                selectedLutemons.remove(l);
            }
        });

        holder.btnDelete.setOnClickListener(v -> {
            Storage.getInstance().getHomeLutemons().remove(l);
            lutemons.remove(position);
            Storage.getInstance().saveLutemons(holder.itemView.getContext()); // Save after deletion ✅
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, lutemons.size());
        });
    }

    @Override
    public int getItemCount() {
        return lutemons.size();
    }

    public void clearSelection() {
        selectedLutemons.clear();
    }

}
