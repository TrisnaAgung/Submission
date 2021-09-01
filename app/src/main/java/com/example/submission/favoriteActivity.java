package com.example.submission;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.submission.adapter.favoriteAdapter;
import com.example.submission.database.Favorite;
import com.example.submission.databinding.ActivityFavoriteBinding;
import com.example.submission.viewModel.favoriteViewModel;

import java.util.List;

public class favoriteActivity extends AppCompatActivity {

    ActivityFavoriteBinding binding;
    com.example.submission.viewModel.favoriteViewModel favoriteViewModel;
    LinearLayoutManager layoutManager;
    com.example.submission.adapter.favoriteAdapter favoriteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        binding             = DataBindingUtil.setContentView(this, R.layout.activity_favorite);
        favoriteViewModel   = ViewModelProviders.of(this).get(com.example.submission.viewModel.favoriteViewModel.class);
        binding.rvFavorite.setHasFixedSize(true);
        layoutManager       = new LinearLayoutManager(favoriteActivity.this, LinearLayoutManager.VERTICAL, false);
        binding.rvFavorite.setLayoutManager(layoutManager);
        binding.rvFavorite.setItemAnimator(new DefaultItemAnimator());
        binding.rvFavorite.setNestedScrollingEnabled(true);

        getSupportActionBar().setTitle(getString(R.string.app_favorite));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

    private void initUI() {
        favoriteViewModel.getListFavorite().observe(this, new Observer<List<Favorite>>() {
            @Override
            public void onChanged(List<Favorite> favorites) {
                favoriteAdapter     = new favoriteAdapter(favoriteActivity.this,favorites);
                favoriteAdapter.setOnItemClickListener(new favoriteAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, Favorite obj, int position) {
                        Intent intent   = new Intent(favoriteActivity.this, detailActivity.class);
                        intent.putExtra("username", obj.getLogin());
                        intent.putExtra("id", Math.toIntExact(obj.getId()));
                        startActivity(intent);
                    }
                });
                binding.rvFavorite.setAdapter(favoriteAdapter);
                favoriteAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onResume() {
        initUI();
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}