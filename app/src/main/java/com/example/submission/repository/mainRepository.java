package com.example.submission.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.submission.model.User;
import com.example.submission.retrofit.ApiInterface;
import com.example.submission.retrofit.ServiceGenerator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class mainRepository {
    private ApiInterface apiInterface;
    private final Context context;
    private MutableLiveData<Boolean> isLoading  = new MutableLiveData<>();

    public mainRepository(Context context) {
        this.context    = context;
        apiInterface    = ServiceGenerator.createService(ApiInterface.class);
    }

    public LiveData<User> getUsers(String username) {
        isLoading.setValue(true);
        final MutableLiveData<User> data    = new MutableLiveData<>();
        apiInterface.getUsers(username).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                isLoading.setValue(false);
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                } else {
                    data.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                isLoading.setValue(false);
                data.setValue(null);
                t.printStackTrace();
            }
        });

        return data;
    }

    public LiveData<Boolean> getLoading() {
        return isLoading;
    }
}
