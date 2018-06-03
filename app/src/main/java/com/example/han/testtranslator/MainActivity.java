package com.example.han.testtranslator;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
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

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.han.testtranslator.CopyAssets.copyFile;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final int WRITE_REQUEST_CODE = 101;

    private TessBaseAPI baseApi;
    private String recognizedText;
    private Bitmap bitmap;
    private TextToSpeech tts;
    private Button ttsButton;
    private DefinitionList definitions;
    private String wordDefinition;

    private CopyAssets c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        requestPermissions(permissions, WRITE_REQUEST_CODE);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WordsAPI.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WordsAPI api = retrofit.create(WordsAPI.class);

        Call<DefinitionList> call = api.getDefinitions("base");


        call.enqueue(new Callback<DefinitionList>() {
            @Override
            public void onResponse(Call<DefinitionList> call, Response<DefinitionList> response) {
                List<Definition> defs = response.body().getDefinitions();
                for (Definition def: defs)
                {
                    wordDefinition += " " + def.getDefinition();
                }

                Log.d("IT WORKED", "onResponse: " + defs.toString());
            }

            @Override
            public void onFailure(Call<DefinitionList> call, Throwable t) {

                //Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onFailure: "+ t.getMessage());
            }
        });

        Log.d(TAG, "AHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH: " + getExternalFilesDir("/").getPath() + "/");
        Log.d(TAG, "BRUHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH: " + Environment.getExternalStorageDirectory()+"/www/resources/");
        Log.d(TAG, "DOES EXTERNAL STORAGE EXIST FOR MEEEEEEEEEEEEEEEEEEEEEEEE: " + Environment.getExternalStorageState());
        Log.d(TAG, "IS EXTERNAL STORAGE REMOVEABBLLLLLLLLLLLLLLLLLLLLLE: " + Environment.isExternalStorageRemovable());
        Log.d(TAG, "IS EXTERNAL STORAGE EMULATEDDDDDDDDDDDDDDDDDDDDDD: " + Environment.isExternalStorageEmulated());

        c = new CopyAssets();
        c.copyAssets(getApplicationContext());


        wireWidgets();

        imageToString();

        initTTS();

        ttsButton.setText(recognizedText);

        setListeners();

        Intent svc = new Intent(this, OverlayShowingService.class);
        startService(svc);
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
                String toSpeak = wordDefinition;
                Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
                HashMap<String,String> param = new HashMap<>();
                tts.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, param);
            }
        });
    }



    private void imageToString() {
        //String dataPath = getExternalFilesDir("/").getPath() + "/";
        String dataPath = Environment.getExternalStorageDirectory()+"/www/resources/";
        Log.d(TAG, "WHAT IS THE FILEPATH: " + dataPath);
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
                else{
                    Toast.makeText(MainActivity.this, "ERROR HAS OCCURED WITH TEXT TO SPEECH SERVICE", Toast.LENGTH_SHORT).show();
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

    protected void onDestroy() {


        //Close the Text to Speech Library
        if(tts != null) {

            tts.stop();
            tts.shutdown();
            Log.d(TAG, "TTS Destroyed");
        }
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case WRITE_REQUEST_CODE:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //Granted.


                }
                else{
                    //Denied.
                }
                break;
        }
    }

}

