package com.example.adminarabmall.orderlist;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;

import com.example.adminarabmall.OnItemClickListener;
import com.example.adminarabmall.R;
import com.example.adminarabmall.databinding.ActivityOrderListBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class OrderList extends AppCompatActivity {
    ActivityOrderListBinding binding;
    FirebaseFirestore fb;
    public static final String ITEM_KEY = "item_key";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        fb = FirebaseFirestore.getInstance();
        fb.collection("Orders").orderBy("time", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                ArrayList<Order> order = (ArrayList<Order>) value.toObjects(Order.class);
                UserInfo(order);
            }
        });

//        ArrayList<Order> order = (ArrayList<Order>) queryDocumentSnapshots.toObjects(Order.class);
//        UserInfo(order);

    }

    private void UserInfo(ArrayList<Order> order) {
        OrderAdapter adapter = new OrderAdapter(order, new OnItemClickListener() {
            @Override
            public void onclickItem(String id) {
                Intent i = new Intent(getBaseContext(), OrderDetails.class);
                i.putExtra(ITEM_KEY, id);
                startActivity(i);
            }
        });
        binding.orderListRv.setAdapter(adapter);
        binding.orderListRv.setHasFixedSize(true);
        binding.orderListRv.setLayoutManager(new LinearLayoutManager(this));
    }


}