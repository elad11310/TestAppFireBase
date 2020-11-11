package com.example.myapplication;

public class Client {
    public String name;
    public String birthDate;
    public String gender;
    public String id;

    public Client() { // default constructor , we use this for inserting data later.

    }

    public Client(String name, String birthDate, String gender, String id) {
        this.name = name;
        this.birthDate = birthDate;
        this.gender = gender;
        this.id = id;
    }

    public String toString(){
        return "Name: " + this.name + " BirthDate: " + this.birthDate + " Gender:"+ this.gender;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
