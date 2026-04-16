package com.svalero.bstronggym.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "workouts")
public class Workout {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;
    private String description;
    private int durationMinutes;
    private String date;
    private int calories;

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public int getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(int durationMinutes) { this.durationMinutes = durationMinutes; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public int getCalories() { return calories; }
    public void setCalories(int calories) { this.calories = calories; }
}