package com.example.myproject.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface MemoriesDao {

    @Insert
    public Long insertMemory(MemoryClass memory);

    @Query("SELECT * FROM memories")
    public MemoryClass[] loadAllMemories();

    @Query("SELECT * FROM memories WHERE id == :memoryID")
    public MemoryClass loadMemoryWithID(Long memoryID);

    @Query("SELECT * FROM memories ORDER BY id desc LIMIT 5")
    public MemoryClass[] loadLast5Memories();
}
