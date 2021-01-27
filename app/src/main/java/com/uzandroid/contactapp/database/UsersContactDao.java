package com.uzandroid.contactapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.uzandroid.contactapp.model.UsersContact;

import java.util.List;

@Dao
public interface UsersContactDao {

    @Insert
    void insertContact(UsersContact contact);

    @Update
    void updateContact(UsersContact contact);

    @Delete
    void deleteContact(UsersContact contact);

    @Query("SELECT * FROM users_contact")
    LiveData<List<UsersContact>> getAllContacts();

    @Query("SELECT * FROM users_contact WHERE name = :name")
    LiveData<List<UsersContact>> getContact(String name);


    @Query("DELETE FROM users_contact")
    void deleteAllContact();

}
