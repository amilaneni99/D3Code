package com.projectx.d3codeapp;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface StudentDao {
    @Query("SELECT * FROM student WHERE reg_no = :regNo")
    Student getStudent(String regNo);

    @Insert
    void insertStudent(Student student);

    @Update
    void updateStudent(Student student);

    @Delete
    void delete(Student student);
}
