package com.uzandroid.contactapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.uzandroid.contactapp.adapter.ContactAdapter;
import com.uzandroid.contactapp.model.UsersContact;
import com.uzandroid.contactapp.viewmodel.ContactViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int ADD_REQUEST_CODE = 1;
    public static final int EDIT_REQUEST_CODE = 2;
    public static final int INFO_REQUEST_CODE = 3;
    public static final int INFO_RESULT_CODE = 1;

    private FloatingActionButton addButton;
    private RecyclerView recyclerView;
    private ContactAdapter adapter;

    private ContactViewModel contactViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar()
                .setTitle("Contacts");

        recyclerView = findViewById(R.id.recycler_view_Id);
        addButton = findViewById(R.id.floating_action_button_Id);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        adapter = new ContactAdapter(MainActivity.this);
        recyclerView.setAdapter(adapter);


        contactViewModel = ViewModelProviders.of(this).get(ContactViewModel.class);

        contactViewModel.getAllContacts().observe(this, new Observer<List<UsersContact>>() {
            @Override
            public void onChanged(List<UsersContact> usersContacts) {

                adapter.setContacts(usersContacts);
                adapter.notifyDataSetChanged();


            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent1 = new Intent(MainActivity.this, AddEditActivity.class);
                startActivityForResult(intent1, ADD_REQUEST_CODE);

            }
        });

        adapter.setOnItemClickListener(new ContactAdapter.OnItemClickListener() {
            @Override
            public void onItemClickActivity(UsersContact contact) {


                Intent intent = new Intent(MainActivity.this, Info1Activity.class);
                intent.putExtra(AddEditActivity.EXTRA_ID, contact.getId());
                intent.putExtra(AddEditActivity.EXTRA_NAME, contact.getName());
                intent.putExtra(AddEditActivity.EXTRA_PHONE, contact.getPhone());
                intent.putExtra(AddEditActivity.EXTRA_EMAIL,contact.getEmail());
                intent.putExtra(AddEditActivity.EXTRA_USSD, contact.getUssd());
                startActivity(intent);

            }
        });

        adapter.setOnItemClickDeleteListener(new ContactAdapter.OnItemDeleteListener() {
            @Override
            public void onItemClickDelete(final UsersContact contact) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Delete Contact");
                builder.setMessage("Are you delete contact? Right!");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        contactViewModel.deleteContact(contact);

                    }
                });
                builder.setNegativeButton("No", null);
                builder.create();
                builder.show();


            }
        });

        adapter.setOnItemClickListener(new ContactAdapter.OnItemEditListener() {
            @Override
            public void onItemClickEdit(UsersContact contact) {

                Intent intent = new Intent(MainActivity.this, AddEditActivity.class);
                intent.putExtra(AddEditActivity.EXTRA_ID, contact.getId());
                intent.putExtra(AddEditActivity.EXTRA_NAME, contact.getName());
                intent.putExtra(AddEditActivity.EXTRA_PHONE, contact.getPhone());
                intent.putExtra(AddEditActivity.EXTRA_EMAIL,contact.getEmail());
                intent.putExtra(AddEditActivity.EXTRA_USSD,contact.getUssd());
                startActivityForResult(intent, EDIT_REQUEST_CODE);

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_REQUEST_CODE && resultCode == RESULT_OK) {

            String name = data.getStringExtra(AddEditActivity.EXTRA_NAME);
            String phone = data.getStringExtra(AddEditActivity.EXTRA_PHONE);
            String email = data.getStringExtra(AddEditActivity.EXTRA_EMAIL);
            String ussd = data.getStringExtra(AddEditActivity.EXTRA_USSD);

            UsersContact contact = new UsersContact();
            contact.setName(name);
            contact.setPhone(phone);
            contact.setEmail(email);
            contact.setUssd(ussd);
            contactViewModel.insertContact(contact);

            Toast.makeText(this, "Save Contact", Toast.LENGTH_SHORT).show();

        }
        else if (requestCode == EDIT_REQUEST_CODE && resultCode == RESULT_OK) {

            int id = data.getIntExtra(AddEditActivity.EXTRA_ID, -1);

            if (id == -1) {

                Toast.makeText(this, "Contact can't Edit", Toast.LENGTH_SHORT).show();

                return;
            }

            String name = data.getStringExtra(AddEditActivity.EXTRA_NAME);
            String phone = data.getStringExtra(AddEditActivity.EXTRA_PHONE);
            String email = data.getStringExtra(AddEditActivity.EXTRA_EMAIL);
            String ussd = data.getStringExtra(AddEditActivity.EXTRA_USSD);

            UsersContact contact = new UsersContact(name, phone, email, ussd);

            contact.setId(id);

            contactViewModel.updateContact(contact);

            Toast.makeText(this, "Contact Edit", Toast.LENGTH_SHORT).show();


        } else if (requestCode == INFO_REQUEST_CODE && resultCode == RESULT_OK) {

            int id = data.getIntExtra(AddEditActivity.EXTRA_ID, -1);

            if (id == -1) {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                return;
            }

            String name = data.getStringExtra(AddEditActivity.EXTRA_NAME);
            String phone = data.getStringExtra(AddEditActivity.EXTRA_PHONE);
            String email = data.getStringExtra(AddEditActivity.EXTRA_EMAIL);
            String ussd = data.getStringExtra(AddEditActivity.EXTRA_USSD);

            Intent intent = new Intent(MainActivity.this, Info1Activity.class);
            intent.putExtra(AddEditActivity.EXTRA_ID, id);
            intent.putExtra(AddEditActivity.EXTRA_NAME, name);
            intent.putExtra(AddEditActivity.EXTRA_PHONE, phone);
            intent.putExtra(AddEditActivity.EXTRA_EMAIL, email);
            intent.putExtra(AddEditActivity.EXTRA_USSD, ussd);
            setResult(RESULT_OK, intent);
            finish();


        } else {
            Toast.makeText(this, "Not Save", Toast.LENGTH_SHORT).show();
        }

    }
}
