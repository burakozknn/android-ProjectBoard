package com.gmail.burakozknn.projectapp;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "project_table")
public class Project {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;

    private int price;

    public Project(String title, int price) {
        this.title = title;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getPrice() {
        return price;
    }

    public void setId(int id) {
        this.id = id;
    }
}
