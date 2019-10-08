package com.projectx.d3codeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import org.threeten.bp.LocalDate;
import org.threeten.bp.Period;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    final Calendar myCalendar = Calendar.getInstance();
    String nameT="",dobT="",phoneT="",regNoT="",branchT="",cgpaT="";
    EditText name,dob,phone,regNo,branch,cgpa;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    String day,month,year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        name = findViewById(R.id.name);


        dob = findViewById(R.id.dob);

        phone = findViewById(R.id.phone);


        regNo = findViewById(R.id.regNo);


        branch = findViewById(R.id.branch);

        cgpa = findViewById(R.id.cgpa);




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

//        LocalDate dOB = LocalDate.of(Integer.parseInt(year),Integer.parseInt(month)+1,Integer.parseInt(day));
//        LocalDate now = LocalDate.now();
//        Period diff =Period.between(dOB,now);
//        age = String.valueOf(diff.getYears());

        findViewById(R.id.dob).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, dateSetListener, myCalendar
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
                nameT = name.getText().toString();
                phoneT = phone.getText().toString();
                regNoT = regNo.getText().toString();
                branchT = branch.getText().toString();
                cgpaT = cgpa.getText().toString();
                dobT = dob.getText().toString();
                Intent intent = new Intent(MainActivity.this,DetailsActivity.class);
                intent.putExtra("name",nameT);
                intent.putExtra("dob",dobT);
                intent.putExtra("phone",phoneT);
                intent.putExtra("regNo",regNoT);
                intent.putExtra("branch",branchT);
                intent.putExtra("cgpa",cgpaT);
//                intent.putExtra("age",age);
                startActivity(intent);
            }
        });
    }
}
