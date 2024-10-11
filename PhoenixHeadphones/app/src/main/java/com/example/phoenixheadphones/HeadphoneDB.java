package com.example.phoenixheadphones;
import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.phoenixheadphones.dao.HeadphoneDao;
import com.example.phoenixheadphones.entities.Note;


@Database(entities = {Note.class},version = 1)
public abstract class HeadphoneDB extends RoomDatabase {
    public abstract HeadphoneDao headphoneDao();
}

