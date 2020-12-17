package com.geektech.taskapp25.interfaces;

import com.geektech.taskapp25.Note;

public interface OnItemClickListener {
    void onClick(int position, Note note);
    void onLongClick(int position,Note note);
}
