package com.solupark.app;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyBd extends SQLiteOpenHelper {
    public MyBd(Context context){
        super(context, "DBRAYON2", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table tablerayon (_id integer primary key autoincrement, rayon integer)" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public int LectureRayon (){
        Cursor cursor;
        cursor = getReadableDatabase().rawQuery("SELECT rayon from tablerayon", null);
        if (cursor.getCount() == 0) return 0;
        cursor.moveToFirst();
        return (cursor.getInt(0));
    }

    public void writeRayon (int r){
        if (tablevide()){
        getWritableDatabase().execSQL("INSERT INTO tablerayon (_id, rayon) VALUES ("+ 1 +"," + r +")");
        }
        else getWritableDatabase().execSQL("UPDATE tablerayon SET rayon=" + r +" WHERE _id = 1");
    }

    public boolean tablevide(){
        Cursor cursor;
        cursor = getReadableDatabase().rawQuery("SELECT rayon from tablerayon", null);
        if (cursor.getCount() == 0){
            return true;
        }
        else return false;
    }
}
