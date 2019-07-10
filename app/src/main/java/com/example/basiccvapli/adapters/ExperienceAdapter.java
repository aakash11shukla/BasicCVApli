package com.example.basiccvapli.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.basiccvapli.R;
import com.google.firebase.database.DataSnapshot;

public class ExperienceAdapter extends RecyclerView.Adapter<ExperienceAdapter.ExperienceViewHolder> {

    private DataSnapshot mExperienceList;
    public ExperienceAdapter(DataSnapshot experienceList) {
        mExperienceList = experienceList;
    }

    public void setmExperienceList(DataSnapshot mExperienceList) {
        this.mExperienceList = mExperienceList;
    }

    @NonNull
    @Override
    public ExperienceViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ExperienceViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.experience_list_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ExperienceViewHolder experienceViewHolder, int i) {

//        Experience experience = mExperienceList.get(i);
//        experienceViewHolder.comapnyNameTextview.setText(experience.getCompanyName());
//        experienceViewHolder.industryTextview.setText(experience.getIndustry());
//        experienceViewHolder.designationTextview.setText(experience.getDesignation());
//        experienceViewHolder.locationTextview.setText(experience.getLocation());
//        experienceViewHolder.fromTextview.setText(experience.getFromDate());
//        experienceViewHolder.toTextview.setText(experience.getToDate());
    }

    @Override
    public int getItemCount() {
        return (mExperienceList != null)?(int)mExperienceList.getChildrenCount():0;
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
