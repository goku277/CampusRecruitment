package com.pushpa.mycampusrecruitment.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Universitynamedb extends SQLiteOpenHelper {
    public Universitynamedb(@Nullable Context context) {
        super(context, "universitydb", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query= "create table universityname(university text);";
        db.execSQL(query);
    }

    public void delete() {
        SQLiteDatabase db= this.getWritableDatabase();
        db.delete("universityname", null, null);
    }

    public void insertData(String univer) {
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues cv= new ContentValues();
        cv.put("university", univer);
        db.insert("universityname",null,cv);
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
