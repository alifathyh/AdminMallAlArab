package com.example.adminarabmall.category;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.widget.Toast;

import com.example.adminarabmall.databinding.ActivityDisplayCategoriesBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class DisplayCategories extends AppCompatActivity {
    ActivityDisplayCategoriesBinding binding;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDisplayCategoriesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = FirebaseFirestore.getInstance();

        db.collection("Categories").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()) {
                    ArrayList<Category> categories = (ArrayList<Category>) queryDocumentSnapshots.toObjects(Category.class);

                    CategoryAdapter adapter = new CategoryAdapter(categories);
                    binding.rv.setAdapter(adapter);
                    binding.rv.setHasFixedSize(true);
                    binding.rv.setLayoutManager(new LinearLayoutManager(getBaseContext()));

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(DisplayCategories.this, "فشل في عرض البيانات", Toast.LENGTH_SHORT).show();
            }
        });

    }
}