package com.example.hellodelivery.viewmodels;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.hellodelivery.database.FavoriteEntity;
import com.example.hellodelivery.repositories.FavoriteRepository;
import java.util.List;

public class FavoriteViewModel extends AndroidViewModel {
    private final FavoriteRepository repository;
    private final LiveData<List<FavoriteEntity>> allFavorites;

    public FavoriteViewModel(@NonNull Application application) {
        super(application);
        repository = new FavoriteRepository(application);
        allFavorites = repository.getAllFavorites();
    }

    public LiveData<List<FavoriteEntity>> getAllFavorites() {
        return allFavorites;
    }

    public void addFavorite(FavoriteEntity favorite) {
        repository.addFavorite(favorite);
    }

    public void removeFavorite(FavoriteEntity favorite) {
        repository.removeFavorite(favorite);
    }

    public void isFavorite(String productId, FavoriteRepository.FavoriteCallback callback) {
        repository.checkIsFavorite(productId, callback);
    }
}
