package com.projectx.d3codeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class EditDetails extends AppCompatActivity {

    EditText name,dob,phone,regNo,branch,cgpa,interests,hobbies,workEx;
    String nameT,dobT,phoneT,regNoT,branchT,cgpaT,interestT,hobbiesT,workExT,day,month,year;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    final Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_details);

        final StudentDatabase db = Room.databaseBuilder(getApplicationContext(),
                StudentDatabase.class, "student").build();

        Intent i = getIntent();
        nameT = i.getStringExtra("name");
        dobT = i.getStringExtra("dob");
        phoneT = i.getStringExtra("phone");
        regNoT = i.getStringExtra("regNo");
        branchT = i.getStringExtra("branch");
        cgpaT = i.getStringExtra("cgpa");
        interestT = i.getStringExtra("interest");
        workExT = i.getStringExtra("workEx");
        hobbiesT = i.getStringExtra("hobbies");

        name = findViewById(R.id.nameEdit);
        dob = findViewById(R.id.dobEdit);
        phone = findViewById(R.id.phoneEdit);
        regNo = findViewById(R.id.regNoEdit);
        branch = findViewById(R.id.branchEdit);
        cgpa = findViewById(R.id.cgpaEdit);
        interests = findViewById(R.id.interestsEdit);
        hobbies = findViewById(R.id.hobbiesEdit);
        workEx = findViewById(R.id.workEdit);

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                Student student = null;
                try{
                    student = db.studentDao().getStudent(regNoT);
                }catch (SQLiteConstraintException e){
                    Toast.makeText(EditDetails.this, "Error", Toast.LENGTH_SHORT).show();
                }finally {
                    name.setText(student.name);
                    dob.setText(student.dob);
                    phone.setText(student.phone);
                    regNo.setText(student.regNo);
                    branch.setText(student.branch);
                    cgpa.setText(student.cgpa);
                    interests.setText(student.interests);
                    hobbies.setText(student.hobbies);
                    workEx.setText(student.work);
                }
            }
        });



        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int y, int m, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, y);
                myCalendar.set(Calendar.MONTH, m);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                day = myCalendar.get(Calendar.DAY_OF_MONTH)+"";
                month = myCalendar.get(Calendar.MONTH)+1+"";
                year = myCalendar.get(Calendar.YEAR)+"";
                dob.setText(day+"/"+month+"/"+year);
            }

        };

        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(EditDetails.this, dateSetListener, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.setTitle("Date of Birth");
                datePickerDialog.setCanceledOnTouchOutside(false);
                datePickerDialog.show();
                datePickerDialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.BLACK);
                datePickerDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
            }
        });

        findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Student student = new Student(regNoT,nameT,phoneT,dobT,branchT,cgpaT,interestT,hobbiesT,workExT);
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            db.studentDao().updateStudent(student);
                        }catch (SQLiteConstraintException ex){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(EditDetails.this, "Item Already Added", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }finally {
                            try {
                                AsyncTask.execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        final Student student1 = db.studentDao().getStudent(regNoT);
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(EditDetails.this, student1.dob, Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                });
                            }catch (SQLiteConstraintException ex){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(EditDetails.this, "Item Already Added", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }finally {
                                Intent intent = new Intent(EditDetails.this,ViewPDF.class);
                                startActivity(intent);
                            }
                        }
                    }
                });
            }
        });



    }
}
