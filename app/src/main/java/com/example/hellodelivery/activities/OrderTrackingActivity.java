package com.example.hellodelivery.activities;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.hellodelivery.R;
import com.example.hellodelivery.databinding.ActivityOrderTrackingBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class OrderTrackingActivity extends AppCompatActivity implements OnMapReadyCallback {

    private ActivityOrderTrackingBinding binding;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderTrackingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        binding.btnBack.setOnClickListener(v -> onBackPressed());
        binding.btnCallDriver.setOnClickListener(v -> {
            // Implement call functionality
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // Mock coordinates for store and user
        LatLng storeLocation = new LatLng(9.0300, 38.7400); // Example: Addis Ababa
        LatLng userLocation = new LatLng(9.0350, 38.7500);

        mMap.addMarker(new MarkerOptions().position(storeLocation).title("Store"));
        mMap.addMarker(new MarkerOptions().position(userLocation).title("Your Location"));
        
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 14f));
        
        // In a real app, you would use Socket.IO to update the driver's marker position in real-time
    }
}
