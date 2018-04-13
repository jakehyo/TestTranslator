package com.example.han.testtranslator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.Translate.TranslateOption;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

public class MainActivity extends AppCompatActivity {

    private String recognizedText;
    public static String GOOGLE_API_KEY = "AIzaSyAeRI1ySyvjH-SZXcLPR8fRofgPNC9XKXE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        setContentView(R.layout.activity_main);

        // Instantiates a client
        Translate translate = TranslateOptions.getDefaultInstance().getService();

        // The text to translate
        String text = "Hello, world!";

        // Translates some text into Russian
        Translation translation =
                translate.translate(
                        text,
                        TranslateOption.sourceLanguage("en"),
                        TranslateOption.targetLanguage("ru"));

        Log.d("TEXT", "onCreateText: %s%n" + text);
        Log.d("TRANSLATED TEXT", "onCreateText: %s%n" + translation.getTranslatedText());




//        TessBaseAPI baseApi = new TessBaseAPI();
//// DATA_PATH = Path to the storage
//// lang = for which the language data exists, usually "eng"
//        baseApi.init(DATA_PATH, lang);
//// Eg. baseApi.init("/mnt/sdcard/tesseract/tessdata/eng.traineddata", "eng");
//        baseApi.setImage(bitmap);
//        recognizedText = baseApi.getUTF8Text();
//        baseApi.end();

        }



    }

