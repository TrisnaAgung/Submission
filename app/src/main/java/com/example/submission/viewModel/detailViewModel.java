package com.example.submission.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.submission.database.Favorite;
import com.example.submission.model.Detail;
import com.example.submission.repository.detailRepository;

public class detailViewModel extends AndroidViewModel {

    com.example.submission.repository.detailRepository detailRepository;

    public detailViewModel(@NonNull Application application) {
        super(application);

        detailRepository  = new detailRepository(getApplication());
    }

    public LiveData<Boolean> getLoading() {
        return detailRepository.getLoading();
    }

    public LiveData<Detail> getDetail(String username, long id) {
        return detailRepository.get(username, id);
    }

    public boolean insertFavorite(Favorite favorite) {
        return detailRepository.insertFavorite(favorite);
    }

    public boolean deleteFavorite(Favorite favorite) {
        return detailRepository.deleteFavorite(favorite);
    }

    public boolean checkFavorite(long id) {
        return detailRepository.checkFavorite(id);
    }
}
