package com.geektech.taskapp25.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.geektech.taskapp25.Note;

import java.util.List;

@Dao
public interface NoteDao {
    @Query("SELECT * FROM note ORDER BY CreateAt DESC")
    List<Note> getAll();

    @Insert
    public void insert(Note note);

    @Delete
    public void delete(Note note);

    @Update
    public void update(Note note);

}
