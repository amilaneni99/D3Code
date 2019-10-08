package com.projectx.d3codeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class ViewPDF extends AppCompatActivity {

    String name,dob,age,phone,regNo,branch,cgpa,interestT,hobbiesT,workExT;
    private String path;
    private File dir;
    private File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pdf);

        Intent i = getIntent();
        name = i.getStringExtra("name");
        dob = i.getStringExtra("dob");
        phone = i.getStringExtra("phone");
        regNo = i.getStringExtra("regNo");
        branch = i.getStringExtra("branch");
        cgpa = i.getStringExtra("cgpa");
        interestT = i.getStringExtra("interest");
        workExT = i.getStringExtra("workEx");
        hobbiesT = i.getStringExtra("hobbies");

        findViewById(R.id.makePdf).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPDF();
            }
        });

        findViewById(R.id.editDetails).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewPDF.this,EditDetails.class);
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
