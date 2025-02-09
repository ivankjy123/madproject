package com.example.madproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PetDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "petDayCare.db";
    private static final int DATABASE_VERSION = 1;

    // Pet Information Table
    public static final String TABLE_PET_INFORMATION = "PetInformation";
    public static final String COLUMN_PET_ID = "id";
    public static final String COLUMN_PET_NAME = "name";
    public static final String COLUMN_PET_TYPE = "type";
    public static final String COLUMN_PET_BREED = "breed";
    public static final String COLUMN_PET_AGE = "age";
    public static final String COLUMN_PET_GENDER = "gender";
    public static final String COLUMN_PET_COLOR = "color";
    public static final String COLUMN_PET_AGGRESSION = "aggression";

    // Owner Information Table
    public static final String TABLE_OWNER_INFORMATION = "OwnerInformation";
    public static final String COLUMN_OWNER_ID = "id";
    public static final String COLUMN_OWNER_NAME = "name";
    public static final String COLUMN_OWNER_CONTACT = "contact_number";
    public static final String COLUMN_OWNER_EMAIL = "email_address";
    public static final String COLUMN_OWNER_EMERGENCY = "emergency_contact";

    // Booking Information Table
    public static final String TABLE_BOOKING_INFORMATION = "BookingInformation";
    public static final String COLUMN_BOOKING_ID = "id";
    public static final String COLUMN_BOOKING_PET_NAME = "pet_name";
    public static final String COLUMN_BOOKING_CHECK_IN_DATE = "check_in_date";
    public static final String COLUMN_BOOKING_CHECK_OUT_DATE = "check_out_date";

    // Constructor
    public PetDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create Pet Information Table
        String CREATE_PET_INFORMATION_TABLE = "CREATE TABLE " + TABLE_PET_INFORMATION + " ("
                + COLUMN_PET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_PET_NAME + " TEXT, "
                + COLUMN_PET_TYPE + " TEXT, "
                + COLUMN_PET_BREED + " TEXT, "
                + COLUMN_PET_AGE + " TEXT, "
                + COLUMN_PET_GENDER + " TEXT, "
                + COLUMN_PET_COLOR + " TEXT, "
                + COLUMN_PET_AGGRESSION + " TEXT)";
        db.execSQL(CREATE_PET_INFORMATION_TABLE);

        // Create Owner Information Table
        String CREATE_OWNER_INFORMATION_TABLE = "CREATE TABLE " + TABLE_OWNER_INFORMATION + " ("
                + COLUMN_OWNER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_OWNER_NAME + " TEXT, "
                + COLUMN_OWNER_CONTACT + " TEXT, "
                + COLUMN_OWNER_EMAIL + " TEXT, "
                + COLUMN_OWNER_EMERGENCY + " TEXT)";
        db.execSQL(CREATE_OWNER_INFORMATION_TABLE);

        // Create Booking Information Table
        String CREATE_BOOKING_INFORMATION_TABLE = "CREATE TABLE " + TABLE_BOOKING_INFORMATION + " ("
                + COLUMN_BOOKING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_BOOKING_PET_NAME + " TEXT, "
                + COLUMN_BOOKING_CHECK_IN_DATE + " TEXT, "
                + COLUMN_BOOKING_CHECK_OUT_DATE + " TEXT)";
        db.execSQL(CREATE_BOOKING_INFORMATION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PET_INFORMATION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OWNER_INFORMATION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKING_INFORMATION);
        onCreate(db);
    }

    // Insert Pet Information
    public void insertPetInformation(String name, String type, String breed, String age, String gender, String color, String aggression) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_PET_NAME, name);
        values.put(COLUMN_PET_TYPE, type);
        values.put(COLUMN_PET_BREED, breed);
        values.put(COLUMN_PET_AGE, age);
        values.put(COLUMN_PET_GENDER, gender);
        values.put(COLUMN_PET_COLOR, color);
        values.put(COLUMN_PET_AGGRESSION, aggression);

        db.insert(TABLE_PET_INFORMATION, null, values);
        db.close();
    }

    // Insert Owner Information
    public void insertOwnerInformation(String name, String contact, String email, String emergency) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_OWNER_NAME, name);
        values.put(COLUMN_OWNER_CONTACT, contact);
        values.put(COLUMN_OWNER_EMAIL, email);
        values.put(COLUMN_OWNER_EMERGENCY, emergency);

        db.insert(TABLE_OWNER_INFORMATION, null, values);
        db.close();
    }

    // Insert Booking Information (Check-in and Check-out dates)
    public void insertBookingInformation(String petName, String checkInDate, String checkOutDate) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_BOOKING_PET_NAME, petName);
        values.put(COLUMN_BOOKING_CHECK_IN_DATE, checkInDate);
        values.put(COLUMN_BOOKING_CHECK_OUT_DATE, checkOutDate);

        db.insert(TABLE_BOOKING_INFORMATION, null, values);
        db.close();
    }

    // Retrieve All Pet Information
    public Cursor getAllPetInformation() {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_PET_INFORMATION;
        return db.rawQuery(selectQuery, null);
    }

    // Retrieve All Booking Information
    public Cursor getAllBookingInformation() {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_BOOKING_INFORMATION;
        return db.rawQuery(selectQuery, null);
    }

    // Update Pet Information
    public int updatePetInformation(int petId, String name, String type, String breed, String age, String gender, String color, String aggression) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_PET_NAME, name);
        values.put(COLUMN_PET_TYPE, type);
        values.put(COLUMN_PET_BREED, breed);
        values.put(COLUMN_PET_AGE, age);
        values.put(COLUMN_PET_GENDER, gender);
        values.put(COLUMN_PET_COLOR, color);
        values.put(COLUMN_PET_AGGRESSION, aggression);

        return db.update(TABLE_PET_INFORMATION, values, COLUMN_PET_ID + " = ?", new String[]{String.valueOf(petId)});
    }

    // Delete Pet Information
    public void deletePetInformation(int petId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PET_INFORMATION, COLUMN_PET_ID + " = ?", new String[]{String.valueOf(petId)});
        db.close();
    }
}
