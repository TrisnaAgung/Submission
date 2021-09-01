package com.example.submission.repository;

import android.content.ContentResolver;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.submission.database.DaoSession;
import com.example.submission.database.Favorite;
import com.example.submission.database.FavoriteContentProvider;
import com.example.submission.database.FavoriteDao;
import com.example.submission.utility.appController;
import java.util.List;

public class favoriteRepository {

    private final Context context;
    private DaoSession daoSession;

    public favoriteRepository(Context context) {
        this.context    = context;
        daoSession   = ((appController) this.context).getDaoSession();
    }

    public LiveData<List<Favorite>> getFavorite() {
        MutableLiveData<List<Favorite>> data    = new MutableLiveData<>();
        FavoriteDao favoriteDao                 = daoSession.getFavoriteDao();
        data.setValue(favoriteDao.queryBuilder().list());
        return data;
    }

//    public LiveData<List<Favorite>> getFavorites() {
//        MutableLiveData<List<Favorite>> data    = new MutableLiveData<>();
//        context.getContentResolver().query()
//        data.setValue(favoriteDao.queryBuilder().list());
//    }

}
