package com.example.loginregestration;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "UserDatabase.db";
    private static final int DATABASE_VERSION = 1;
    private static final String USERS_TABLE = "users";

    public DbHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + USERS_TABLE + " (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT, gender TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USERS_TABLE);
        onCreate(db);
    }

    public boolean InsertUser(String username, String password, String gender) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        contentValues.put("gender", gender);

        // Insert the data into the database and check if it was successful
        long result = db.insert(USERS_TABLE, null, contentValues);
        return result != -1;  // Returns true if insert was successful, false otherwise
    }


    // Method to check if username and password match and return gender if valid
    public String getUserGender(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT gender FROM " + USERS_TABLE + " WHERE username = ? AND password = ?", new String[]{username, password});

        if (cursor.moveToFirst()) {
            String gender = cursor.getString(0);
            cursor.close();
            return gender; // Returns gender ("Male" or "Female")
        } else {
            cursor.close();
            return null; // Invalid credentials
        }
    }
}
