package com.example.adminarabmall.orderlist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.adminarabmall.category.Category;
import com.example.adminarabmall.databinding.CustomStatusItemSpBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class StatusSpAdapter extends BaseAdapter {
    ArrayList<Status> statuses;
    @Override
    public int getCount() {
        return statuses.size();
    }

    public StatusSpAdapter(ArrayList<Status> statuses) {
        this.statuses = statuses;
    }

    @Override
    public Status getItem(int i) {
        return statuses.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        CustomStatusItemSpBinding binding = CustomStatusItemSpBinding.inflate(LayoutInflater.from(viewGroup.getContext()));

        Status c = statuses.get(i);
        Picasso.get().load(c.getImage()).into(binding.customStatusItemIv);
        binding.customStatusItemTvName.setText(c.getName());
        return binding.getRoot();
    }
}