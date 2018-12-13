package com.gmail.burakozknn.projectapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ProjectHolder> {

    private List<Project> projects = new ArrayList<>();

    @NonNull
    @Override
    public ProjectHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.project_item,parent,false);

        return new ProjectHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectHolder holder, int position) {

        Project currentProject = projects.get(position);

        holder.textViewTitle.setText(currentProject.getTitle());
        holder.textViewPrice.setText(String.valueOf(currentProject.getPrice()));
        holder.textViewBudget.setText(R.string.budget);

    }

    @Override
    public int getItemCount() {
        return projects.size();
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
        notifyDataSetChanged();
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
        }
    }
}
