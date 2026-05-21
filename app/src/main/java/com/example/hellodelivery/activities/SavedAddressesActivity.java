package com.example.hellodelivery.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.hellodelivery.adapters.AddressAdapter;
import com.example.hellodelivery.database.AddressEntity;
import com.example.hellodelivery.database.AppDatabase;
import com.example.hellodelivery.databinding.ActivitySavedAddressesBinding;
import java.util.ArrayList;
import java.util.concurrent.Executors;

public class SavedAddressesActivity extends AppCompatActivity {

    private ActivitySavedAddressesBinding binding;
    private AddressAdapter adapter;

    private final ActivityResultLauncher<Intent> addressPickerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    String address = result.getData().getStringExtra("address");
                    double lat = result.getData().getDoubleExtra("lat", 0);
                    double lng = result.getData().getDoubleExtra("lng", 0);
                    saveAddressToDb("Other", address, lat, lng);
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySavedAddressesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupToolbar();
        setupRecyclerView();
        observeAddresses();

        binding.btnAddNewAddress.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddressPickerActivity.class);
            addressPickerLauncher.launch(intent);
        });
    }

    private void setupToolbar() {
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        binding.toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void setupRecyclerView() {
        adapter = new AddressAdapter(new ArrayList<>(), new AddressAdapter.OnAddressClickListener() {
            @Override
            public void onAddressClick(AddressEntity address) {
                // Return result if requested, or just show details
            }

            @Override
            public void onDeleteClick(AddressEntity address) {
                deleteAddress(address);
            }
        });
        binding.addressesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.addressesRecyclerView.setAdapter(adapter);
    }

    private void observeAddresses() {
        AppDatabase.getInstance(this).addressDao().getAllAddresses().observe(this, addresses -> {
            if (addresses != null) {
                adapter.updateList(addresses);
            }
        });
    }

    private void saveAddressToDb(String title, String address, double lat, double lng) {
        AddressEntity newAddress = new AddressEntity(title, address, lat, lng);
        Executors.newSingleThreadExecutor().execute(() -> {
            AppDatabase.getInstance(this).addressDao().addAddress(newAddress);
        });
    }

    private void deleteAddress(AddressEntity address) {
        Executors.newSingleThreadExecutor().execute(() -> {
            AppDatabase.getInstance(this).addressDao().deleteAddress(address);
        });
    }
}
