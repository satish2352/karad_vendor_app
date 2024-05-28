package com.example.karadvenderapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.karadvenderapp.MyLib.Constants;
import com.example.karadvenderapp.MyLib.Shared_Preferences;
import com.example.karadvenderapp.R;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;


public class    DownloadInvoiceActivity extends AppCompatActivity {

    private static final String TAG ="mytag" ;
    private TextView toolbar,tv_company_name, tv_price, tv_date, tv_name, tv_mobile, tv_email_id, tv_address, tv_package_name,
            tv_package_price, tv_sub_total, tv_tax, tv_main_total,tv_invoice_number,tv_busi_name,tv_business_type,tv_validity,tv_startdate,tv_enddate;


    private Button btn_takescreen;
    private Bitmap myBitmap;
    private LinearLayout llPdf;
    private Bitmap bitmap;
    private String Invoice, fld_package_name, fld_package_amount,
            owner_name, mobile,
            email, fld_product_type_name;
    private static final int PERMISSION_REQUEST_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invoice);
        btn_takescreen = findViewById(R.id.btn_takescreen);
        toolbar = findViewById(R.id.toolbar);
        llPdf = findViewById(R.id.linear_layout);

//        tv_price = findViewById(R.id.tv_price);
        tv_date = findViewById(R.id.tv_date);
        tv_name = findViewById(R.id.tv_name);
        tv_mobile = findViewById(R.id.tv_mobile);
        tv_email_id = findViewById(R.id.tv_email_id);
        tv_address = findViewById(R.id.tv_address);
        tv_package_name = findViewById(R.id.tv_package_name);
        tv_package_price = findViewById(R.id.tv_package_price);
        tv_sub_total = findViewById(R.id.tv_sub_total);
        tv_tax = findViewById(R.id.tv_tax);
        tv_main_total = findViewById(R.id.tv_main_total);
        tv_invoice_number= findViewById(R.id.tv_invoice_number);
        tv_company_name= findViewById(R.id.tv_company_name);
        tv_busi_name=findViewById(R.id.tv_busi_name);
        tv_date= findViewById(R.id.tv_date);
        tv_business_type= findViewById(R.id.tv_business_type);
        tv_validity=findViewById(R.id.tv_validity);
        tv_startdate=findViewById(R.id.tv_startdate);
        tv_enddate=findViewById(R.id.tv_enddate);

        Intent intent = getIntent();
        Invoice = intent.getStringExtra("id");
        fld_package_name = intent.getStringExtra("fld_package_name");
        fld_package_amount = intent.getStringExtra("fld_package_amount");
        mobile = intent.getStringExtra("mobile");
        email = intent.getStringExtra("email");
        fld_product_type_name = intent.getStringExtra("fld_product_type_name");


        tv_name.setText(Shared_Preferences.getPrefs(DownloadInvoiceActivity.this,Constants.REG_NAME));
        tv_mobile.setText(mobile);
        tv_email_id.setText(email);
        tv_package_name.setText(fld_package_name);
        tv_package_price.setText("₹" + fld_package_amount);
        tv_sub_total.setText("₹" + fld_package_amount);
        tv_main_total.setText("₹" + fld_package_amount);
        tv_invoice_number.setText("INV-" + Invoice);
        tv_date.setText(intent.getStringExtra("created_at"));
        tv_busi_name.setText(intent.getStringExtra("Businessname"));
        tv_business_type.setText(intent.getStringExtra("Businesstype"));
        tv_validity.setText(intent.getStringExtra("validity")+" Days");
        tv_startdate.setText(intent.getStringExtra("startdate"));
        tv_enddate.setText(intent.getStringExtra("enddate"));

      checkPermissionsAndCreatePdf();


        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btn_takescreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.d("size", " " + llPdf.getWidth() + "  " + llPdf.getWidth());
