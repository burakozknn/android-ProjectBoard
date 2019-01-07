package com.gmail.burakozknn.projectapp;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.UUID;

@Entity(tableName = "project_table")
public class Project {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;

    private String description;

    private int price;

    public Project(String title, String description, int price) {
        this.title = title;
        this.description = description;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }

    public void setId(int id) {
        this.id = id;
    }

}