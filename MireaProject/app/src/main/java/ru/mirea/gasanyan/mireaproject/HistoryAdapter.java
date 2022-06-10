package ru.mirea.gasanyan.mireaproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.StoryViewHolder>{
    private List<Owner> owners;

    public HistoryAdapter(List<Owner> owners){
        this.owners = owners;
    }

    public void addStoryToList(Owner owner){
        owners.add(owner);
    }

    @Override
    public StoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View cardView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new StoryViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull StoryViewHolder holder, int position) {
        Owner owner = owners.get(position);
        holder.nameField.setText(owner.name);
        holder.ageField.setText(owner.age);
        holder.jobField.setText(owner.job);
    }

    @Override
    public int getItemCount() {
        return owners.size();
    }

    public static class StoryViewHolder extends RecyclerView.ViewHolder{
        public TextView jobField;

        public TextView ageField;
        public TextView nameField;

        public StoryViewHolder(View itemView) {
            super(itemView);
            nameField = itemView.findViewById(R.id.name);
            jobField = itemView.findViewById(R.id.job);
            ageField =  itemView.findViewById(R.id.age);
        }
    }
}