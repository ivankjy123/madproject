package com.example.madproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class UserDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "UserDB";
    private static final int DATABASE_VERSION = 2;

    // Table name
    public static final String TABLE_USER = "newuser";

    // Column names
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_USERNAME = "username";  // New column for username
    public static final String COLUMN_IC = "icno";
    public static final String COLUMN_PASSWORD = "password";

    // SQL query to create the table
    public UserDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USER + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_EMAIL + " TEXT, " +
                COLUMN_USERNAME + " TEXT, " +  // Make sure this is added
                COLUMN_IC + " INTEGER, " +
                COLUMN_PASSWORD + " TEXT)";
        db.execSQL(CREATE_USERS_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop the old table if it exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);

        // Create the new table with the added 'username' column
        onCreate(db);
    }


    // Check if the email already exists
    public boolean doesEmailExist(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USER, null, COLUMN_EMAIL + "=?", new String[]{email}, null, null, null);
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    // Check if the IC number already exists
    public boolean doesIcNoExist(int icno) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USER, null, COLUMN_IC + "=?", new String[]{String.valueOf(icno)}, null, null, null);
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    // Add a new user with username, email, IC number, and password
    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, user.getUsername());  // Insert username
        values.put(COLUMN_EMAIL, user.getEmail());
        values.put(COLUMN_IC, user.getIcno());
        values.put(COLUMN_PASSWORD, user.getPassword());

        long result = db.insert(TABLE_USER, null, values);
        if (result == -1) {
            Log.e("UserDatabaseHelper", "Error inserting user data");
        } else {
            Log.d("UserDatabaseHelper", "User inserted successfully: " + user.getUsername());
        }

        db.close();
    }


    // Check if the email and password match an existing user
    public boolean isUserValid(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USER, null, COLUMN_EMAIL + "=? AND " + COLUMN_PASSWORD + "=?",
                new String[]{email, password}, null, null, null);

        boolean isValid = cursor.getCount() > 0;  // If count > 0, user exists with the given email and password
        cursor.close();
        return isValid;
    }
    // Retrieve the username by email
    public String getUsernameByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USER, new String[]{COLUMN_USERNAME}, COLUMN_EMAIL + "=?",
                new String[]{email}, null, null, null);

        String username = null;  // Initialize username as null

        if (cursor != null && cursor.moveToFirst()) {
            int usernameIndex = cursor.getColumnIndex(COLUMN_USERNAME);
            if (usernameIndex == -1) {
                Log.e("Database", "Column not found: " + COLUMN_USERNAME);
            } else {
                // Retrieve the username
                username = cursor.getString(usernameIndex);
                Log.d("Database", "Username found: " + username);
            }
            cursor.close();
        }

        // Return the username if found, or null if not
        return username;
    }



}

