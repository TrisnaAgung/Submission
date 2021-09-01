package com.example.submission.utility;

import android.app.Application;
import android.content.Context;

import com.example.submission.database.DaoMaster;
import com.example.submission.database.DaoSession;

public class appController extends Application {
    public static final boolean ENCRYPTED = true;
    private DaoSession daoSession;
    private DaoMaster mDaoMaster;

    @Override
    public void onCreate() {
        super.onCreate();
//        MySQLiteOpenHelper helper = new MySQLiteOpenHelper(this,"githubuser-db",null);
//        Database db = helper.getWritableDb();
//        daoSession = new DaoMaster(db).newSession();
        DaoMaster.OpenHelper helper = new DaoMaster.DevOpenHelper(this, "githubuser-db", null);
        mDaoMaster = new DaoMaster(helper.getWritableDatabase());
        daoSession = mDaoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

}
