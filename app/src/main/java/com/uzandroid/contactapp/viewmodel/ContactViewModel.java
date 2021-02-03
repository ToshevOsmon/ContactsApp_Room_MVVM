package com.uzandroid.contactapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.uzandroid.contactapp.model.UsersContact;
import com.uzandroid.contactapp.repository.ContactRepository;

import java.util.List;

public class ContactViewModel extends AndroidViewModel {

    private ContactRepository repository;
    private LiveData<List<UsersContact>> contacts;

    public ContactViewModel(@NonNull Application application) {
        super(application);

        repository = new ContactRepository(application);
        contacts = repository.getAllContacts();

    }

    public void insertContact(UsersContact contact) {
        repository.insertContact(contact);
    }

    public void updateContact(UsersContact contact) {
        repository.updateContact(contact);
    }

    public void deleteContact(UsersContact contact) {
        repository.deleteContact(contact);
    }

    public void deleteAllContacts() {
        repository
                .deleteAllContacts();
    }

    public LiveData<List<UsersContact>> getAllContacts() {
        return contacts;
    }

    public LiveData<List<UsersContact>> getContact(String name) {
        return contacts;
    }
}
