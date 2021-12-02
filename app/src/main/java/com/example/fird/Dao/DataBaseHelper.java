package com.example.fird.Dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.fird.entities.Product;

public class DataBaseHelper extends SQLiteOpenHelper {
    private  static final String DB_Name="projet app";
    private static final int version=1;

    public DataBaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_Name, null, version);
    }


    @Override

    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d("oki","Oncreate avant la creation");
       sqLiteDatabase.execSQL(Product.CREATE_TABLE);
        Log.d("oki","Oncreate apres la creation");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
