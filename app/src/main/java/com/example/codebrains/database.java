package com.example.codebrains;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class database extends SQLiteOpenHelper {
    public static final String DB_NAME="Codebrains";
    public static final int version =1;
    public database(@Nullable Context context) {
        super(context, DB_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
      db.execSQL("CREATE TABLE Clients (\n" +
              "    ClientID INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
              "    FullName TEXT NOT NULL,\n" +
              "    Email TEXT UNIQUE NOT NULL,\n" +
              "    Password TEXT NOT NULL,\n" +
              "    Country TEXT,\n" +
              "    DateOfBirth DATE,\n" +
              "    Address TEXT,\n" +
              "    PhoneNumber TEXT,\n" +
              "    CreatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP\n" +
              ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
