package com.example.submission.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.submission.model.Followers;
import com.example.submission.R;

import java.util.ArrayList;
import java.util.List;

public class followersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Followers> items = new ArrayList<>();;
    Context context;

    public followersAdapter(Context context, List<Followers> items) {
        this.context    = context;
        this.items      = items;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_nama;
        public ImageView iv_image;
        public ConstraintLayout parent_layout;

        public OriginalViewHolder(View v) {
            super(v);
            parent_layout   = v.findViewById(R.id.parent_layout);
            tv_nama         = v.findViewById(R.id.tv_nama);
            iv_image        = v.findViewById(R.id.iv_image);
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_follow, parent, false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof OriginalViewHolder) {
            OriginalViewHolder view = (OriginalViewHolder) holder;

            Followers p = items.get(position);

            view.tv_nama.setText(p.getLogin());

            Glide.with(context)
                    .load(p.getAvatarUrl())
                    .centerCrop()
                    .into(view.iv_image);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}