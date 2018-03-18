package com.example.dingjie.elderlauncher;

/**
 * Created by dingjie on 2018/3/18.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by user on 2017/11/26.
 */


public class MySQLite extends SQLiteOpenHelper {
    final int addType = 1;
    final int deleteType = 2;
    public MySQLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE  TABLE main.misscall " +
                "(_id INTEGER PRIMARY KEY  NOT NULL , " +
                "number VARCHAR, " +
                "time INTEGER)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public void add(String number) {
        SQLiteDatabase db = this.getWritableDatabase();

        if(!contain(number,addType)) {
            ContentValues values = new ContentValues();
            values.put("number", number);
            values.put("time", 1);
            long id = db.insert("main.misscall", null, values);
            Log.d("ADD", id + "");

        }
        db.close();

    }
    public boolean contain(String number,int type){
        String selectQuery = "SELECT * FROM " + "main.misscall";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor!=null && cursor.moveToFirst()) {
            do {
                if(number.equals(cursor.getString(1))){
                    if(type == addType) {
                        int time = cursor.getInt(2);
                        ContentValues cv = new ContentValues();
                        Log.d("BEFORE", String.valueOf(time));
                        time++;
                        cv.put("time", time);

                        db.update("main.misscall", cv, "number = ?", new String[]{number});
                        Log.d("AFTER", String.valueOf(cursor.getInt(2)));
                        Log.d("UPDATE", number);

                    }
                    return true;
                }

            } while (cursor.moveToNext());
        }
// return contact list

        return false;
    }
    public boolean delete(String number){
        if(contain(number,deleteType)) {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete("main.misscall", "number = ?", new String[]{number});
            return true;
        }else{
            return false;
        }

    }
}
