package com.example.submission.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.submission.database.DaoSession;
import com.example.submission.database.Favorite;
import com.example.submission.database.FavoriteDao;
import com.example.submission.model.Detail;
import com.example.submission.model.DetailUser;
import com.example.submission.model.Followers;
import com.example.submission.model.Following;
import com.example.submission.retrofit.ApiInterface;
import com.example.submission.retrofit.ServiceGenerator;
import com.example.submission.utility.appController;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class detailRepository {
    private final Context context;
    private DaoSession daoSession;
    private MutableLiveData<Boolean> isLoading  = new MutableLiveData<>();
    private MutableLiveData<Detail> data        = new MutableLiveData<>();
    private Detail detail                       = new Detail();

    public detailRepository(Context context) {
        this.context    = context;
        daoSession   = ((appController) this.context).getDaoSession();
    }

    public LiveData<Detail> get(String username, long id) {
        ApiInterface apiInterface       = ServiceGenerator.createService(ApiInterface.class);
        List<Observable<?>> requests    = new ArrayList<>();
        requests.add(apiInterface.getDetail(username));
        requests.add(apiInterface.getFollowers(username));
        requests.add(apiInterface.getFollowing(username));

        Observable.zip(
                requests,
                new Function<Object[], Object>() {
                    @Override
                    public Object apply(Object[] objects) throws Exception {
                        detail.setDetailUser(DetailUser.class.cast(objects[0]));
                        detail.setFollowers((List<Followers>) objects[1]);
                        detail.setFollowing((List<Following>) objects[2]);
                        return new Object();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> isLoading.setValue(true))
                .doOnTerminate(() -> {
                    isLoading.setValue(false);
                })
                .subscribe(
                        new Consumer<Object>() {
                            @Override
                            public void accept(Object o) throws Exception {
                                detail.setFavorite(checkFavorite(id));
                                data.setValue(detail);
                            }
                        },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable e) throws Exception {
                            }
                        }
                );

        return data;
    }

    public LiveData<Boolean> getLoading() {
        return isLoading;
    }

    public Boolean insertFavorite(Favorite favorite) {
        FavoriteDao favoriteDao     = daoSession.getFavoriteDao();
        favoriteDao.insertOrReplace(favorite);
        return true;
    }

    public Boolean deleteFavorite(Favorite favorite) {
        FavoriteDao favoriteDao     = daoSession.getFavoriteDao();
        favoriteDao.delete(favorite);
        return true;
    }

    public Boolean checkFavorite(long id) {
        Boolean check               = false;
        FavoriteDao favoriteDao     = daoSession.getFavoriteDao();
        List<Favorite> favorites    = new ArrayList<>();
        favorites                   = favoriteDao.queryBuilder().where(FavoriteDao.Properties.Id.eq(id)).list();
        if (favorites.size() > 0) {
            check = true;
        }

        return check;
    }
}
