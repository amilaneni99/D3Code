package com.projectx.d3codeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.room.Room;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteConstraintException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DetailsActivity extends AppCompatActivity {

    String name,dob,age,phone,regNo,branch,cgpa,interestT,hobbiesT,workExT;
    EditText interests,hobbies,workEx;
    Context context;
    private String path;
    private File dir;
    private File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        checkStoragePermission();
        context  = getApplicationContext();

        final StudentDatabase db = Room.databaseBuilder(getApplicationContext(),
                StudentDatabase.class, "student").build();

        interests = findViewById(R.id.interests);

        hobbies = findViewById(R.id.hobbies);

        workEx = findViewById(R.id.work);


        Intent i = getIntent();
        name = i.getStringExtra("name");
        dob = i.getStringExtra("dob");
//        age = i.getStringExtra("age");
        phone = i.getStringExtra("phone");
        regNo = i.getStringExtra("regNo");
        branch = i.getStringExtra("branch");
        cgpa = i.getStringExtra("cgpa");

        System.out.println(name+" "+dob+" "+phone);

        final Student student = new Student(regNo,name,phone,dob,branch,cgpa,interestT,hobbiesT,workExT);

        findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                interestT = interests.getText().toString();
                hobbiesT = hobbies.getText().toString();
                workExT = workEx.getText().toString();
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            db.studentDao().insertStudent(student);
                            createPDF();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(DetailsActivity.this, "Added", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(DetailsActivity.this,ViewPDF.class);
                                    intent.putExtra("name",name);
                                    intent.putExtra("dob",dob);
                                    intent.putExtra("phone",phone);
                                    intent.putExtra("regNo",regNo);
                                    intent.putExtra("branch",branch);
                                    intent.putExtra("cgpa",cgpa);
                                    intent.putExtra("workEx",workExT);
                                    intent.putExtra("hobbies",hobbiesT);
                                    intent.putExtra("interest",interestT);
                                    startActivity(intent);
                                }
                            });

                        }catch (SQLiteConstraintException ex){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(DetailsActivity.this, "Item Already Added", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });
            }
        });

    }

    private void checkStoragePermission() {
        if (Build.VERSION.SDK_INT >= 23){
            if (!(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)){
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},3);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 3:
                if (grantResults[0]==PackageManager.PERMISSION_GRANTED){

                }else{
                    Toast.makeText(context, "Please Grant Storage Permission", Toast.LENGTH_SHORT).show();
                }
        }
    }

    public void createPDF(){
        Document document = new Document();
        try {
            path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/D3Code";
            dir = new File(path);
            if (!dir.exists()){
                dir.mkdirs();
            }
            file = new File(dir,name+" Resume.pdf");
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            PdfWriter.getInstance(document, fileOutputStream);

            document.open();

            document.setPageSize(PageSize.A4);
            document.addCreationDate();
            document.addAuthor("Abhinav");
            LineSeparator ls = new LineSeparator();
            ls.setLineColor(new BaseColor(0,0,0,68));

            //Heading
            Chunk heading = new Chunk("About "+name);
            Paragraph para1 = new Paragraph(heading);
            para1.setAlignment(Element.ALIGN_CENTER);
            document.add(heading);

            document.add(new Chunk(ls));

            //Basic
            Chunk basicChunk = new Chunk("NAME: "+name.toUpperCase()+"\n"+"Date of Birth: "+dob+"\n"+"Mobile No.: "+phone);
            Paragraph basic = new Paragraph(basicChunk);
            document.add(basic);

            document.add(new Chunk(ls));

            //Education
            Chunk education = new Chunk("Registration Number: "+regNo+"\n"+"Branch: "+branch+"\n"+"CGPA: "+cgpa);
            Paragraph edu = new Paragraph(education);
            document.add(edu);

            document.add(new Chunk(ls));

            //About
            Chunk about = new Chunk("Interests: "+interestT+"\n"+"Hobbies: "+hobbiesT+"\n"+"Work Experience: "+workExT);
            Paragraph aboutP = new Paragraph(about);
            document.add(aboutP);

            document.add(new Chunk(ls));

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            document.close();
        }
    }


}
