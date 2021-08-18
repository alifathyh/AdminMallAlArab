package com.example.adminarabmall.orderlist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminarabmall.OnItemClickListener;
import com.example.adminarabmall.databinding.CustomOrderListBinding;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderHolder> {
    ArrayList<Order> orders;
    OnItemClickListener listener;


    public OrderAdapter(ArrayList<Order> orders, OnItemClickListener listener) {
        this.orders = orders;
        this.listener = listener;
    }

    @NonNull
    @Override
    public OrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CustomOrderListBinding binding = CustomOrderListBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new OrderHolder(binding.getRoot());    }

    @Override
    public void onBindViewHolder(@NonNull OrderHolder holder, int position) {
        Order order = orders.get(position);
        holder.binding.applicant.setText(order.getUserName());
        holder.binding.date.setText(order.getDate());
        holder.binding.day.setText(order.getDay());
        holder.binding.time.setText(order.getTime());
        holder.binding.amount.setText(String.valueOf(order.getTotal()));
        holder.binding.status.setText(order.getStatus());
        holder.binding.applicant.setTag(order.getDocId());
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    class OrderHolder extends RecyclerView.ViewHolder{
        CustomOrderListBinding binding;

        public OrderHolder(@NonNull View itemView) {
            super(itemView);
            binding = CustomOrderListBinding.bind(itemView);

            binding.cusromOrderListBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String id = String.valueOf(binding.applicant.getTag());
                    listener.onclickItem(id);
                }
            });
        }
    }
}
