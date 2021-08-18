package com.example.adminarabmall.item;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.adminarabmall.category.Category;
import com.example.adminarabmall.item.ItemFragment;

import java.util.List;

public class PagerAdapter extends FragmentStateAdapter {
    List<Category> categories;

    public PagerAdapter(@NonNull FragmentActivity fragmentActivity, List<Category> categories) {
        super(fragmentActivity);
        this.categories = categories;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        String catName = categories.get(position).getName();
        return ItemFragment.newInstance(catName);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
}
