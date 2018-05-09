package com.example.han.testtranslator;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.googlecode.tesseract.android.TessBaseAPI;

import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    //public static String GOOGLE_API_KEY = "AIzaSyAeRI1ySyvjH-SZXcLPR8fRofgPNC9XKXE";
    public static final String TESS_DATA = "/tessdata";
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String DATA_PATH = Environment.getExternalStorageDirectory().toString() + "/Tess";

    private TessBaseAPI baseApi;
    private String recognizedText;
    private Bitmap bitmap;
    private TextToSpeech tts;

    private Button ttsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //android.speech.RecognizerIntent



        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.hello);
        baseApi = new TessBaseAPI();
// DATA_PATH = Path to the storage
// lang = for which the language data exists, usually "eng"


        String dataPath = getExternalFilesDir("/").getPath() + "/";
        Log.d(TAG, "onCreate: FilePath" + dataPath);
        baseApi.init(dataPath, "eng");

        Log.d("ERROR", "extractText: " + "FAILED TO BE READ");
// Eg. baseApi.init("/mnt/sdcard/tesseract/tessdata/eng.traineddata", "eng");
        baseApi.setImage(bitmap);
        recognizedText = baseApi.getUTF8Text();
        baseApi.end();
        Log.d(TAG, "onCreate: " + recognizedText);




        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.UK);
                }
            }
        });


        wireWidgets();
        ttsButton.setText(recognizedText);
        setListeners();


//        GoogleAPI.setHttpReferrer("https://accounts.google.com/o/oauth2/auth");
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

    private void wireWidgets() {
        ttsButton = (Button) findViewById(R.id.button_tts);
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

