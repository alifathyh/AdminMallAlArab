package com.example.adminarabmall.category;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminarabmall.databinding.CustomCategoryItemBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryHolder> {
        ArrayList<Category> categories;

public CategoryAdapter(ArrayList<Category> categories) {
        this.categories = categories;
        }

@NonNull
@Override
public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    CustomCategoryItemBinding binding = CustomCategoryItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
    return new CategoryHolder(binding.getRoot());        }

@Override
public void onBindViewHolder(@NonNull CategoryHolder holder, int position) {

        Category category = categories.get(position);

        holder.binding.customCategoryItemTvName.setText(category.getName());
        Picasso.get().load(category.getImage()).into(holder.binding.imageView);
        }

@Override
public int getItemCount() {
        return categories.size();
        }

class CategoryHolder extends RecyclerView.ViewHolder{

    CustomCategoryItemBinding binding;
    public CategoryHolder(@NonNull View itemView) {
        super(itemView);
        binding = CustomCategoryItemBinding.bind(itemView);
    }
}
}

