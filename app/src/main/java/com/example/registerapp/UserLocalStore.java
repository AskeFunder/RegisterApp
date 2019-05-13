package com.example.registerapp;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.registerapp.model.User;

public class UserLocalStore {

    public static final String SP_NAME = "userDetails";
    SharedPreferences userLocalDatabase;

    public UserLocalStore(Context context) {
        userLocalDatabase = context.getSharedPreferences(SP_NAME, 0);
    }

    public void storeUserData(User user) {
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putString("name", user.getName());
        spEditor.putInt("age", user.getAge());
        spEditor.putString("username", user.getUsername());
        spEditor.putString("password", user.getPassword());

        spEditor.commit();
    }

    public User getLoggedInUser() {
        String name = userLocalDatabase.getString("name", "N/A");
        int age = userLocalDatabase.getInt("age", -1);
        String username = userLocalDatabase.getString("username", "N/A");
        String password = userLocalDatabase.getString("password", "N/A");
        String language = userLocalDatabase.getString("language", "N/A");

        User storedUser = new User(name, username, password, age, language);

        return storedUser;
    }

    public void setUserLoggedIn(boolean loggedIn) {
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putBoolean("loggedIn", loggedIn);
        spEditor.commit();
    }

    public UserLocalStore clearUserData() {
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.clear();
        return this;
    }

    public boolean getUserLoggedIn() {
        if (userLocalDatabase.getBoolean("loggedIn", false)) {
            return true;
        } else {
            return false;
        }
    }

    public String getRememberedUsername() {
        String rememberedUsername = userLocalDatabase.getString("rememberedUsername", null);
        return rememberedUsername;
    }

    public void setRememberedUsername(String username) {
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putString("rememberedUsername", username);
        spEditor.commit();
    }


}
