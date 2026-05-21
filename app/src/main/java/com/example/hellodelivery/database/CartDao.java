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
public interface CartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addToCart(CartEntity cartItem);

    @Update
    void updateCart(CartEntity cartItem);

    @Delete
    void removeFromCart(CartEntity cartItem);

    @Query("SELECT * FROM cart_items")
    LiveData<List<CartEntity>> getAllCartItems();

    @Query("DELETE FROM cart_items")
    void clearCart();

    @Query("SELECT * FROM cart_items WHERE productId = :productId LIMIT 1")
    CartEntity getCartItemById(String productId);
}
