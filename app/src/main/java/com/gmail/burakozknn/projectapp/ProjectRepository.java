package com.gmail.burakozknn.projectapp;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class ProjectRepository {

    private ProjectDao projectDao;

    private LiveData<List<Project>> allProjects;

    public ProjectRepository(Application application) {

        ProjectDatabase database = ProjectDatabase.getInstance(application);

        projectDao = database.projectDao();

        allProjects = projectDao.getAllProjects();
    }

    public void insert(Project project){

        new InsertProjectAsyncTask(projectDao).execute(project);

    }

    public void update(Project project){

        new UpdateProjectAsyncTask(projectDao).execute(project);

    }

    public void delete(Project project){

        new DeleteProjectAsyncTask(projectDao).execute(project);

    }

    public void deleteAllProjets(){

        new DeleteAllProjectAsyncTask(projectDao).execute();

    }

    public LiveData<List<Project>> getAllProjects() {
        return allProjects;
    }

    private static class InsertProjectAsyncTask extends AsyncTask<Project,Void,Void> {

        private ProjectDao projectDao;

        private InsertProjectAsyncTask(ProjectDao projectDao) {
            this.projectDao = projectDao;
        }


        @Override
        protected Void doInBackground(Project... projects) {

            projectDao.insert(projects[0]);
            return null;
        }
    }

    private static class UpdateProjectAsyncTask extends AsyncTask<Project,Void,Void> {

        private ProjectDao projectDao;

        private UpdateProjectAsyncTask(ProjectDao projectDao) {
            this.projectDao = projectDao;
        }


        @Override
        protected Void doInBackground(Project... projects) {

            projectDao.update(projects[0]);
            return null;
        }
    }

    private static class DeleteProjectAsyncTask extends AsyncTask<Project,Void,Void> {

        private ProjectDao projectDao;

        private DeleteProjectAsyncTask(ProjectDao projectDao) {
            this.projectDao = projectDao;
        }


        @Override
        protected Void doInBackground(Project... projects) {

            projectDao.delete(projects[0]);
            return null;
        }
    }

    private static class DeleteAllProjectAsyncTask extends AsyncTask<Void,Void,Void> {

        private ProjectDao projectDao;

        private DeleteAllProjectAsyncTask(ProjectDao projectDao) {
            this.projectDao = projectDao;
        }


        @Override
        protected Void doInBackground(Void... voids ) {

            projectDao.deleteAllNotes();
            return null;
        }
    }


}
