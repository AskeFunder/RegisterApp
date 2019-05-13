package com.example.registerapp.model;

public class User {

    private String name, username, password, language;
    private int age;

    public User(String name, String username, String password, int age, String language) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.age = age;
        this.language = language;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.age = -1;
        this.name = "";
    }

    public User() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        String toString = "";

        toString = toString + "Username: " + this.username + " Password: " + this.password;

        return toString;
    }
}
