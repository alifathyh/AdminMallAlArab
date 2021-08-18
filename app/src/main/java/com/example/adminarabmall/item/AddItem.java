package com.example.adminarabmall.item;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.adminarabmall.R;
import com.example.adminarabmall.category.Category;
import com.example.adminarabmall.category.CategorySPAdapter;
import com.example.adminarabmall.databinding.ActivityAddItemBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;

public class AddItem extends AppCompatActivity {
    ActivityAddItemBinding binding;

    private static final int PICK_IMG_REQ_CODE = 1;
    Uri selectedImageUri;
    String editSelectedImageUri;
    String editItemCategory;
    String editItemQuantity;
    ArrayList<Category> categories;
    String docId;

    Toolbar toolbar;
    FirebaseFirestore fb = FirebaseFirestore.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddItemBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        toolbar = binding.detailsToolbar;
        setSupportActionBar(toolbar);

        fillCategoriesIntoSpinner();

        Intent intent = getIntent();
        docId = intent.getStringExtra(ItemFragment.ITEM_KEY);

        if(docId != null){
            disableFields();
            displayDetailsItem();
        }
        else {
            enableFields();
            clearFields();
        }

        binding.addItemImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, PICK_IMG_REQ_CODE);
            }
        });

//        binding.itemBtnSave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String nameItem = binding.addItemName.getText().toString();
//                String description = binding.itemDescription.getText().toString();
//                Double price = Double.parseDouble(binding.addItemPrice.getText().toString());
//                String itemCategory = ((Category)binding.itemSp.getSelectedItem()).getName();
//
//                String docId = fb.collection("Items").document().getId();
//
//                Item item = new Item(docId,nameItem,description,price,itemCategory,"");
//                uploadImageIntoFirebaseStorage(item);
//
//            }
//        });
    }

    private void displayDetailsItem() {
        fb.collection("Items").document(docId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Item item = documentSnapshot.toObject(Item.class);
                Picasso.get().load(item.getImage()).into(binding.addItemImg);
                editSelectedImageUri = item.getImage();
                binding.itemDescription.setText(item.getDescription());
                binding.addItemPrice.setText(String.valueOf(item.getPrice()));
                binding.addItemName.setText(item.getName());
                binding.addItemCategory.setText(item.getCategoryName());
                editItemCategory = item.getCategoryName();
                binding.addItemQuantity.setText(item.getQuantity());
                binding.addItemTheCondition.setText(item.getTheCondition());
            }
        });
    }

    private void clearFields() {
        binding.addItemImg.setImageURI(null);
        binding.addItemPrice.setText("");
        binding.addItemName.setText("");
        binding.itemDescription.setText("");
    }

    private void enableFields() {
//        binding.itemBtnSave.setVisibility(View.VISIBLE);
        binding.addItemCategory.setVisibility(View.GONE);
        binding.addItemCategory1.setVisibility(View.GONE);
        binding.addItemQuantity.setVisibility(View.GONE);
        binding.addItemQuantity1.setVisibility(View.GONE);
        binding.addItemTheCondition.setVisibility(View.GONE);
        binding.addItemTheCondition1.setVisibility(View.GONE);
        binding.itemSp.setVisibility(View.VISIBLE);
        binding.quantitySp.setVisibility(View.VISIBLE);
        binding.theConditionSp.setVisibility(View.VISIBLE);
        binding.itemDescription.setEnabled(true);
        binding.addItemName.setEnabled(true);
        binding.addItemPrice.setEnabled(true);
        binding.itemSp.setEnabled(true);
        binding.quantitySp.setEnabled(true);
        binding.theConditionSp.setEnabled(true);
        binding.addItemImg.setEnabled(true);
    }

    private void disableFields() {
        binding.addItemCategory.setVisibility(View.VISIBLE);
        binding.addItemCategory1.setVisibility(View.VISIBLE);
        binding.addItemQuantity.setVisibility(View.VISIBLE);
        binding.addItemQuantity1.setVisibility(View.VISIBLE);
        binding.addItemTheCondition.setVisibility(View.VISIBLE);
        binding.addItemTheCondition1.setVisibility(View.VISIBLE);
        binding.itemSp.setVisibility(View.GONE);
        binding.quantitySp.setVisibility(View.GONE);
        binding.theConditionSp.setVisibility(View.GONE);
        binding.itemDescription.setEnabled(false);
        binding.addItemName.setEnabled(false);
        binding.addItemPrice.setEnabled(false);
        binding.addItemImg.setEnabled(false);
        binding.addItemCategory.setEnabled(false);
        binding.addItemQuantity.setEnabled(false);
        binding.addItemTheCondition.setEnabled(false);
    }


    private void uploadImageIntoFirebaseStorage(Item item) {
        Calendar calendar = Calendar.getInstance();
        storage.getReference().child("items/"+calendar.getTimeInMillis()).putFile(selectedImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // another request to get image url
                taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        item.setImage(uri.toString());
                        addItemToFirestore(item);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddItem.this, "فشل في تحميل الصورة", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addItemToFirestore(Item item) {
        fb.collection("Items").document(item.getDocId()).set(item)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Toast.makeText(AddItem.this, "تم اضافة المنتج", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddItem.this, "فشل اضافة المنتج", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void fillCategoriesIntoSpinner() {
        fb.collection("Categories").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                if(!queryDocumentSnapshots.isEmpty()) {
                    ArrayList<Category> categories = (ArrayList<Category>) queryDocumentSnapshots.toObjects(Category.class);
                    CategorySPAdapter adapter = new CategorySPAdapter(categories);
                    binding.itemSp.setAdapter(adapter);
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.setting,menu);
        MenuItem save = menu.findItem(R.id.details_menu_save);
        MenuItem edit = menu.findItem(R.id.details_menu_edit);
        MenuItem delete = menu.findItem(R.id.details_menu_delete);
        if(docId == null){
            save.setVisible(true);
            edit.setVisible(false);
            delete.setVisible(false);

        }else {
            save.setVisible(false);
            edit.setVisible(true);
            delete.setVisible(true);

        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        String nameItem = binding.addItemName.getText().toString();
        String description = binding.itemDescription.getText().toString();

        switch (item.getItemId()){
            case R.id.details_menu_save:
                if(docId == null){
                    if(TextUtils.isEmpty(nameItem))
                        binding.addItemName.setError("ادخل اسم المنتج");
                    else if (TextUtils.isEmpty(description))
                        binding.itemDescription.setError("ادخل وصف المنتج");
                    else if (TextUtils.isEmpty(binding.addItemPrice.getText().toString()))
                        binding.addItemPrice.setError("ادخل ثمن المنتج");
                    else if (selectedImageUri == null)
                        Toast.makeText(AddItem.this, "ادخل صورة المنتج", Toast.LENGTH_SHORT).show();
                    else {
                        Double price = Double.parseDouble(binding.addItemPrice.getText().toString());
                        String itemCategory = ((Category)binding.itemSp.getSelectedItem()).getName();
                        String itemQuantity = (String) binding.quantitySp.getSelectedItem();
                        String theCondition = (String) binding.theConditionSp.getSelectedItem();
                        int indexQuantity = binding.quantitySp.getSelectedItemPosition();
                        int indexTheCondition = binding.theConditionSp.getSelectedItemPosition();
                        String docId = fb.collection("Items").document().getId();
                        Item items = new Item(docId, nameItem, description, price, itemCategory, "", itemQuantity, theCondition, indexQuantity , indexTheCondition);
                        uploadImageIntoFirebaseStorage(items);
                    }
                }else{

                    if(TextUtils.isEmpty(nameItem))
                        binding.addItemName.setError("ادخل اسم المنتج");
                    else if (TextUtils.isEmpty(description))
                        binding.itemDescription.setError("ادخل وصف المنتج");
                    else if (TextUtils.isEmpty(binding.addItemPrice.getText().toString()))
                        binding.addItemPrice.setError("ادخل ثمن المنتج");
                    else if (selectedImageUri == null) {
                        double itemPrice = Double.parseDouble(binding.addItemPrice.getText().toString());
                        String ItemCategory = editItemCategory;
                        String itemQuantity = editItemQuantity;
                        String theCondition = (String) binding.theConditionSp.getSelectedItem();
                        int indexQuantity = binding.quantitySp.getSelectedItemPosition();
                        int indexTheCondition = binding.theConditionSp.getSelectedItemPosition();
                        Item items = new Item(docId, nameItem, description, itemPrice, ItemCategory, editSelectedImageUri, itemQuantity, theCondition, indexQuantity , indexTheCondition);
                        addItemToFirestore(items);
                        disableFields();
                        finish();
                    }else {
                        double itemPrice = Double.parseDouble(binding.addItemPrice.getText().toString());
                        String ItemCategory = editItemCategory;
                        String itemQuantity = editItemQuantity;
                        String theCondition = (String) binding.theConditionSp.getSelectedItem();
                        int indexQuantity = binding.quantitySp.getSelectedItemPosition();
                        int indexTheCondition = binding.theConditionSp.getSelectedItemPosition();
                        Item items = new Item(docId,nameItem,description,itemPrice,ItemCategory,"", itemQuantity, theCondition, indexQuantity , indexTheCondition);
                        uploadImageIntoFirebaseStorage(items);
                        disableFields();
                        finish();
                    }
                }

                return true;

                case R.id.details_menu_edit:

                    enableFields();
                    binding.itemSp.setVisibility(View.GONE);
                    binding.quantitySp.setVisibility(View.GONE);
                    MenuItem save = toolbar.getMenu().findItem(R.id.details_menu_save);
                    MenuItem edit = toolbar.getMenu().findItem(R.id.details_menu_edit);
                    MenuItem delete = toolbar.getMenu().findItem(R.id.details_menu_delete);
                    save.setVisible(true);
                    edit.setVisible(false);
                    delete.setVisible(false);
                    return true;

            case R.id.details_menu_delete:
                fb.collection("Items").document(docId).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(AddItem.this, "تم حذف المنتج", Toast.LENGTH_SHORT).show();
                        finish();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddItem.this, "فضل حذف المنتج", Toast.LENGTH_SHORT).show();
                    }
                });

        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMG_REQ_CODE && resultCode == RESULT_OK && data != null) {
            Uri u = data.getData();
            int flag = data.getFlags() & (Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            getContentResolver().takePersistableUriPermission(u,flag);
            binding.addItemImg.setImageURI(u);
            selectedImageUri = u;
        }
    }
}