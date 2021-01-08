package com.example.pdftest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ActivityCompat.requestPermissions(this,new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        ActivityCompat.requestPermissions(this,new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
        button = (Button)findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CrPDF();

            }
        });
    }

    public void CrPDF()

    {
        PdfDocument pdfDocument = new PdfDocument();
        Paint paint = new Paint();

        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(1200,2400,1).create();
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);

        Canvas canvas = page.getCanvas();
        canvas.drawText("Hello World",400,500,paint);
        pdfDocument.finishPage(page);

        Toast.makeText(MainActivity.this,Environment.getExternalStorageState(),Toast.LENGTH_SHORT).show();
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/mypdf.pdf";
        String path1 = "/Internal Storage/mypdf.pdf";
        Toast.makeText(MainActivity.this,path,Toast.LENGTH_SHORT).show();
        File file = new File(path);

        try {
            Toast.makeText(MainActivity.this,"Before",Toast.LENGTH_SHORT).show();
            pdfDocument.writeTo(new FileOutputStream(file));
            Toast.makeText(MainActivity.this,"After",Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(MainActivity.this,"Error",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        pdfDocument.close();
    }
}