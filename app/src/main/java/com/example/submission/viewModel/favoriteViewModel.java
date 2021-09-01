package com.example.submission.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.submission.database.Favorite;
import com.example.submission.repository.favoriteRepository;

import java.util.List;


public class favoriteViewModel extends AndroidViewModel {

    com.example.submission.repository.favoriteRepository favoriteRepository;

    public favoriteViewModel(@NonNull Application application) {
        super(application);

        favoriteRepository  = new favoriteRepository(getApplication());
    }

    public LiveData<List<Favorite>> getListFavorite() {
        return favoriteRepository.getFavorite();
    }
}
