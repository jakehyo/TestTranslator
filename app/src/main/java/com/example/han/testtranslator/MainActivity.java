package com.example.han.testtranslator;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.googlecode.tesseract.android.TessBaseAPI;

import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private TessBaseAPI baseApi;
    private String recognizedText;
    private Bitmap bitmap;
    private TextToSpeech tts;
    private Button ttsButton;
    private String translatedText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        GoogleAPI.setHttpReferrer("https://translation.googleapis.com/language/translate/v2?key=AIzaSyAeRI1ySyvjH-SZXcLPR8fRofgPNC9XKXE");
//
//        // Set the Google Translate API key
//        // See: http://code.google.com/apis/language/translate/v2/getting_started.html
//        GoogleAPI.setKey("AIzaSyAeRI1ySyvjH-SZXcLPR8fRofgPNC9XKXE");
//
//        try {
//            translatedText = Translate.DEFAULT.execute("Bonjour le monde", Language.FRENCH, Language.ENGLISH);
//        } catch (GoogleAPIException e) {
//            Log.e(TAG, "onCreate: "+ e.getMessage());
//            Log.d(TAG, "RYANATOR: " + translatedText);
//        }



        wireWidgets();

        imageToString();

        initTTS();



        ttsButton.setText(recognizedText);

        setListeners();
    }



    private void wireWidgets() {
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.download);
        baseApi = new TessBaseAPI();
        ttsButton = (Button) findViewById(R.id.button_tts);
    }



    private void setListeners() {
        ttsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSpeak = recognizedText;
                Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
                tts.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            }
        });
    }



    private void imageToString() {
        String dataPath = getExternalFilesDir("/").getPath() + "/";
        baseApi.init(dataPath, "eng");
        //baseApi.init("
        baseApi.setImage(bitmap);
        recognizedText = baseApi.getUTF8Text();
        baseApi.end();
        Log.d(TAG, "onCreateMagic: " + recognizedText);
    }



    private void initTTS() {
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.UK);
                }
            }
        });
    }



    @Override
    protected void onPause(){
        if(tts !=null){
            tts.stop();
            tts.shutdown();
        }
        super.onPause();
    }
}

