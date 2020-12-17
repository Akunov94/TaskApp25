package com.geektech.taskapp25.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.geektech.taskapp25.Note;

@Database(entities = {Note.class},version = 1)
public abstract class AppDataBase extends RoomDatabase {
    public abstract NoteDao noteDao();

}
