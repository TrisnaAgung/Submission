package com.example.submission;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.submission.adapter.detailAdapter;
import com.example.submission.database.DaoSession;
import com.example.submission.database.Favorite;
import com.example.submission.databinding.ActivityDetailBinding;
import com.example.submission.databinding.DetailLayoutBinding;
import com.example.submission.fragment.followerFragment;
import com.example.submission.fragment.followingFragment;
import com.example.submission.model.Detail;
import com.example.submission.model.DetailUser;
import com.example.submission.model.Followers;
import com.google.gson.Gson;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class detailActivity extends AppCompatActivity {

    DetailLayoutBinding binding;
    com.example.submission.viewModel.detailViewModel detailViewModel;
    SweetAlertDialog sweetAlertDialog;
    String username;
    Gson gson = new Gson();
    int id;
    DaoSession daoSession;
    DetailUser detailUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding             = DataBindingUtil.setContentView(this, R.layout.detail_layout);
        detailViewModel     = ViewModelProviders.of(this).get(com.example.submission.viewModel.detailViewModel.class);
        username            = getIntent().getStringExtra("username");
        id                  = getIntent().getIntExtra("id",0);
        sweetAlertDialog    = new SweetAlertDialog(detailActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        sweetAlertDialog.getProgressHelper().setBarColor(Color.parseColor("#000080"));
        sweetAlertDialog.setTitleText("Mohon Tunggu...");
        sweetAlertDialog.setCancelable(false);

        getSupportActionBar().setTitle(getString(R.string.app_detail));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        detailViewModel.getLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    showpDialog();
                } else {
                    hidepDialog();
                }
            }
        });

        initUI(username, Long.valueOf(id));

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (detailUser != null) {
                    if (detailViewModel.checkFavorite(detailUser.getId())) {
                        if (detailViewModel.deleteFavorite(gson.fromJson(gson.toJson(detailUser),Favorite.class))) {
                            binding.fab.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                        }
                    } else {

                        if (detailViewModel.insertFavorite(gson.fromJson(gson.toJson(detailUser),Favorite.class))) {
                            binding.fab.setImageResource(R.drawable.ic_baseline_favorite_24);
                        }
                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_another, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initUI(String username, long id) {

        detailViewModel.getDetail(username, id).observe(this, new Observer<Detail>() {
            @Override
            public void onChanged(Detail detail) {
                if (detail != null) {
                    detailUser  = detail.getDetailUser();
                    binding.parent.setVisibility(View.VISIBLE);
                    binding.dataLayout.tvNama.setText(detail.getDetailUser().getName());
                    binding.dataLayout.tvUsername.setText(detail.getDetailUser().getLogin());
                    binding.dataLayout.tvCompany.setText(detail.getDetailUser().getCompany());
                    binding.dataLayout.tvLocation.setText(detail.getDetailUser().getLocation());
                    binding.dataLayout.tvRepository.setText(String.valueOf(detail.getDetailUser().getPublicRepos()));
                    binding.dataLayout.tvFollowing.setText(String.valueOf(detail.getDetailUser().getFollowing()));
                    binding.dataLayout.tvFollowers.setText(String.valueOf(detail.getDetailUser().getFollowers()));
                    Glide.with(detailActivity.this)
                            .load(detail.getDetailUser().getAvatarUrl())
                            .centerCrop()
                            .into(binding.dataLayout.ivAvatar);
                    detailAdapter adapter = new detailAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
                    adapter.addFrag(followerFragment.newInstance(gson.toJson(detail.getFollowers())),getString(R.string.tab_text_1));
                    adapter.addFrag(followingFragment.newInstance(gson.toJson(detail.getFollowing())),getString(R.string.tab_text_2));
                    binding.dataLayout.vpData.setAdapter(adapter);
                    binding.dataLayout.htabTabs.setupWithViewPager(binding.dataLayout.vpData);
                    if (detail.getFavorite()) {
                        binding.fab.setImageResource(R.drawable.ic_baseline_favorite_24);
                    } else {
                        binding.fab.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                    }
                } else {
                    binding.parent.setVisibility(View.GONE);
                }
            }
        });
    }

    private void showpDialog() {
        if (!sweetAlertDialog.isShowing())
            sweetAlertDialog.show();
    }

    private void hidepDialog() {
        if (sweetAlertDialog.isShowing())
            sweetAlertDialog.dismiss();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}