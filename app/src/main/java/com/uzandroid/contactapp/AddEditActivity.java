package com.uzandroid.contactapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class AddEditActivity extends AppCompatActivity {

    public static final String EXTRA_ID = "com.uzandroid.contactapp.EXTRA_ID";
    public static final String EXTRA_NAME = "com.uzandroid.contactapp.EXTRA_NAME";
    public static final String EXTRA_PHONE = "com.uzandroid.contactapp.EXTRA_PHONE";
    public static final String EXTRA_EMAIL = "com.uzandroid.contactapp.EXTRA_EMAIL";
    public static final String EXTRA_USSD = "com.uzandroid.contactapp.EXTRA_USSD";

    private EditText editName;
    private EditText editPhone;
    private EditText editEmail;
    public String ussd ;

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

        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_ID)) {

            actionBar.setTitle("Edit Contact");

            String name = intent.getStringExtra(EXTRA_NAME);
            String phone = intent.getStringExtra(EXTRA_PHONE);
            String email = intent.getStringExtra(EXTRA_EMAIL);


            ussd = intent.getStringExtra(EXTRA_USSD);

            editName.setText(name);
            editPhone.setText(phone);
            editEmail.setText(email);

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

        if (phone.length() < 9 ) {
            editPhone.setError("Number Error");

            return;
        }
        if (email.endsWith(".com") || email.endsWith(".ru")|| email.endsWith(".uz")) {
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
            ussd = "Beline";
        }
        if (phone.startsWith("97") || phone.startsWith("+99897")) {
            ussd = "UMS";
        }
        if (phone.startsWith("88") || phone.startsWith("+99888")) {
            ussd = "Humuns";
        }


        Intent data = new Intent();

        data.putExtra(EXTRA_NAME, name);
        data.putExtra(EXTRA_PHONE, phone);
        data.putExtra(EXTRA_EMAIL, email);
        data.putExtra(EXTRA_USSD, ussd);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);

        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();

    }
}
