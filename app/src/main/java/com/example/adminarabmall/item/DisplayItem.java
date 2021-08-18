package com.example.adminarabmall.item;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.adminarabmall.category.Category;
import com.example.adminarabmall.databinding.ActivityDisplayItemBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class DisplayItem extends AppCompatActivity {
    ActivityDisplayItemBinding binding;
    FirebaseFirestore fb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDisplayItemBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        fb = FirebaseFirestore.getInstance();

        fb.collection("Categories").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                ArrayList<Category> categories = (ArrayList<Category>) queryDocumentSnapshots.toObjects(Category.class);
                setupViewPagerWithTabs(categories);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(DisplayItem.this, "فشل في جلب البيانات", Toast.LENGTH_SHORT).show();

            }
        });



    }

    private void setupViewPagerWithTabs(ArrayList<Category> categories) {

        PagerAdapter adapter = new PagerAdapter(this, categories);
        binding.displayItemPager.setAdapter(adapter);
        new TabLayoutMediator(binding.displayItemTable, binding.displayItemPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(categories.get(position).getName());
            }
        }).attach();
    }
}