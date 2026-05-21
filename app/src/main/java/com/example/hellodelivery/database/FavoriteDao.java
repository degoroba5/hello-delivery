package com.example.hellodelivery.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

@Dao
public interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addFavorite(FavoriteEntity favorite);

    @Delete
    void removeFavorite(FavoriteEntity favorite);

    @Query("SELECT * FROM favorites")
    LiveData<List<FavoriteEntity>> getAllFavorites();

    @Query("SELECT * FROM favorites WHERE productId = :productId LIMIT 1")
    FavoriteEntity getFavoriteById(String productId);
}
