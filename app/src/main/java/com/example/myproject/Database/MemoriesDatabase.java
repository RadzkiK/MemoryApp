package com.example.myproject.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {MemoryClass.class}, version = 1)
public abstract class MemoriesDatabase extends RoomDatabase {

    private static final  String DB_NAME = "memoriesDB";
    private static MemoriesDatabase instance;



    public static synchronized MemoriesDatabase getInstance(Context context) {
        if(instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), MemoriesDatabase.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return instance;
    }
    public abstract MemoriesDao memoriesDao();
}
