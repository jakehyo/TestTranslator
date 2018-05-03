package com.example.han.testtranslator;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.googlecode.tesseract.android.TessBaseAPI;


public class MainActivity extends AppCompatActivity {

    public static String GOOGLE_API_KEY = "AIzaSyAeRI1ySyvjH-SZXcLPR8fRofgPNC9XKXE";
    public static final String TESS_DATA = "/tessdata";
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String DATA_PATH = Environment.getExternalStorageDirectory().toString() + "/Tess";

    private TessBaseAPI tessBaseAPI;
    private Uri outputFileDir;
    private String mCurrentPhotoPath;

    private String recognizedText;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //android.speech.RecognizerIntent

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.hello);
        TessBaseAPI baseApi = new TessBaseAPI();
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
        Log.d(TAG, "onCreate: "+ recognizedText);


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

