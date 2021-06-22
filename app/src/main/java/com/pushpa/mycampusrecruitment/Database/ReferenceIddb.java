package com.pushpa.mycampusrecruitment.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ReferenceIddb extends SQLiteOpenHelper {

    Context context;
    public ReferenceIddb(@Nullable Context context) {
        super(context, "referenceiddb", null, 1);
        this.context= context;
    }

    public void insertData(String ref) {
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues cv= new ContentValues();
        cv.put("reference", ref);
        db.insert("referenceid",null,cv);
        db.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query= "create table referenceid(reference text);";
        db.execSQL(query);
    }

    public void delete() {
        SQLiteDatabase db= this.getWritableDatabase();
        db.delete("referenceid", null, null);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}