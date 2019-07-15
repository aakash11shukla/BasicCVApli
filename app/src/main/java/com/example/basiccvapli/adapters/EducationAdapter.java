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
import com.example.basiccvapli.models.Education;

public class EducationAdapter extends ListAdapter<Education, EducationAdapter.EducationViewHolder> {

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        public void OnItemClick(int position);
    }

    private static final DiffUtil.ItemCallback<Education> DIFF_CALLBACK = new DiffUtil.ItemCallback<Education>() {
        @Override
        public boolean areItemsTheSame(@NonNull Education oldItem, @NonNull Education newItem) {
            return oldItem.getEdid().equals(newItem.getEdid());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Education oldItem, @NonNull Education newItem) {
            return oldItem.getDegreeType().equals(newItem.getDegreeType()) &&
                    oldItem.getInstituteName().equals(newItem.getInstituteName()) &&
                    oldItem.getFieldOfStudy().equals(newItem.getFieldOfStudy()) &&
                    oldItem.getPercentage().equals(newItem.getPercentage()) &&
                    oldItem.getPertype().equals(newItem.getPertype()) &&
                    oldItem.getFromdate().equals(newItem.getFromdate()) &&
                    oldItem.getTodate().equals(newItem.getTodate());
        }
    };

    public EducationAdapter(EducationAdapter.OnItemClickListener listener) {
        super(DIFF_CALLBACK);
        this.listener = listener;
    }

    @NonNull
    @Override
    public EducationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new EducationViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.education_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull EducationViewHolder holder, int position) {
        Education education = getItem(position);
        holder.degreeType.setText(education.getDegreeType());
        holder.instituteName.setText(education.getInstituteName());
        holder.fieldOfStudy.setText(education.getFieldOfStudy());
        holder.pertype.setText(education.getPertype());
        holder.percentage.setText(education.getPercentage());
        holder.fromdate.setText(education.getFromdate());
        holder.todate.setText(education.getTodate());
        holder.bind(position);
    }

    class EducationViewHolder extends RecyclerView.ViewHolder {

        private TextView degreeType;
        private TextView instituteName;
        private TextView pertype;
        private TextView percentage;
        private TextView fieldOfStudy;
        private TextView fromdate;
        private TextView todate;

        EducationViewHolder(@NonNull View itemView) {
            super(itemView);
            degreeType = itemView.findViewById(R.id.education_type);
            instituteName = itemView.findViewById(R.id.institute_name);
            pertype = itemView.findViewById(R.id.per_type);
            percentage = itemView.findViewById(R.id.percentage);
            fieldOfStudy = itemView.findViewById(R.id.fieldofstudy);
            fromdate = itemView.findViewById(R.id.from_date);
            todate = itemView.findViewById(R.id.to_date);
        }

        void bind(final int position) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.OnItemClick(position);
                }
            });
        }
    }
}
