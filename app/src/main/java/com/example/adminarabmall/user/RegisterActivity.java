package com.example.adminarabmall.user;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.adminarabmall.Constants;
import com.example.adminarabmall.MainActivity;
import com.example.adminarabmall.databinding.ActivityRegisterBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity {
    ActivityRegisterBinding binding;
    private FirebaseAuth mAuth;
    FirebaseStorage storage;
    private FirebaseFirestore db;
    private static final int PICK_IMG_REQ_CODE = 1;
    Uri selectedImageUri;

    private Button register;
    private TextView have_account;
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        db = FirebaseFirestore.getInstance();

        register = binding.activityRegisterBtnRegister;
        have_account = binding.activityRegisterTvHaveAccount;
        img = binding.profileUserImg;

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, PICK_IMG_REQ_CODE);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.activityRegisterEdName.getText().toString();
                String phone = binding.activityRegisterEdPhone.getText().toString().trim();
                String email = binding.activityRegisterEdEmail.getText().toString().trim();
                String pass = binding.activityRegisterEdPassword.getText().toString().trim();

                if(TextUtils.isEmpty(name))
                    binding.activityRegisterEdName.setError("ادخل اسمك");
                else if(TextUtils.isEmpty(phone))
                    binding.activityRegisterEdName.setError("ادخل رقم هاتفك");
                else if(TextUtils.isEmpty(email))
                    binding.activityRegisterEdName.setError("ادخل البريد الالكتروني");
                else if(TextUtils.isEmpty(pass))
                    binding.activityRegisterEdName.setError("ادخل كلمة المرور مرة اخرى");
                else if(selectedImageUri == null)
                    Toast.makeText(RegisterActivity.this, "اضف صورتك", Toast.LENGTH_SHORT).show();
                else{
                    mAuth.createUserWithEmailAndPassword(email, pass)
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {

                                    User user = new User(name, phone, email, "", mAuth.getUid());

                                    addImageToStoreg(user);

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(RegisterActivity.this, "فشل تسجيل الحساب" + e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }

        });

        have_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void addImageToStoreg(User user) {
        Calendar calendar = Calendar.getInstance();
        storage.getReference().child("profile/"+calendar.getTimeInMillis()).putFile(selectedImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // another request to get image url
                taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        user.setImg(uri.toString());
                        addUserToFirestore(user);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegisterActivity.this, "فشل في تحميل الصورة", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addUserToFirestore(User user) {
        db.collection("Admins").document(user.getUserId()).set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                        SharedPreferences.Editor edit = sp.edit();
                        edit.putString(Constants.USER_UID_KEY,user.getUserId());
                        edit.putString(Constants.USER_EMAIL_KEY,user.getEmail());
                        edit.putString(Constants.USER_NAME_KEY,user.getName());
                        edit.putString(Constants.USER_IMAGE_KEY,user.getImg());
                        edit.putString(Constants.USER_PHONE,user.getPhone());
                        edit.commit();

                        Toast.makeText(RegisterActivity.this, "تم التسجيل بنجاح", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegisterActivity.this, "فشل عملية التسجيل", Toast.LENGTH_SHORT).show();
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

            img.setImageURI(u);
            selectedImageUri = u;
        }
    }
}