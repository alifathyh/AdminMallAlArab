package com.example.adminarabmall.item;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminarabmall.OnViewItemClickListener;
import com.example.adminarabmall.category.Category;
import com.example.adminarabmall.category.CategoryAdapter;
import com.example.adminarabmall.databinding.CustomCategoryItemBinding;
import com.example.adminarabmall.databinding.CustomItemItemBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemHolder> {
    ArrayList<Item> items;
    private OnViewItemClickListener listener;

    public ItemAdapter(ArrayList<Item> items, OnViewItemClickListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CustomItemItemBinding binding = CustomItemItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ItemHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        Item item = items.get(position);
        holder.binding.itemName.setText(item.getName());
        holder.binding.itemPrice.setText(String.valueOf(item.getPrice()));
        holder.binding.itemName.setTag(item.getDocId());
        Picasso.get().load(item.getImage()).into(holder.binding.itemImg);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ItemHolder extends RecyclerView.ViewHolder{

        CustomItemItemBinding binding;
        Item item;
        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            binding = CustomItemItemBinding.bind(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String id = String.valueOf(binding.itemName.getTag());
                    listener.onItemClick(id);
                }
            });
        }
    }
}

