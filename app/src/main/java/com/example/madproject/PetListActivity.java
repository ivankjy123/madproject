package com.example.madproject;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class PetListActivity extends AppCompatActivity {

    ListView petListView;
    DayCareDatabaseHelper dbHelper;
    ArrayList<String> petList;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_list);

        petListView = findViewById(R.id.listViewPets);
        dbHelper = new DayCareDatabaseHelper(this);
        petList = new ArrayList<>();

        loadPetData();
    }

    private void loadPetData() {
        Cursor cursor = dbHelper.getAllPets();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String petInfo = "🐾 Name: " + cursor.getString(1) +
                        "\n📅 Age: " + cursor.getString(2) +
                        "\n🐕 Breed: " + cursor.getString(3) +
                        "\n👤 Owner: " + cursor.getString(4) +
                        "\n📞 Contact: " + cursor.getString(5);
                petList.add(petInfo);
            } while (cursor.moveToNext());
            cursor.close();
        } else {
            Toast.makeText(this, "No pet data found", Toast.LENGTH_SHORT).show();
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, petList);
        petListView.setAdapter(adapter);
    }
}
