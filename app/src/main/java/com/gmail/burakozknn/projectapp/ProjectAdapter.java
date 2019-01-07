package com.gmail.burakozknn.projectapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;


public class ProjectAdapter extends ListAdapter<Project, ProjectAdapter.ProjectHolder> {

    private OnItemClickListener listener;

    public ProjectAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Project> DIFF_CALLBACK = new DiffUtil.ItemCallback<Project>() {
        @Override
        public boolean areItemsTheSame(@NonNull Project oldItem, @NonNull Project newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Project oldItem, @NonNull Project newItem) {
            return oldItem.getTitle().equals(newItem.getTitle()) &&
                    oldItem.getDescription().equals(newItem.getDescription()) &&
                    oldItem.getPrice() == newItem.getPrice() ;
        }
    };


    @NonNull
    @Override
    public ProjectHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.project_item,parent,false);

        return new ProjectHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ProjectHolder holder, int position) {

        Project currentProject = getItem(position);

        holder.textViewTitle.setText(currentProject.getTitle());
        holder.textViewPrice.setText(String.valueOf(currentProject.getPrice()));
        holder.textViewBudget.setText(R.string.budget);

    }


    public Project getProjectAt(int position) {
        return getItem(position);
    }

    class ProjectHolder extends RecyclerView.ViewHolder {

        private TextView textViewTitle;
        private TextView textViewPrice;
        private TextView textViewBudget;

        public ProjectHolder(@NonNull View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewPrice = itemView.findViewById(R.id.text_view_price);
            textViewBudget = itemView.findViewById(R.id.text_view_budget);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });

        }
    }

    public interface OnItemClickListener {
        void onItemClick(Project project);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;

    }

}