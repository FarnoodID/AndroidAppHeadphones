package com.example.phoenixheadphones.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.phoenixheadphones.entities.Note;

import java.util.List;

@Dao
public interface HeadphoneDao {

    @Insert
    void insertHeadphone(Note note);

    @Update
    void updateHeadphone(Note note);

    @Delete
    void deleteHeadphone(Note note);

    @Query("Select * from headphones")
    List<Note> getAllHeadphones();

}
