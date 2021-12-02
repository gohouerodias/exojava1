package com.example.fird.Dao;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.fird.entities.Product;

@Database(entities = {Product.class}, version = 1)
public abstract class DataBaseRoom extends RoomDatabase {
    private static DataBaseRoom dataBaseRoom;

    public static DataBaseRoom getInstance(Context context) {
        if(dataBaseRoom==null) {
            dataBaseRoom = Room.databaseBuilder(context,
                    DataBaseRoom.class, "project2021:db").build();
        }
        return dataBaseRoom;
    }
    public abstract ProductRoomDao productRoomDao();

}
