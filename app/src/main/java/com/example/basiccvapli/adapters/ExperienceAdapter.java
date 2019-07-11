package com.example.basiccvapli.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.basiccvapli.R;
import com.example.basiccvapli.models.Experience;
import com.google.firebase.database.DataSnapshot;

public class ExperienceAdapter extends ListAdapter<Experience, ExperienceAdapter.ExperienceViewHolder> {

    private static final DiffUtil.ItemCallback<Experience> DIFF_CALLBACK = new DiffUtil.ItemCallback<Experience>() {
        @Override
        public boolean areItemsTheSame(@NonNull Experience oldItem, @NonNull Experience newItem) {
            return oldItem.getEid() == newItem.getEid();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Experience oldItem, @NonNull Experience newItem) {
            return oldItem.getCompanyName().equals(newItem.getCompanyName()) &&
                    oldItem.getIndustry().equals(newItem.getIndustry()) &&
                    oldItem.getDesignation().equals(newItem.getDesignation()) &&
                    oldItem.getLocation().equals(newItem.getLocation()) &&
                    oldItem.getFromDate().equals(newItem.getFromDate()) &&
                    oldItem.getToDate().equals(newItem.getToDate());
        }
    };
    public ExperienceAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public ExperienceViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ExperienceViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.experience_list_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ExperienceViewHolder holder, int position) {
        Experience experience = getItem(position);
        holder.comapnyNameTextview.setText(experience.getCompanyName());
        holder.designationTextview.setText(experience.getDesignation());
        holder.fromTextview.setText(experience.getFromDate());
        holder.toTextview.setText(experience.getToDate());
        holder.locationTextview.setText(experience.getLocation());
        holder.industryTextview.setText(experience.getIndustry());
    }

    class ExperienceViewHolder extends RecyclerView.ViewHolder{

        TextView comapnyNameTextview;
        TextView industryTextview;
        TextView designationTextview;
        TextView locationTextview;
        TextView fromTextview;
        TextView toTextview;

        ExperienceViewHolder(@NonNull View itemView) {
            super(itemView);
            comapnyNameTextview = itemView.findViewById(R.id.companyName);
            industryTextview = itemView.findViewById(R.id.industry);
            designationTextview = itemView.findViewById(R.id.designation);
            locationTextview = itemView.findViewById(R.id.location);
            fromTextview = itemView.findViewById(R.id.from);
            toTextview = itemView.findViewById(R.id.to);
        }
    }
}
