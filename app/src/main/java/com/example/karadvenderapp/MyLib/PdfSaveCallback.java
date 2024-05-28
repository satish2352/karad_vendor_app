package com.example.karadvenderapp.MyLib;

public interface PdfSaveCallback {
    void onPdfSaved(String filePath);
    void onError(Exception e);
}