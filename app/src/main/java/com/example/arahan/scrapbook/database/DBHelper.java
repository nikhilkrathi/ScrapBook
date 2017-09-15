package com.example.arahan.scrapbook.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context applicationcontext) {
        super(applicationcontext, "user.db", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String query;
        query = "CREATE TABLE users_data ( userId INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, email TEXT, hobbies TEXT, best_friend" +
                " TEXT, contact TEXT, crush TEXT)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query;
        query = "DROP TABLE IF EXISTS users";
        db.execSQL(query);
        onCreate(db);
    }

    /**
     * Inserts User into SQLite DB
     * @param queryValues
     */
    public boolean insertUser(HashMap<String, String> queryValues) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
//        values.put("userId", queryValues.get("userId"));
        values.put("name", queryValues.get("name"));
        values.put("email", queryValues.get("email"));
        values.put("hobbies", queryValues.get("hobbies"));
        values.put("best_friend", queryValues.get("best_friend"));
        values.put("contact", queryValues.get("contact"));
        values.put("crush", queryValues.get("crush"));

        long res = database.insert("users_data", null, values);
        database.close();
        if (res == -1)
            return false;
        return true;
    }

    /**
     * Get list of Users from SQLite DB as Array List
     * @return
     */
    public ArrayList<HashMap<String, String>> getAllUsers() {
        ArrayList<HashMap<String, String>> usersList;
        usersList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT  * FROM users_data";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("userId", cursor.getString(0));
                map.put("name", cursor.getString(1));
                map.put("email", cursor.getString(2));
                map.put("hobbies", cursor.getString(3));
                map.put("best_friend", cursor.getString(4));
                map.put("contact", cursor.getString(5));
                map.put("crush", cursor.getString(6));
                usersList.add(map);
            } while (cursor.moveToNext());
        }
        database.close();
        return usersList;
    }
}