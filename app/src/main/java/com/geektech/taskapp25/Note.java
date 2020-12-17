package com.geektech.taskapp25;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Note  implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    private  String title;
    private long CreateAt;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getCreateAt() {

        return CreateAt;
    }

    public void setCreateAt(long createAt) {
        CreateAt = createAt;
    }

    public Note(String title, long createAt) {
        this.title = title;
        CreateAt = createAt;
    }

    public Note() {
    }
}
