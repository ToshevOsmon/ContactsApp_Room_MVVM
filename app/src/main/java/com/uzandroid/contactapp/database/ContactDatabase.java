package com.uzandroid.contactapp.database;

import android.content.Context;
import android.os.AsyncTask;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import com.uzandroid.contactapp.model.UsersContact;

@Database(entities = {UsersContact.class}, version = 1, exportSchema = false)
public abstract class ContactDatabase extends RoomDatabase {

    private static ContactDatabase instance;

    public abstract UsersContactDao contactDao();

    public static synchronized ContactDatabase getInstance(Context context) {
        if (instance == null) {

            instance = Room.databaseBuilder(context.getApplicationContext(),
                    ContactDatabase.class,
                    "contacts__DB")
                    .allowMainThreadQueries()

                    //addCallBack ma'lumotni bazaga qo'shib quyadi
                    .addCallback(callback)
                    .build();

        }
        return instance;
    }

    private static RoomDatabase.Callback callback = new RoomDatabase.Callback() {

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {

        private UsersContactDao contactDao;

        private PopulateDbAsyncTask(ContactDatabase database) {
            contactDao = database.contactDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            contactDao.insertContact(new UsersContact("Osmon","946530255","toshev.osmon@gmail.com","Ucell"));
            return null;
        }
    }


}
