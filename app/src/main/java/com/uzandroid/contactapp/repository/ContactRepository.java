package com.uzandroid.contactapp.repository;

import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;
import com.uzandroid.contactapp.database.ContactDatabase;
import com.uzandroid.contactapp.database.UsersContactDao;
import com.uzandroid.contactapp.model.UsersContact;
import java.util.List;

public class ContactRepository {

    private UsersContactDao contactDao;

    private LiveData<List<UsersContact>> contacts;

    public ContactRepository(Application application) {

        ContactDatabase database = ContactDatabase.getInstance(application);
        contactDao = database.contactDao();
        contacts = contactDao.getAllContacts();
    }

    public void insertContact(UsersContact contact) {
        new InsertContactAsyncTask(contactDao).execute(contact);

    }

    public void updateContact(UsersContact contact) {
        new UpdateContactAsyncTask(contactDao).execute(contact);
    }

    public void deleteContact(UsersContact contact) {
        new DeleteContactAsyncTask(contactDao).execute(contact);
    }

    public void deleteAllContacts() {
        new DeleteAllContactAsyncTask(contactDao).execute();

    }

    public LiveData<List<UsersContact>> getAllContacts() {
        return contacts;
    }

    public LiveData<List<UsersContact>> getContact(String name) {
        return contacts;
    }


    private static class InsertContactAsyncTask extends AsyncTask<UsersContact, Void, Void> {
        private UsersContactDao contactDao;

        private InsertContactAsyncTask(UsersContactDao contactDao) {
            this.contactDao = contactDao;
        }

        @Override
        protected Void doInBackground(UsersContact... usersContacts) {
            contactDao.insertContact(usersContacts[0]);
            return null;
        }
    }

    private static class UpdateContactAsyncTask extends AsyncTask<UsersContact, Void, Void> {
        private UsersContactDao contactDao;

        private UpdateContactAsyncTask(UsersContactDao contactDao) {
            this.contactDao = contactDao;
        }

        @Override
        protected Void doInBackground(UsersContact... usersContacts) {
            contactDao.updateContact(usersContacts[0]);
            return null;
        }
    }

    private static class DeleteContactAsyncTask extends AsyncTask<UsersContact, Void, Void> {
        private UsersContactDao contactDao;

        private DeleteContactAsyncTask(UsersContactDao contactDao) {
            this.contactDao = contactDao;
        }

        @Override
        protected Void doInBackground(UsersContact... usersContacts) {
            contactDao.deleteContact(usersContacts[0]);
            return null;
        }
    }

    private static class DeleteAllContactAsyncTask extends AsyncTask<Void, Void, Void> {
        private UsersContactDao contactDao;

        private DeleteAllContactAsyncTask(UsersContactDao contactDao) {
            this.contactDao = contactDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            contactDao.deleteAllContact();
            return null;
        }
    }

}
