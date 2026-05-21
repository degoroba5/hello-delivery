package com.example.hellodelivery.viewmodels;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.hellodelivery.database.AppDatabase;
import com.example.hellodelivery.database.CartDao;
import com.example.hellodelivery.database.CartEntity;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CartViewModel extends AndroidViewModel {
    private final CartDao cartDao;
    private final ExecutorService executorService;
    private final LiveData<List<CartEntity>> allCartItems;

    public CartViewModel(@NonNull Application application) {
        super(application);
        AppDatabase db = AppDatabase.getInstance(application);
        cartDao = db.cartDao();
        executorService = Executors.newSingleThreadExecutor();
        allCartItems = cartDao.getAllCartItems();
    }

    public LiveData<List<CartEntity>> getAllCartItems() {
        return allCartItems;
    }

    public void addToCart(CartEntity item) {
        executorService.execute(() -> {
            CartEntity existing = cartDao.getCartItemById(item.getProductId());
            if (existing != null) {
                existing.setQuantity(existing.getQuantity() + item.getQuantity());
                cartDao.updateCart(existing);
            } else {
                cartDao.addToCart(item);
            }
        });
    }

    public void updateCart(CartEntity item) {
        executorService.execute(() -> cartDao.updateCart(item));
    }

    public void removeFromCart(CartEntity item) {
        executorService.execute(() -> cartDao.removeFromCart(item));
    }

    public void clearCart() {
        executorService.execute(cartDao::clearCart);
    }
}
