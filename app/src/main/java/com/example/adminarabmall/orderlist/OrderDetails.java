package com.example.adminarabmall.orderlist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.adminarabmall.R;
import com.example.adminarabmall.category.Category;
import com.example.adminarabmall.category.CategorySPAdapter;
import com.example.adminarabmall.databinding.ActivityOrderDetailsBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class OrderDetails extends AppCompatActivity {
    ActivityOrderDetailsBinding binding;
    FirebaseFirestore fb;
    FirebaseStorage storage;
    String id, docId, userId, userName, userPhone, userLocation, time, date, day;
    double total;
    List<OrderItem> orderItems;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        fb = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();

        fillStatusIntoSpinner();


        Intent intent = getIntent();
        id = intent.getStringExtra(OrderList.ITEM_KEY);
        

        fb.collection("Orders").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Order order = documentSnapshot.toObject(Order.class);
                binding.orderDetailsDay.setText(order.getDay());
                binding.orderDetailsDate.setText(order.getDate());
                binding.orderDetailsTime.setText(order.getTime());
                binding.orderDetailsTotal.setText(String.valueOf(order.getTotal()));
                binding.orderDetailsName.setText(order.getUserName());
                binding.orderDetailsLocation.setText(order.getUserLocation());
                binding.orderDetailsPhone.setText(order.getUserPhone());
                orderItems = order.getOrderItems();
                fillorder(orderItems);
                
                
                //**********************************************************************************
                 docId = order.getDocId();
                 userId = order.getUserId();
                 userName = order.getUserName();
                 userPhone = order.getUserPhone();
                 userLocation = order.getUserLocation();
                 time = order.getTime();
                 date = order.getDate();
                 day = order.getDay();
                 total = order.getTotal();
            }
        });
        binding.confirmOeder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status = ((Status) binding.statusSp.getSelectedItem()).getName();
                
                switch (binding.statusSp.getSelectedItemPosition()){
                    case 0:
                        Toast.makeText(OrderDetails.this, "لم يتم تغير الحالة", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Order order1 = new Order(docId, userId, userName, userPhone, userLocation, orderItems,
                                time, date, day, total, status,1);
                        fb.collection("Orders").document(id).set(order1).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(OrderDetails.this, "جاري الارسال", Toast.LENGTH_SHORT).show();
                            }
                        });
                        finish();
                        break;
                    case 2:
                        fb.collection("Orders").document(id).delete();
                        Toast.makeText(OrderDetails.this, "تم التسليم", Toast.LENGTH_SHORT).show();
                        finish();
                }
            }
        });
    }

    private void fillStatusIntoSpinner() {
        fb.collection("Status").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                if(!queryDocumentSnapshots.isEmpty()) {
                    ArrayList<Status> statuses = (ArrayList<Status>) queryDocumentSnapshots.toObjects(Status.class);
                    StatusSpAdapter adapter = new StatusSpAdapter(statuses);
                    binding.statusSp.setAdapter(adapter);
                }
            }
        });
    }

    private void fillorder(List<OrderItem> orderItems) {
        OrderDetailsAdapter adapter = new OrderDetailsAdapter(orderItems);
        binding.orderDetailsRv.setAdapter(adapter);
        binding.orderDetailsRv.setHasFixedSize(true);
        binding.orderDetailsRv.setLayoutManager(new LinearLayoutManager(this));
    }
}
