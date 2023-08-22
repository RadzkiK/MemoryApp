package com.example.myproject.Database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "memories")
public class MemoryClass {

    @PrimaryKey(autoGenerate = true)
    public int id;

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



}
