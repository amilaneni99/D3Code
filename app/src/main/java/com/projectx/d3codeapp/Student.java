package com.projectx.d3codeapp;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Student {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "reg_no")
    public String regNo;

    public String name;

    public String phone;

    public String dob;

    public String branch;

    public String cgpa;

//    public String age;

    public String interests;

    public String hobbies;

    public String work;


    public Student(String regNo, String name, String phone, String dob, String branch, String cgpa, String interests, String hobbies, String work) {
        this.regNo = regNo;
        this.name = name;
        this.phone = phone;
        this.dob = dob;
        this.branch = branch;
        this.cgpa = cgpa;
//        this.age = age;
        this.interests = interests;
        this.hobbies = hobbies;
        this.work = work;
    }

    @NonNull
    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(@NonNull String regNo) {
        this.regNo = regNo;
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

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getCgpa() {
        return cgpa;
    }

    public void setCgpa(String cgpa) {
        this.cgpa = cgpa;
    }

    public String getInterests() {
        return interests;
    }

    public void setInterests(String interests) {
        this.interests = interests;
    }

    public String getHobbies() {
        return hobbies;
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }
}
