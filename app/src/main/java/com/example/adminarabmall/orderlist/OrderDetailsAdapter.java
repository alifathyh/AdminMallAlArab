package com.example.adminarabmall.orderlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminarabmall.databinding.CustomOrderListBinding;
import com.example.adminarabmall.databinding.CustomOrdrsListItemBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OrderDetailsAdapter extends RecyclerView.Adapter<OrderDetailsAdapter.OrderDetailsHolder> {
    List<OrderItem> orders;
    Context context;


    public OrderDetailsAdapter(List<OrderItem> orders) {
        this.orders = orders;
    }

    @NonNull
    @Override
    public OrderDetailsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CustomOrdrsListItemBinding binding = CustomOrdrsListItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new OrderDetailsHolder(binding.getRoot());    }

    @Override
    public void onBindViewHolder(@NonNull OrderDetailsHolder holder, int position) {
        OrderItem order = orders.get(position);
        holder.binding.customOrderItemTvPrice.setText(String.valueOf(order.getItem().getPrice()));
        holder.binding.customOrderItemTvName.setText(order.getItem().getName());
        holder.binding.customOrderItemTvCount.setText(String.valueOf(order.getCount()));
        holder.binding.customOrderItemTvSubTotal.setText(String.valueOf(order.getItem().getPrice()*order.getCount()));
        Picasso.get().load(order.getItem().getImage()).into(holder.binding.customOrderItemIv);

    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    class OrderDetailsHolder extends RecyclerView.ViewHolder{
        CustomOrdrsListItemBinding binding;

        public OrderDetailsHolder(@NonNull View itemView) {
            super(itemView);
            binding = CustomOrdrsListItemBinding.bind(itemView);

        }
    }
}