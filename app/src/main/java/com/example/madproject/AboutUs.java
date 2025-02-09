package com.example.madproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class AboutUs extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    // Latitude and Longitude for AI PETS GROUP SDN BHD
    private static final LatLng AI_PETS_GROUP_LOCATION = new LatLng(1.556736, 103.763200);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_about_us);

// Initialize the map fragment
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add marker for AI PETS GROUP SDN BHD
        mMap.addMarker(new MarkerOptions()
                .position(AI_PETS_GROUP_LOCATION)
                .title("AI PETS GROUP SDN BHD")
                .snippet("45, Jalan Austin Height 8/1, Taman Mount Austin, 81100 Johor Bahru, Johor"));

        // Move and zoom camera to the location
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(AI_PETS_GROUP_LOCATION, 15));

        // Open Google Maps app when the map is clicked
        mMap.setOnMapClickListener(latLng -> openGoogleMaps(AI_PETS_GROUP_LOCATION));
    }

    private void openGoogleMaps(LatLng latLng) {
        // Construct the geo URI
        String uri = "geo:" + latLng.latitude + "," + latLng.longitude + "?q=" + Uri.encode("AI PETS GROUP SDN BHD");
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        intent.setPackage("com.google.android.apps.maps"); // Open in Google Maps app if installed

        // Check if Google Maps app is available
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            // Fall back to a web browser
            Uri webUri = Uri.parse("https://www.google.com/maps/search/?api=1&query=" + latLng.latitude + "," + latLng.longitude);
            Intent webIntent = new Intent(Intent.ACTION_VIEW, webUri);
            startActivity(webIntent);
        }
    }
}
