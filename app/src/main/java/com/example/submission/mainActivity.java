package com.example.submission;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.submission.adapter.userAdapter;
import com.example.submission.database.FavoriteContentProvider;
import com.example.submission.model.ItemsItem;
import com.example.submission.model.User;
import com.example.submission.databinding.ActivityMainBinding;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class mainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    com.example.submission.viewModel.mainViewModel mainViewModel;
    SweetAlertDialog sweetAlertDialog;
    RecyclerView.LayoutManager layoutManager;
    com.example.submission.adapter.userAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding             = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainViewModel       = ViewModelProviders.of(this).get(com.example.submission.viewModel.mainViewModel.class);
        sweetAlertDialog    = new SweetAlertDialog(mainActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        sweetAlertDialog.getProgressHelper().setBarColor(Color.parseColor("#000080"));
        sweetAlertDialog.setTitleText("Mohon Tunggu...");
        sweetAlertDialog.setCancelable(false);
        binding.rvSearch.setHasFixedSize(true);
        layoutManager       = new LinearLayoutManager(mainActivity.this, LinearLayoutManager.VERTICAL, false);
        binding.rvSearch.setLayoutManager(layoutManager);
        binding.rvSearch.setItemAnimator(new DefaultItemAnimator());
        binding.rvSearch.setNestedScrollingEnabled(true);

        mainViewModel.getLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    showpDialog();
                } else {
                    hidepDialog();
                }
            }
        });

        getSupportActionBar().setTitle(getString(R.string.app_title));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        if (searchManager != null) {
            SearchView searchView = (SearchView) (menu.findItem(R.id.search)).getActionView();
            searchView.setMaxWidth(Integer.MAX_VALUE);
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setQueryHint(getResources().getString(R.string.search));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    getUsers(query);
                    return true;
                }
                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.favorite) {
            startActivity(new Intent(mainActivity.this, favoriteActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    private void showpDialog() {
        if (!sweetAlertDialog.isShowing())
            sweetAlertDialog.show();
    }

    private void hidepDialog() {
        if (sweetAlertDialog.isShowing())
            sweetAlertDialog.dismiss();
    }

    private void getUsers(String username) {
        mainViewModel.getUsers(username).observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                userAdapter = new userAdapter(mainActivity.this,user.getItems());
                userAdapter.setOnItemClickListener(new com.example.submission.adapter.userAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, ItemsItem obj, int position) {
                        Intent intent   = new Intent(mainActivity.this, detailActivity.class);
                        intent.putExtra("username", obj.getLogin());
                        intent.putExtra("id", obj.getId());
                        startActivity(intent);
                    }
                });
                binding.rvSearch.setAdapter(userAdapter);
                userAdapter.notifyDataSetChanged();
            }
        });
    }
}