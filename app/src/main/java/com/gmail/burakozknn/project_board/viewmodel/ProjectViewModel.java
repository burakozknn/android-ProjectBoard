package com.gmail.burakozknn.project_board.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.gmail.burakozknn.project_board.model.Project;
import com.gmail.burakozknn.project_board.model.ProjectRepository;

import java.util.List;

public class ProjectViewModel extends AndroidViewModel {
    private ProjectRepository repository;
    private LiveData<List<Project>> allProjects;

    public ProjectViewModel(@NonNull Application application) {
        super(application);
        repository = new ProjectRepository(application);
        allProjects = repository.getAllProjects();
    }

    public void insert(Project project) {
        repository.insert(project);
    }

    public void update(Project project) {
        repository.update(project);
    }

    public void delete(Project project) {
        repository.delete(project);
    }

    public void deleteAllProjects() {
        repository.deleteAllProjects();
    }

    public LiveData<List<Project>> getAllProjects() {
        return allProjects;
    }
}
