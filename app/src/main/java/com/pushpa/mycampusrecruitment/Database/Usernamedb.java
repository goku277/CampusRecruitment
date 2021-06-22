package com.pushpa.mycampusrecruitment.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Usernamedb extends SQLiteOpenHelper {
    public Usernamedb(@Nullable Context context) {
        super(context, "usernamedb", null, 1);
    }

    public void insertData(String username11) {
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues cv= new ContentValues();
        cv.put("username1", username11);
        db.insert("username",null,cv);
        db.close();
    }

    public void delete() {
        SQLiteDatabase db= this.getWritableDatabase();
        db.delete("username", null, null);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String query= "create table username(username1 text);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
