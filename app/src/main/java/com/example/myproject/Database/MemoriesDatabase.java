package com.example.myproject.Database;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {MemoryClass.class}, version = 2)
public abstract class MemoriesDatabase extends RoomDatabase {

    private static final  String DB_NAME = "memoriesDB";
    private static final Object LOCK = new Object();
    private static final String LOG_TAG = MemoriesDatabase.class.getSimpleName();
    private static MemoriesDatabase instance;



    public static MemoriesDatabase getInstance(Context context) {
        if(instance == null) {
            synchronized (LOCK) {
                Log.d(LOG_TAG, "Creating new database instance");
                instance = Room.databaseBuilder(context.getApplicationContext(), MemoriesDatabase.class, MemoriesDatabase.DB_NAME)
                        .build();
            }
        }
        Log.d(LOG_TAG, "Getting the database instance");

        return instance;
    }
    public abstract MemoriesDao memoriesDao();
}
