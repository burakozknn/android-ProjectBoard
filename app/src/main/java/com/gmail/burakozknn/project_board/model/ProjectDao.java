package com.gmail.burakozknn.project_board.model;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ProjectDao {

    @Insert
    void insert(Project project);

    @Update
    void update(Project project);

    @Delete
    void delete(Project project);

    @Query("DELETE FROM project_table")
    void deleteAllProjects();

    @Query("SELECT * FROM project_table ORDER BY id DESC")
    LiveData<List<Project>> getAllProjects();
}
