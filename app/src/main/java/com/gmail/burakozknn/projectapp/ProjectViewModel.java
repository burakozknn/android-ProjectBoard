package com.gmail.burakozknn.projectapp;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

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
        repository.deleteAllProjets();
    }

    public LiveData<List<Project>> getAllProjects() {
        return allProjects;
    }
}
