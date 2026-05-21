package com.example.hellodelivery.repositories;

import android.content.Context;
import androidx.lifecycle.LiveData;
import com.example.hellodelivery.database.AppDatabase;
import com.example.hellodelivery.database.FavoriteDao;
import com.example.hellodelivery.database.FavoriteEntity;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FavoriteRepository {
    private final FavoriteDao favoriteDao;
    private final ExecutorService executor;

    public FavoriteRepository(Context context) {
        favoriteDao = AppDatabase.getInstance(context).favoriteDao();
        executor = Executors.newSingleThreadExecutor();
    }

    public void addFavorite(FavoriteEntity favorite) {
        executor.execute(() -> favoriteDao.addFavorite(favorite));
    }

    public void removeFavorite(FavoriteEntity favorite) {
        executor.execute(() -> favoriteDao.removeFavorite(favorite));
    }

    public LiveData<List<FavoriteEntity>> getAllFavorites() {
        return favoriteDao.getAllFavorites();
    }

    public void checkIsFavorite(String productId, FavoriteCallback callback) {
        executor.execute(() -> {
            FavoriteEntity fav = favoriteDao.getFavoriteById(productId);
            callback.onResult(fav != null);
        });
    }

    public interface FavoriteCallback {
        void onResult(boolean isFavorite);
    }
}
