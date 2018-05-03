package com.example.han.testtranslator;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

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
        bitmap = BitmapFactory.decodeFile("/Users/per6/Desktop/TestTranslator/app/src/main/res/raw/bitmap.bmp");
    }

    public String extractText()
    {
        TessBaseAPI baseApi = new TessBaseAPI();
// DATA_PATH = Path to the storage
// lang = for which the language data exists, usually "eng"
      if (isExternalStorageReadable()==true)
      {
          Log.d("ERROR", "extractText: " + "READ");
          baseApi.init("/sdcard/tesseract/tessdata/eng.traineddata", "eng");
      }
      else
      {
          Log.d("ERROR", "extractText: " + "FAILED TO BE READ");
      }
// Eg. baseApi.init("/mnt/sdcard/tesseract/tessdata/eng.traineddata", "eng");
        baseApi.setImage(bitmap);
        recognizedText = baseApi.getUTF8Text();
        baseApi.end();
        return recognizedText;
    }

    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }



}
