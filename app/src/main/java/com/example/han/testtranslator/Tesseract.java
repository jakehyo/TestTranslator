package com.example.han.testtranslator;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.googlecode.tesseract.android.TessBaseAPI;

/**
 * Created by per6 on 4/25/18.
 */

public class Tesseract {

    private String recognizedText;
    private Bitmap bitmap;


    public Tesseract()
    {
        recognizedText = "Has not translated";
        bitmap = BitmapFactory.decodeFile("/raw/bitmap.bmp");
    }

    public String extractText()
    {
        TessBaseAPI baseApi = new TessBaseAPI();
// DATA_PATH = Path to the storage
// lang = for which the language data exists, usually "eng"
        baseApi.init("/res/raw/eng.traineddata", "eng");
// Eg. baseApi.init("/mnt/sdcard/tesseract/tessdata/eng.traineddata", "eng");
        baseApi.setImage(bitmap);
        recognizedText = baseApi.getUTF8Text();
        baseApi.end();
        return recognizedText;
    }
}
