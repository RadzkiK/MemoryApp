package com.example.myproject.Database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "memories")
public class MemoryClass {

    public MemoryClass() {
    }

    public MemoryClass(String title, String description, String metaData, String qoute, String photoPath) {
        this.title = title;
        this.description = description;
        this.metaData = metaData;
        this.qoute = qoute;
        this.photoPath = photoPath;
    }

    public MemoryClass(String title, String description, String photoPath) {
        this.title = title;
        this.description = description;
        this.photoPath = photoPath;
    }

    @PrimaryKey(autoGenerate = true)
    public Long id;

    @ColumnInfo
    public String title;

    @ColumnInfo
    public String description;

    @ColumnInfo
    public String metaData;

    @ColumnInfo
    public String qoute;

    @ColumnInfo
    public String photoPath;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getMetaData() {
        return metaData;
    }

    public String getQoute() {
        return qoute;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public Long getId() {
        return id;
    }
}
