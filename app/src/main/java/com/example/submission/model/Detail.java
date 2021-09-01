package com.example.submission.model;

import java.util.List;

public class Detail {
    private DetailUser detailUser;
    private List<Followers> followers;
    private List<Following> following;
    private Boolean favorite;

    public DetailUser getDetailUser() {
        return detailUser;
    }

    public void setDetailUser(DetailUser detailUser) {
        this.detailUser = detailUser;
    }

    public List<Followers> getFollowers() {
        return followers;
    }

    public void setFollowers(List<Followers> followers) {
        this.followers = followers;
    }

    public List<Following> getFollowing() {
        return following;
    }

    public void setFollowing(List<Following> following) {
        this.following = following;
    }

    public Boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }
}
