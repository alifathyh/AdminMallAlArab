package com.example.adminarabmall.item;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.adminarabmall.OnViewItemClickListener;
import com.example.adminarabmall.databinding.FragmentItemBinding;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class ItemFragment extends Fragment {

    private static final String CAT_NAME = "cat_name";
    private String catName;
    public static final String ITEM_KEY = "item_key";


    public ItemFragment() {
    }

    public static ItemFragment newInstance(String catName) {
        ItemFragment fragment = new ItemFragment();
        Bundle args = new Bundle();
        args.putString(CAT_NAME, catName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            catName = getArguments().getString(CAT_NAME);
        }
    }

    FragmentItemBinding binding;
    FirebaseFirestore fb;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentItemBinding.inflate(inflater);
        fb = FirebaseFirestore.getInstance();

        fb.collection("Items").whereEqualTo("categoryName", catName).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (!value.isEmpty()) {
                    ArrayList<Item> items = (ArrayList<Item>) value.toObjects(Item.class);


                    ItemAdapter adapter = new ItemAdapter(items, new OnViewItemClickListener() {
                        @Override
                        public void onItemClick(String id) {
                            Intent i = new Intent(getContext(), AddItem.class);
                            i.putExtra(ITEM_KEY,id);
                            startActivity(i);
                        }
                    });
                    binding.rv.setAdapter(adapter);
                    binding.rv.setHasFixedSize(true);
                    binding.rv.setLayoutManager(new LinearLayoutManager(getContext()));
                }
            }
        });

        return binding.getRoot();
    }
}