package com.example.madproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


    public class DayCareDatabaseHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "PetDayCare.db";
        private static final int DATABASE_VERSION = 1;

        private static final String TABLE_NAME = "PetDetails";
        private static final String COL_ID = "ID";
        private static final String COL_PET_NAME = "PetName";
        private static final String COL_PET_AGE = "PetAge";
        private static final String COL_PET_BREED = "PetBreed";
        private static final String COL_OWNER_NAME = "OwnerName";
        private static final String COL_OWNER_CONTACT = "OwnerContact";

        public DayCareDatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                    COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_PET_NAME + " TEXT, " +
                    COL_PET_AGE + " TEXT, " +
                    COL_PET_BREED + " TEXT, " +
                    COL_OWNER_NAME + " TEXT, " +
                    COL_OWNER_CONTACT + " TEXT)";
            db.execSQL(createTable);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }

        public boolean insertPet(String name, String age, String breed, String owner, String contact) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COL_PET_NAME, name);
            values.put(COL_PET_AGE, age);
            values.put(COL_PET_BREED, breed);
            values.put(COL_OWNER_NAME, owner);
            values.put(COL_OWNER_CONTACT, contact);
            long result = db.insert(TABLE_NAME, null, values);
            return result != -1;
        }

        public Cursor getPetDetails(String searchQuery) {
            SQLiteDatabase db = this.getReadableDatabase();
            return db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COL_PET_NAME + " = ?", new String[]{searchQuery});
        }

        public Cursor getAllPets() {
            SQLiteDatabase db = this.getReadableDatabase();
            return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        }

        public boolean deletePet(String id) {
            SQLiteDatabase db = this.getWritableDatabase();
            int result = db.delete(TABLE_NAME, COL_ID + "=?", new String[]{id});
            return result > 0; // Returns true if deleted successfully
        }

        public boolean updatePetDetails(String name, String age, String breed, String owner, String contact, String id) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COL_PET_NAME, name);
            values.put(COL_PET_AGE, age);
            values.put(COL_PET_BREED, breed);
            values.put(COL_OWNER_NAME, owner);
            values.put(COL_OWNER_CONTACT, contact);
            int rowsAffected = db.update(TABLE_NAME, values, COL_ID + " = ?", new String[]{id});
            return rowsAffected > 0;
        }
    }

