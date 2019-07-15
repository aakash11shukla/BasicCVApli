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
import com.example.basiccvapli.models.Internship;

public class InternshipAdapter extends ListAdapter<Internship, InternshipAdapter.InternshipViewHolder> {

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        public void OnItemClick(int position);
    }

    private static final DiffUtil.ItemCallback<Internship> DIFF_CALLBACK = new DiffUtil.ItemCallback<Internship>() {
        @Override
        public boolean areItemsTheSame(@NonNull Internship oldItem, @NonNull Internship newItem) {
            return oldItem.getIid().equals(newItem.getIid());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Internship oldItem, @NonNull Internship newItem) {
            return oldItem.getCompanyName().equals(newItem.getCompanyName()) &&
                    oldItem.getIndustry().equals(newItem.getIndustry()) &&
                    oldItem.getDesignation().equals(newItem.getDesignation()) &&
                    oldItem.getLocation().equals(newItem.getLocation()) &&
                    oldItem.getFromDate().equals(newItem.getFromDate()) &&
                    oldItem.getToDate().equals(newItem.getToDate());
        }
    };

    public InternshipAdapter(OnItemClickListener listener) {
        super(DIFF_CALLBACK);
        this.listener = listener;
    }

    @NonNull
    @Override
    public InternshipViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new InternshipViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.experience_list_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull InternshipViewHolder holder, int position) {
        Internship experience = getItem(position);
        holder.comapnyNameTextview.setText(experience.getCompanyName());
        holder.designationTextview.setText(experience.getDesignation());
        holder.fromTextview.setText(experience.getFromDate());
        holder.toTextview.setText(experience.getToDate());
        holder.locationTextview.setText(experience.getLocation());
        holder.industryTextview.setText(experience.getIndustry());
        holder.bind(position);
    }

    class InternshipViewHolder extends RecyclerView.ViewHolder {

        TextView comapnyNameTextview;
        TextView industryTextview;
        TextView designationTextview;
        TextView locationTextview;
        TextView fromTextview;
        TextView toTextview;

        InternshipViewHolder(@NonNull View itemView) {
            super(itemView);
            comapnyNameTextview = itemView.findViewById(R.id.companyName);
            industryTextview = itemView.findViewById(R.id.industry);
            designationTextview = itemView.findViewById(R.id.designation);
            locationTextview = itemView.findViewById(R.id.location);
            fromTextview = itemView.findViewById(R.id.from);
            toTextview = itemView.findViewById(R.id.to);
        }

        private void bind(final int pos) {
            this.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.OnItemClick(pos);
                }
            });
        }
    }
}
