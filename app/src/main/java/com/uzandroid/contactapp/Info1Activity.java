package com.uzandroid.contactapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.uzandroid.contactapp.conventer.ImageConverter;

public class Info1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info1);

        //ImageView info_image = findViewById(R.id.info_image);
        TextView info_name = findViewById(R.id.info_name);
        TextView info_phone = findViewById(R.id.info_phone);
        TextView info_email = findViewById(R.id.info_email);
        TextView info_ussd = findViewById(R.id.text_ussd);
        ImageView imageView = findViewById(R.id.info_image);

        //action bar
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Contact Info");


        //intent malumotlarni olib keladi
        Intent intent = getIntent();

        String name =  intent.getStringExtra(AddEditActivity.EXTRA_NAME);
        String phone = intent.getStringExtra(AddEditActivity.EXTRA_PHONE);
        String email = intent.getStringExtra(AddEditActivity.EXTRA_EMAIL);
        String ussd = intent.getStringExtra(AddEditActivity.EXTRA_USSD);
        byte[] image = intent.getByteArrayExtra(AddEditActivity.EXTRA_IMAGE);
        Log.d("TTT","Ishladi: "+ussd+"\n"+phone);

        info_name.setText(name);
        info_phone.setText(phone);
        info_email.setText(email);
        info_ussd.setText(ussd);
        imageView.setImageBitmap(ImageConverter.toBitmap(image));

    }
}
