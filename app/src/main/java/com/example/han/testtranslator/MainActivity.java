package com.example.han.testtranslator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.api.GoogleAPI;
import com.google.api.GoogleAPIException;
import com.google.api.translate.Language;
import com.google.api.translate.Translate;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Set the HTTP referrer to your website address.

        // Set the Google Translate API key
        // See: http://code.google.com/apis/language/translate/v2/getting_started.html
        GoogleAPI.setKey("AIzaSyAeRI1ySyvjH-SZXcLPR8fRofgPNC9XKXE");

        String translatedText = null;
        try {
            translatedText = Translate.DEFAULT.execute("Bonjour le monde", Language.FRENCH, Language.ENGLISH);
        } catch (GoogleAPIException e) {
            e.printStackTrace();
            Log.d("ERROR", "-_-");
        }

        Log.d("OnStart", "onCreate: " + translatedText);



    }
}
