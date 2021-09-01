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
import com.example.submission.adapter.followingAdapter;
import com.example.submission.model.Following;
import com.example.submission.R;
import com.example.submission.databinding.FragmentFollowingBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class followingFragment extends Fragment {

    FragmentFollowingBinding binding;
    com.example.submission.adapter.followingAdapter followingAdapter;
    RecyclerView.LayoutManager layoutManager;
    List<Following> followings;
    Gson gson = new Gson();

    public static followingFragment newInstance(String parameter) {
        Bundle args = new Bundle();
        args.putString("data", parameter);
        followingFragment fragment = new followingFragment();
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
        binding     = DataBindingUtil.inflate(inflater, R.layout.fragment_following, container, false);

        if (getArguments() != null) {
            followings   = gson.fromJson(getArguments().getString("data"), new TypeToken<List<Following>>(){}.getType());
            setData(followings);
        }

        return binding.getRoot();
    }

    public void setData(List<Following> followings) {
        binding.rvData.setHasFixedSize(true);
        layoutManager       = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        binding.rvData.setLayoutManager(layoutManager);
        binding.rvData.setItemAnimator(new DefaultItemAnimator());
        binding.rvData.setNestedScrollingEnabled(true);
        followingAdapter = new followingAdapter(getContext(),followings);
        binding.rvData.setAdapter(followingAdapter);
        followingAdapter.notifyDataSetChanged();
    }
}