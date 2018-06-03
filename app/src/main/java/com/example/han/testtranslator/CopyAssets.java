package com.example.han.testtranslator;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class CopyAssets {

    public CopyAssets (){
    }
    public void copyAssets(Context context) {

        AssetManager assetManager = context.getAssets();
        String[] files = null;
        try {
            files = assetManager.list("");
            Log.d("tag", "IS FILES NULL?: "+ (files.length==0));
        } catch (IOException e) {
            Log.d("tag", "Failed to get asset file list." + Log.getStackTraceString(e));
        }
        // initialize File object
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/www/resources/tessdata");
        // check if the pathname already exists
        // if not create it
        if(!file.exists()){
            // create the full path name
            boolean result = file.mkdir();
            if(result){
                Log.d("tag", "copyAssets: " + "Successfully created "+file.getAbsolutePath());
            }
            else{
                Log.d("tag", "copyAssets: " + "Failed creating "+file.getAbsolutePath());
            }
        }else{
            Log.d("tag", "copyAssets: " + "Pathname already exists");
        }



        if (files != null) for (String filename : files) {
            InputStream in = null;
            OutputStream out = null;
            try {
                in = assetManager.open(filename);
                out = new FileOutputStream(Environment.getExternalStorageDirectory()+"/www/resources/tessdata/" + filename);
                copyFile(in, out);
            } catch(IOException e) {
                Log.d("tag", "Failed to copy asset file: " + filename + Log.getStackTraceString(e));
            }
            finally {
                if (in != null) {
                    try {
                        in.close();
                        in = null;
                    } catch (IOException e) {

                    }
                }
                if (out != null) {
                    try {
                        out.flush();
                        out.close();
                        out = null;
                    } catch (IOException e) {

                    }
                }
            }
        }
    }

    public static void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1){
            out.write(buffer, 0, read);
        }
    }

}