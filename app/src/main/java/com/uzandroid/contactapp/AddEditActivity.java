package com.uzandroid.contactapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.uzandroid.contactapp.conventer.ImageConverter;


public class AddEditActivity extends AppCompatActivity {

    public static final String EXTRA_ID = "com.uzandroid.contactapp.EXTRA_ID";
    public static final String EXTRA_NAME = "com.uzandroid.contactapp.EXTRA_NAME";
    public static final String EXTRA_PHONE = "com.uzandroid.contactapp.EXTRA_PHONE";
    public static final String EXTRA_EMAIL = "com.uzandroid.contactapp.EXTRA_EMAIL";
    public static final String EXTRA_USSD = "com.uzandroid.contactapp.EXTRA_USSD";
    public static final String EXTRA_IMAGE = "com.uzandroid.contactapp.EXTRA_IMAGE";

    public static final int IMAGE_PICK_CODE = 1000;
    public static final int PERMISSON_CODE = 1001;
    public static final int CAMERA_INTENT = 51;

    private EditText editName;
    private EditText editPhone;
    private EditText editEmail;
    private ImageView image_view_id;
    public String ussd;
    private Bitmap bitmapImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        editName = findViewById(R.id.edit_name_id);
        editPhone = findViewById(R.id.edit_phone_id);
        Button saveButton = findViewById(R.id.button_save_id);
        editEmail = findViewById(R.id.edit_email_id);
        image_view_id = findViewById(R.id.image_view_id);

        bitmapImage = null;

        image_view_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, CAMERA_INTENT);
                }

            }
        });

        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_ID)) {

            actionBar.setTitle("Edit Contact");

            String name = intent.getStringExtra(EXTRA_NAME);
            String phone = intent.getStringExtra(EXTRA_PHONE);
            String email = intent.getStringExtra(EXTRA_EMAIL);
            ussd = intent.getStringExtra(EXTRA_USSD);

            byte[] image = intent.getByteArrayExtra(EXTRA_IMAGE);
            bitmapImage = ImageConverter.toBitmap(image);

            editName.setText(name);
            editPhone.setText(phone);
            editEmail.setText(email);
            image_view_id.setImageBitmap(bitmapImage);
        } else {

            actionBar.setTitle("Add Contact");

        }
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                saveContact();

            }
        });

    }

    //button save
    private void saveContact() {

        String name = editName.getText().toString();
        String phone = editPhone.getText().toString();
        String email = editEmail.getText().toString();

        if (name.trim().isEmpty()) {
            editName.setError("Name Empty");
            return;
        }
        if (phone.trim().isEmpty()) {
            editPhone.setError("Phone Number Empty");
            return;
        }

        if (phone.length() < 9) {
            editPhone.setError("Number Error");

            return;
        }
        if (!(email.endsWith(".com") || email.endsWith(".ru") || email.endsWith(".uz"))) {
            editEmail.setError("Error email");
            return;
        }


        if (phone.startsWith("94") || phone.startsWith("+99894") ||
                phone.startsWith("93") || phone.startsWith("+99893")) {
            ussd = "Ucell";
        }
        if (phone.startsWith("99") || phone.startsWith("+99899") ||
                phone.startsWith("95") || phone.startsWith("+99895")) {
            ussd = "Uzmobile";
        }
        if (phone.startsWith("90") || phone.startsWith("91") ||
                phone.startsWith("+99891") || phone.startsWith("+99890")) {
            ussd = "Beeline";
        }
        if (phone.startsWith("97") || phone.startsWith("+99897")) {
            ussd = "MobiUz";
        }
        if (phone.startsWith("33") || phone.startsWith("+99833")) {
            ussd = "Humans";
        }
        if (phone.startsWith("98") || phone.startsWith("+99898")) {
            ussd = "Perfectum";
        }


        Intent data = new Intent();

        byte[] imageByte = ImageConverter.fromBitmap(bitmapImage);

        data.putExtra(EXTRA_NAME, name);
        data.putExtra(EXTRA_PHONE, phone);
        data.putExtra(EXTRA_EMAIL, email);
        data.putExtra(EXTRA_USSD, ussd);
        data.putExtra(EXTRA_IMAGE, imageByte);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);

        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();

    }

  /*  @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {

            case PERMISSON_CODE: {

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickImageFromGallery();
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }


    }

*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (resultCode == RESULT_OK && requestCode == CAMERA_INTENT) {

            Log.d("TTTT", "" + data.toString());
            bitmapImage = (Bitmap) data.getExtras().get("data");

            if (bitmapImage != null) {

                image_view_id.setImageBitmap(bitmapImage);

            }

        }
    }

}
