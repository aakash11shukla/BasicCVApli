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
import com.example.basiccvapli.models.Skill;

public class SkillAdapter extends ListAdapter<Skill, SkillAdapter.SkillViewHolder> {

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        public void OnItemClick(int position);
    }

    private static final DiffUtil.ItemCallback<Skill> DIFF_CALLBACK = new DiffUtil.ItemCallback<Skill>() {
        @Override
        public boolean areItemsTheSame(@NonNull Skill oldItem, @NonNull Skill newItem) {
            return oldItem.getSid().equals(newItem.getSid());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Skill oldItem, @NonNull Skill newItem) {
            return oldItem.getSid().equals(newItem.getSid()) &&
                    oldItem.getSkillLevel().equals(newItem.getSkillLevel()) &&
                    oldItem.getSkillName().equals(newItem.getSkillName());
        }
    };

    public SkillAdapter(OnItemClickListener listener) {
        super(DIFF_CALLBACK);
        this.listener = listener;
    }

    @NonNull
    @Override
    public SkillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SkillViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.skill_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SkillViewHolder holder, int position) {
        Skill skill = getItem(position);
        holder.skillName.setText(skill.getSkillName());
        holder.skillLevel.setText(skill.getSkillLevel());
        holder.bind(position);
    }

    class SkillViewHolder extends RecyclerView.ViewHolder {

        private TextView skillName;
        private TextView skillLevel;

        SkillViewHolder(@NonNull View itemView) {
            super(itemView);
            skillName = itemView.findViewById(R.id.skill_name);
            skillLevel = itemView.findViewById(R.id.skill_level);
        }


        void bind(final int position) {
            this.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.OnItemClick(position);
                }
            });
        }
    }
}
