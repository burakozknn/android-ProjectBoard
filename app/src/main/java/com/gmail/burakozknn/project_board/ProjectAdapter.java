package com.gmail.burakozknn.project_board;

import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ProjectAdapter extends ListAdapter<Project, ProjectAdapter.ProjectHolder> {

    //private List<Project> projects = new ArrayList<>();
    private OnItemClickListener listener;

    public ProjectAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Project> DIFF_CALLBACK = new DiffUtil.ItemCallback<Project>() {
        @Override
        public boolean areItemsTheSame(@NonNull Project oldProject, @NonNull Project newProject) {
            return oldProject.getId() == newProject.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Project oldProject, @NonNull Project newProject) {
            return oldProject.getTitle().equals(newProject.getTitle()) &&
                    oldProject.getDescription().equals(newProject.getDescription())
                    && oldProject.getPrice() == newProject.getPrice();
        }
    };

    @NonNull
    @Override
    public ProjectHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.project_item, parent, false);


        return new ProjectHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectHolder holder, int position) {

        Project currentProject = getItem(position);
        holder.textViewTitle.setText(currentProject.getTitle());
        holder.textViewBudget.setText(R.string.budget);
        holder.textViewPrice.setText(String.valueOf(currentProject.getPrice()));


    }



    public Project getProjectAt(int position) {
        return getItem(position);
    }

    class ProjectHolder extends RecyclerView.ViewHolder {

        private TextView textViewTitle;
        private TextView textViewBudget;
        private TextView textViewPrice;

        public ProjectHolder(@NonNull View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewBudget = itemView.findViewById(R.id.text_view_budget);
            textViewPrice = itemView.findViewById(R.id.text_view_price);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
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
