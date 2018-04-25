package com.example.han.testtranslator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


public class MainActivity extends AppCompatActivity {

    public static String GOOGLE_API_KEY = "AIzaSyAeRI1ySyvjH-SZXcLPR8fRofgPNC9XKXE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Tesseract tesseract = new Tesseract();

        String text = tesseract.extractText();

        Log.d("TRANSLATION", "onCreate: " + text);


//        GoogleAPI.setHttpReferrer("https://accounts.google.com/o/oauth2/auth");
//        setContentView(R.layout.activity_main);
//        GoogleAPI.setKey(GOOGLE_API_KEY);
//        String translatedText = null;
//        try {
//            translatedText = Translate.DEFAULT.execute("Bonjour le monde", Language.FRENCH, Language.ENGLISH);
//        } catch (GoogleAPIException e) {
//            Log.d("ERROR", "onCreate: " + Log.getStackTraceString(e));
//        }
//        Log.d("TEXT", "onCreate: " + translatedText);
//        }


    }
}

