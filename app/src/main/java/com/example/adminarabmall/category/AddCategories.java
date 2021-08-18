package com.example.adminarabmall.category;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.adminarabmall.databinding.ActivityAddCategoriesBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;

public class AddCategories extends AppCompatActivity {
    ActivityAddCategoriesBinding binding;

    private static final int PICK_IMG_REQ_CODE = 1;
    Uri selectedImageUri;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddCategoriesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.categoryImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, PICK_IMG_REQ_CODE);
            }
        });

        binding.categoryBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = binding.categoryName.getText().toString();

                if(TextUtils.isEmpty(name))
                    binding.categoryName.setError("اضف اسم التصنيف");
                if(selectedImageUri == null)
                    Toast.makeText(AddCategories.this, "اصف صورة", Toast.LENGTH_SHORT).show();
                else{
                    String docId = db.collection("Categories").document().getId();
                    Category category = new Category(docId, name, "");
                    uploadImageIntoFirebaseStorage(category);
                }

            }
        });

    }

    private void uploadImageIntoFirebaseStorage(Category category) {
        Calendar calendar = Calendar.getInstance();
        storage.getReference().child("category/"+calendar.getTimeInMillis()).putFile(selectedImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // another request to get image url
                taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        category.setImage(uri.toString());
                        addItemToFirestore(category);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddCategories.this, "خطأ في تحميل الصورة", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addItemToFirestore(Category category) {
        db.collection("Categories").document(category.getDocId()).set(category)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(AddCategories.this, "تم اضافة التصنيف ", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddCategories.this, "فشل اضافة التصنيف", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMG_REQ_CODE && resultCode == RESULT_OK && data != null) {
            Uri u = data.getData();
            int flag = data.getFlags() & (Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            getContentResolver().takePersistableUriPermission(u,flag);
            binding.categoryImg.setImageURI(u);
            selectedImageUri = u;
        }
    }
}