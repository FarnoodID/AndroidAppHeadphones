package com.example.phoenixheadphones.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName= "headphones")

public class Note {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="headphone_id")
    int headphoneID;

    @NonNull
    @ColumnInfo(name="headphone_name")
    String headphoneName;


    @NonNull
    @ColumnInfo(name="headphone_note")
    String headphoneNote;



    public Note(int headphoneID) {
        this.headphoneID = headphoneID;
    }

    public Note(@NonNull String headphoneName, String headphoneNote) {
        this.headphoneName = headphoneName;
        this.headphoneNote = headphoneNote;
    }

    public Note(int headphoneID, @NonNull String headphoneName, String headphoneNote) {
        this.headphoneID = headphoneID;
        this.headphoneName = headphoneName;
        this.headphoneNote = headphoneNote;
    }

    public Note(){

    }


    public int getHeadphoneID() {
        return headphoneID;
    }

    public void setHeadphoneID(int headphoneID) {
        this.headphoneID = headphoneID;
    }

    @NonNull
    public String getHeadphoneName() {
        return headphoneName;
    }

    public void setHeadphoneName(@NonNull String headphoneName) {
        this.headphoneName = headphoneName;
    }

    public String getHeadphoneNote() {
        return headphoneNote;
    }

    public void setHeadphoneNote(String headphoneNote) {
        this.headphoneNote = headphoneNote;
    }
}
