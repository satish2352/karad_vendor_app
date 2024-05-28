package com.example.karadvenderapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
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
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.karadvenderapp.MyLib.Constants;
import com.example.karadvenderapp.MyLib.PdfSaveCallback;
import com.example.karadvenderapp.MyLib.Shared_Preferences;
import com.example.karadvenderapp.NetworkController.SimpleArcDialog;
import com.example.karadvenderapp.R;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


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
    PdfSaveCallback  callback;

    private ExecutorService executor = Executors.newSingleThreadExecutor();
    private SimpleArcDialog mDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invoice);
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

      //checkPermissionsAndCreatePdf();
        mDialog = new SimpleArcDialog(DownloadInvoiceActivity.this);
        mDialog.setCancelable(false);

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

                //createNewPdf();
                createSplitNewPdf();
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
                createSplitNewPdf();
            }
        } else {
           createSplitNewPdf();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
               createSplitNewPdf();
            } else {
                Log.e(TAG, "Permission denied to write to external storage");
            }
        }
    }
    private void createSplitNewPdf() {
        // Create a new PDF document
        try {
            mDialog.show();
            PdfDocument myPdfDocument = new PdfDocument();
            Paint myPaint = new Paint();
            myPaint.setTextSize(16);
            myPaint.setColor(Color.BLACK);

            // Capture the entire activity screenshot
            Bitmap screenshot = captureFullScreen(DownloadInvoiceActivity.this);

            // Page dimensions and margins
            int pageWidth = screenshot.getWidth(); // Set page width to screenshot width
            int pageHeight = screenshot.getHeight(); // Set page height to screenshot height
            int topMargin = 100; // Adjust the top margin as needed

            // Create a single page
            PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create();
            PdfDocument.Page page = myPdfDocument.startPage(pageInfo);
            Canvas canvas = page.getCanvas();

            // Draw the screenshot on the PDF with top margin
            canvas.drawBitmap(screenshot, 0, 0, myPaint);

            myPdfDocument.finishPage(page);

            // Save the PDF document
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                savePdfToDownloadsQAndAbove(myPdfDocument, new PdfSaveCallback() {
                    @Override
                    public void onPdfSaved(String filePath) {

                        mDialog.dismiss();
                        Toast.makeText(DownloadInvoiceActivity.this, "Invoice Saved ; "+filePath, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(Exception e) {
                        mDialog.dismiss();
                    }
                });
            } else {
                savePdfToDownloadsBelowQ(myPdfDocument, new PdfSaveCallback() {
                    @Override
                    public void onPdfSaved(String filePath) {


                        mDialog.dismiss();
                        Toast.makeText(DownloadInvoiceActivity.this, "File Saved ; "+filePath, Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onError(Exception e) {
                        mDialog.dismiss();
                    }
                });
            }

            // Close the document
            myPdfDocument.close();
        } catch (Exception e) {
            mDialog.dismiss();
           Log.d("mytag" ,"Exception ",e);
           e.printStackTrace();

        }
    }

    private Bitmap captureFullScreen(Activity activity) {
        // Get the root view of the activity
        View rootView = activity.findViewById(R.id.linear_layout);

        // Measure the root view with the desired width and height
        rootView.measure(View.MeasureSpec.makeMeasureSpec(rootView.getWidth(), View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

        // Layout the view with the new dimensions
        rootView.layout(0, 0, rootView.getMeasuredWidth(), rootView.getMeasuredHeight());

        // Create a bitmap with the measured dimensions
        Bitmap bitmap = Bitmap.createBitmap(rootView.getMeasuredWidth(), rootView.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        // Draw the view onto the canvas
        rootView.draw(canvas);

        return bitmap;
    }


    private void savePdfToDownloadsBelowQ(PdfDocument pdfDocument) {
        // Get the current date and format it with hours, minutes, and seconds
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy_HHmmss", Locale.getDefault());
        String currentDate = sdf.format(new Date());

        // Create the filename with the timestamp
        String fileName = "invoice_" + currentDate + ".pdf";

        // Get the Downloads directory
        File downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File file = new File(downloadsDir, fileName);

        try {
            // Write the PDF to the file
            FileOutputStream fos = new FileOutputStream(file);
            pdfDocument.writeTo(fos);
            fos.close();
            Log.d(TAG, "PDF written to " + file.getAbsolutePath());
        } catch (IOException e) {
            Log.e(TAG, "Error writing PDF to file", e);
        }
    }
    private void savePdfToDownloadsBelowQ(final PdfDocument pdfDocument, final PdfSaveCallback callback) {


                // Get the current date and format it with hours, minutes, and seconds
                SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy_HHmmss", Locale.getDefault());
                String currentDate = sdf.format(new Date());

                // Create the filename with the timestamp
                final String fileName = "invoice_" + currentDate + ".pdf";

                // Get the Downloads directory
                File downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                final File file = new File(downloadsDir, fileName);

                try {
                    // Write the PDF to the file
                    FileOutputStream fos = new FileOutputStream(file);
                    pdfDocument.writeTo(fos);
                    fos.close();
                    Log.d(TAG, "PDF written to " + file.getAbsolutePath());

                    // Execute the success callback on the UI thread
                    if (callback != null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                callback.onPdfSaved(Environment.DIRECTORY_DOWNLOADS+"/"+fileName);
                            }
                        });
                    }
                } catch (final IOException e) {
                    Log.e(TAG, "Error writing PDF to file", e);
                    // Execute the error callback on the UI thread
                    if (callback != null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                callback.onError(e);
                            }
                        });
                    }
                }


    }

    private void savePdfToDownloadsQAndAbove(PdfDocument pdfDocument,PdfSaveCallback callback) {

                SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy_HHmmss", Locale.getDefault());
                String currentDate = sdf.format(new Date());

                // Create the filename with the timestamp
                String fileName = "invoice_" + currentDate + ".pdf";
                ContentValues contentValues = new ContentValues();
                contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName);
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



                            if (callback != null) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        callback.onPdfSaved(Environment.DIRECTORY_DOWNLOADS+"/"+fileName);
                                    }
                                });
                            }
                        }
                    } catch (IOException e) {
                        Log.e(TAG, "Error writing PDF to file", e);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                callback.onError(e);
                            }
                        });

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

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}