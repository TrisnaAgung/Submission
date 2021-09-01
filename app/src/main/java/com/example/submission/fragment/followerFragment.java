package com.example.submission.fragment;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.submission.adapter.followersAdapter;
import com.example.submission.model.Followers;
import com.example.submission.R;
import com.example.submission.databinding.FragmentFollowerBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class followerFragment extends Fragment {

    FragmentFollowerBinding binding;
    com.example.submission.adapter.followersAdapter followersAdapter;
    RecyclerView.LayoutManager layoutManager;
    List<Followers> followers;
    Gson gson = new Gson();

    public static followerFragment newInstance(String parameter) {
        Bundle args = new Bundle();
        args.putString("data", parameter);
        followerFragment fragment = new followerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding     = DataBindingUtil.inflate(inflater, R.layout.fragment_follower, container, false);

        if (getArguments() != null) {
            followers   = gson.fromJson(getArguments().getString("data"), new TypeToken<List<Followers>>(){}.getType());
            setData(followers);
        }

        return binding.getRoot();
    }

    public void setData(List<Followers> followers) {
        binding.rvData.setHasFixedSize(true);
        layoutManager       = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        binding.rvData.setLayoutManager(layoutManager);
        binding.rvData.setItemAnimator(new DefaultItemAnimator());
        binding.rvData.setNestedScrollingEnabled(true);
        followersAdapter = new followersAdapter(getContext(),followers);
        binding.rvData.setAdapter(followersAdapter);
        followersAdapter.notifyDataSetChanged();
    }
}