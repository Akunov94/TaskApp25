package com.geektech.taskapp25;

import android.app.Application;

import androidx.room.Room;

import com.geektech.taskapp25.room.AppDataBase;

public class App extends Application {
    public static AppDataBase dataBase;

    @Override
    public void onCreate() {
        super.onCreate();
        dataBase = Room
                .databaseBuilder(this, AppDataBase.class, "database")
                .allowMainThreadQueries()
                .build();
    }
    public static AppDataBase getDataBase(){
        return dataBase;
    }
}
