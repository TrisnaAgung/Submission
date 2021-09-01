package com.example.submission.database;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import com.example.submission.database.Favorite;

import com.example.submission.database.FavoriteDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig favoriteDaoConfig;

    private final FavoriteDao favoriteDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        favoriteDaoConfig = daoConfigMap.get(FavoriteDao.class).clone();
        favoriteDaoConfig.initIdentityScope(type);

        favoriteDao = new FavoriteDao(favoriteDaoConfig, this);

        registerDao(Favorite.class, favoriteDao);
    }
    
    public void clear() {
        favoriteDaoConfig.getIdentityScope().clear();
    }

    public FavoriteDao getFavoriteDao() {
        return favoriteDao;
    }

}