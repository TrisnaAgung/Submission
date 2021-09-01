package com.example.submission.retrofit;

import com.example.submission.model.DetailUser;
import com.example.submission.model.Followers;
import com.example.submission.model.Following;
import com.example.submission.model.User;
import com.example.submission.utility.Cons;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    String API = Cons.SERVER_URL;

    @GET(API + "search/users")
    Call<User> getUsers(@Query("q") String q);

    @GET(API + "users/{username}")
    Observable<DetailUser> getDetail(@Path("username") String username);

    @GET(API + "users/{username}/followers")
    Observable<List<Followers>> getFollowers(@Path("username") String username);

    @GET(API + "users/{username}/following")
    Observable<List<Following>> getFollowing(@Path("username") String username);
}

