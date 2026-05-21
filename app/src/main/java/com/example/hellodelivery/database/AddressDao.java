package com.example.hellodelivery.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface AddressDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addAddress(AddressEntity address);

    @Update
    void updateAddress(AddressEntity address);

    @Delete
    void deleteAddress(AddressEntity address);

    @Query("SELECT * FROM addresses")
    LiveData<List<AddressEntity>> getAllAddresses();
}
