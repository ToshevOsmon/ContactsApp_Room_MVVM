package com.uzandroid.contactapp.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users_contact")
public class UsersContact {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    private String phone;

    private String email;

    private String ussd;

    public UsersContact() {

    }

    public UsersContact(String name, String phone, String email, String ussd) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.ussd = ussd;
    }

    public String getUssd() {
        return ussd;
    }

    public void setUssd(String ussd) {
        this.ussd = ussd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


}
