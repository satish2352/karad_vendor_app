package com.example.karadvenderapp.MyLib;

import android.net.Uri;

public interface PdfSaveCallback {
    void onPdfSaved(String filePath, Uri uri);
    void onError(Exception e);
}