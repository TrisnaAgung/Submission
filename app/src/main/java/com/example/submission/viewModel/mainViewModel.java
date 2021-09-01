package com.example.submission.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.submission.model.User;
import com.example.submission.repository.mainRepository;

public class mainViewModel extends AndroidViewModel {

    com.example.submission.repository.mainRepository mainRepository;

    public mainViewModel(@NonNull Application application) {
        super(application);

        mainRepository  = new mainRepository(getApplication());
    }

    public LiveData<Boolean> getLoading() {
        return mainRepository.getLoading();
    }

    public LiveData<User> getUsers(String username) {
        return mainRepository.getUsers(username);
    }
}
