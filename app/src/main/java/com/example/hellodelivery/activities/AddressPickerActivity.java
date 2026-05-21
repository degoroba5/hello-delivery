package com.example.hellodelivery.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import com.example.hellodelivery.R;
import com.example.hellodelivery.databinding.ActivityAddressPickerBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class AddressPickerActivity extends AppCompatActivity implements OnMapReadyCallback {

    private ActivityAddressPickerBinding binding;
    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationClient;
    private LatLng selectedLatLng;
    private String selectedAddressStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddressPickerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.picker_map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        binding.btnBackPicker.setOnClickListener(v -> onBackPressed());
        binding.btnConfirmAddress.setOnClickListener(v -> confirmAddress());
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnCameraIdleListener(() -> {
            selectedLatLng = mMap.getCameraPosition().target;
            getAddressFromLatLng(selectedLatLng);
        });

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            fusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
                if (location != null) {
                    LatLng currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15f));
                }
            });
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
        }
    }

    private void getAddressFromLatLng(LatLng latLng) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                selectedAddressStr = address.getAddressLine(0);
                binding.tvSelectedAddress.setText(selectedAddressStr);
            }
        } catch (IOException e) {
            e.printStackTrace();
            binding.tvSelectedAddress.setText("Address not found");
        }
    }

    private void confirmAddress() {
        if (selectedAddressStr == null) {
            Toast.makeText(this, "Please select a location", Toast.LENGTH_SHORT).show();
            return;
        }

        String extra = binding.etAddressExtra.getText().toString().trim();
        String fullAddress = selectedAddressStr;
        if (!extra.isEmpty()) {
            fullAddress = extra + ", " + selectedAddressStr;
        }

        Intent resultIntent = new Intent();
        resultIntent.putExtra("address", fullAddress);
        resultIntent.putExtra("lat", selectedLatLng.latitude);
        resultIntent.putExtra("lng", selectedLatLng.longitude);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            onMapReady(mMap);
        }
    }
}
