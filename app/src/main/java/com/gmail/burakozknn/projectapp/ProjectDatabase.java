package com.gmail.burakozknn.projectapp;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.security.Policy;

@Database(entities = {Project.class}, version = 2)
public abstract class ProjectDatabase extends RoomDatabase {

    private static ProjectDatabase instance;

    public abstract ProjectDao projectDao();

    public static synchronized ProjectDatabase getInstance(Context context) {

        if(instance==null) {

            instance = Room.databaseBuilder(context.getApplicationContext(),
                    ProjectDatabase.class,"project_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();

        }
        return instance;

    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void,Void,Void> {

        private ProjectDao projectDao;

        private PopulateDbAsyncTask(ProjectDatabase db) {
            projectDao = db.projectDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            projectDao.insert(new Project("Android","a",40));
            projectDao.insert(new Project("IOS","b",20));

            return null;
        }
    }

}
