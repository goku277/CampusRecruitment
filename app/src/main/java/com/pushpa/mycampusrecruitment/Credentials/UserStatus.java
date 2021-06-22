package com.pushpa.mycampusrecruitment.Credentials;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class UserStatus extends SQLiteOpenHelper {
    Context context;

    public UserStatus(@Nullable Context context) {
        super(context, "userstatus", null, 1);
        this.context = context;
    }

    public void insertData(String user_status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("userStatus", user_status);
        db.insert("user", null, cv);
        db.close();
    }

    public void delete() {
        String query = "select * from user";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c1 = db.rawQuery(query, null);
        if (c1.getCount() > 0) {
            db.delete("user", null, null);
        } else Toast.makeText(context, " sorry but no data to delete!", Toast.LENGTH_SHORT).show();
        db.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "create table user(userStatus text);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}