//                bitmap = loadBitmapFromView(llPdf, llPdf.getWidth(), llPdf.getHeight());
//                try {
//                    createPdf();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }

                createNewPdf();
            }
        });
    }

    private void createNewPdfs() {
        // Create a new document
        PdfDocument myPdfDocument = new PdfDocument();
        Paint myPaint = new Paint();
        myPaint.setTextSize(16);
        myPaint.setColor(android.graphics.Color.BLACK);

        // Create PageInfo object with page width, height, and page number
        PdfDocument.PageInfo mypageInfo1 = new PdfDocument.PageInfo.Builder(1080, 720, 1).create();

        // Start a page
        PdfDocument.Page mypage1 = myPdfDocument.startPage(mypageInfo1);
        Canvas canvas = mypage1.getCanvas();

        // Draw text on the canvas
        canvas.drawText("Welcome To Karad", 40, 50, myPaint);

        // Finish the page
        myPdfDocument.finishPage(mypage1);

        // Define the file path and name in the app-specific directory
        File file = new File(getExternalFilesDir(null), "invoice.pdf");

        try {
            // Write the document content to the file
            myPdfDocument.writeTo(new FileOutputStream(file));
            Log.d(TAG, "PDF written to " + file.getAbsolutePath());
        } catch (IOException e) {
            Log.e(TAG, "Error writing PDF to file", e);
        } finally {
            // Close the document
            myPdfDocument.close();
        }
    }

    private void checkPermissionsAndCreatePdf() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
            } else {
                createNewPdf();
            }
        } else {
            createNewPdf();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                createNewPdf();
            } else {
                Log.e(TAG, "Permission denied to write to external storage");
            }
        }
    }

    private void createNewPdf() {
        PdfDocument myPdfDocument = new PdfDocument();
        Paint myPaint = new Paint();
        myPaint.setTextSize(16);
        myPaint.setColor(android.graphics.Color.BLACK);

        PdfDocument.PageInfo mypageInfo1 = new PdfDocument.PageInfo.Builder(1080, 720, 1).create();
        PdfDocument.Page mypage1 = myPdfDocument.startPage(mypageInfo1);
        Canvas canvas = mypage1.getCanvas();
        canvas.drawText("Welcome To Karad", 40, 50, myPaint);
        myPdfDocument.finishPage(mypage1);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            savePdfToDownloadsQAndAbove(myPdfDocument);
        } else {
            savePdfToDownloadsBelowQ(myPdfDocument);
        }

        myPdfDocument.close();
    }

    private void savePdfToDownloadsBelowQ(PdfDocument pdfDocument) {
        File downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File file = new File(downloadsDir, "invoice.pdf");

        try {
            FileOutputStream fos = new FileOutputStream(file);
            pdfDocument.writeTo(fos);
            fos.close();
            Log.d(TAG, "PDF written to " + file.getAbsolutePath());
        } catch (IOException e) {
            Log.e(TAG, "Error writing PDF to file", e);
        }
    }

    private void savePdfToDownloadsQAndAbove(PdfDocument pdfDocument) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, "invoice.pdf");
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf");
        contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS);

        Uri uri = getContentResolver().insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues);

        if (uri != null) {
            try {
                OutputStream outputStream = getContentResolver().openOutputStream(uri);
                if (outputStream != null) {
                    pdfDocument.writeTo(outputStream);
                    outputStream.close();
                    Log.d(TAG, "PDF written to " + uri.toString());
                }
            } catch (IOException e) {
                Log.e(TAG, "Error writing PDF to file", e);
            }
        } else {
            Log.e(TAG, "Failed to create new MediaStore record.");
        }
    }


    public static Bitmap loadBitmapFromView(View v, int width, int height) {
        Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.draw(c);

        return b;
    }


    private void createPdf() {
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        //  Display display = wm.getDefaultDisplay();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        float hight = displaymetrics.heightPixels;
        float width = displaymetrics.widthPixels;

        int convertHighet = (int) hight, convertWidth = (int) width;

//        Resources mResources = getResources();
//        Bitmap bitmap = BitmapFactory.decodeResource(mResources, R.drawable.screenshot);

        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(convertWidth, convertHighet, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();

        Paint paint = new Paint();
        canvas.drawPaint(paint);

        bitmap = Bitmap.createScaledBitmap(bitmap, convertWidth, convertHighet, true);

        paint.setColor(Color.BLUE);
        canvas.drawBitmap(bitmap, 0, 0, null);
        document.finishPage(page);

        // write the document content
        String targetPdf = "/sdcard/pdffrom_" + Invoice + ".pdf";
        Log.d("TAG", "createPdf: " + targetPdf);
        File filePath;
        filePath = new File(targetPdf);
        try {
            document.writeTo(new FileOutputStream(filePath));

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
        }

        // close the document
        document.close();
        Toast.makeText(this, "PDF is created!!!", Toast.LENGTH_SHORT).show();
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DownloadInvoiceActivity.this);
        alertDialogBuilder.setMessage("PDF is created!!!");
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("Open", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //openGeneratedPDF();
                        File file = new File("/sdcard/pdffrom_" + Invoice + ".pdf");
                        Intent viewPdf = new Intent(Intent.ACTION_VIEW);
                        viewPdf.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        Uri URI = FileProvider.getUriForFile(DownloadInvoiceActivity.this, DownloadInvoiceActivity.this.getApplicationContext().getPackageName() + ".provider", file);
                        viewPdf.setDataAndType(URI, "application/pdf");
                        viewPdf.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        startActivity(viewPdf);

                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}