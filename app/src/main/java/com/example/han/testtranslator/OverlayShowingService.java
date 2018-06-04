package com.example.han.testtranslator;

/**
 * Created by per6 on 5/29/18.
 */
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.media.ImageReader;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.security.Key;

public class OverlayShowingService extends Service implements OnTouchListener, OnClickListener, View.OnKeyListener {

    private View topLeftView, rootView;

    private EditText optionButton;
    private ImageReader mImageReader;
    private float offsetX;
    private float offsetY;
    private int originalXPos;
    private int originalYPos;
    private boolean moving;
    private WindowManager wm;
    private LinearLayout rootContent;

    public static final String WORD_TO_SEARCH = "This is the word you're searching";

    private View overlayedButton;

    private MediaProjectionManager mediaProjectionManager;
    private MediaProjection mediaProjection;
    private int mDensity, mWidth, mHeight;
    private Display mDisplay;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {

        super.onCreate();

        wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);

        mediaProjectionManager = (MediaProjectionManager) getSystemService(MEDIA_PROJECTION_SERVICE);

        overlayedButton = LayoutInflater.from(this).inflate(R.layout.overlay, null);
        overlayedButton.setOnTouchListener(this);
        overlayedButton.setBackgroundColor(0x55fe4444);
        overlayedButton.setOnClickListener(this);
        overlayedButton.findViewById(R.id.dictionaryEnter).setOnClickListener(this);
        overlayedButton.findViewById(R.id.dictionarySearch).setOnClickListener(this);

        InputMethodManager inputManager = (InputMethodManager) this.
                getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputManager != null) {
            inputManager.toggleSoftInput(0, InputMethodManager.SHOW_IMPLICIT);
        }



        //optionButton = new Button(this);
        //optionButton.setText("Options");
        //optionButton.setOnTouchListener(this);
        //optionButton.setBackgroundColor(0x55fe4444);
        //optionButton.setOnClickListener(this);


        WindowManager.LayoutParams params = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY, LayoutParams.FLAG_NOT_TOUCH_MODAL, PixelFormat.TRANSLUCENT);
        params.gravity = Gravity.LEFT | Gravity.TOP;
        params.x = 0;
        params.y = 0;
        wm.addView(overlayedButton, params);
        //wm.addView(optionButton, params);





        topLeftView = new View(this);
        WindowManager.LayoutParams topLeftParams = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, LayoutParams.TYPE_APPLICATION_OVERLAY, LayoutParams.FLAG_NOT_FOCUSABLE | LayoutParams.FLAG_NOT_TOUCH_MODAL, PixelFormat.TRANSLUCENT);
        topLeftParams.gravity = Gravity.LEFT | Gravity.TOP;
        topLeftParams.x = 0;
        topLeftParams.y = 0;
        topLeftParams.width = 0;
        topLeftParams.height = 0;
        wm.addView(topLeftView, topLeftParams);



        //rootView =

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (overlayedButton != null) {
            wm.removeView(overlayedButton);
            //wm.removeView(optionButton);
            wm.removeView(topLeftView);
            overlayedButton = null;
            //optionButton = null;
            topLeftView = null;
        }
    }



    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            float x = event.getRawX();
            float y = event.getRawY();

            moving = false;

            int[] location = new int[2];
            overlayedButton.getLocationOnScreen(location);
            //optionButton.getLocationOnScreen(location);

            originalXPos = location[0];
            originalYPos = location[1];



            offsetX = originalXPos - x;
            offsetY = originalYPos - y;

        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            int[] topLeftLocationOnScreen = new int[2];
            topLeftView.getLocationOnScreen(topLeftLocationOnScreen);

            System.out.println("topLeftY=" + topLeftLocationOnScreen[1]);
            System.out.println("originalY=" + originalYPos);

            float x = event.getRawX();
            float y = event.getRawY();

            WindowManager.LayoutParams params = (LayoutParams) overlayedButton.getLayoutParams();

            int newX = (int) (offsetX + x);
            int newY = (int) (offsetY + y);

            if (Math.abs(newX - originalXPos) < 1 && Math.abs(newY - originalYPos) < 1 && !moving) {
                return false;
            }

            params.x = newX - (topLeftLocationOnScreen[0]);
            params.y = newY - (topLeftLocationOnScreen[1]);

            wm.updateViewLayout(overlayedButton, params);
            //wm.updateViewLayout(optionButton, params);

            moving = true;
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            if (moving) {
                return true;
            }
        }

        return false;
    }
    private void showScreenShotImage(Bitmap b) {
        //imageView.setImageBitmap(b);
    }


    /*  Method which will take screenshot on Basis of Screenshot Type ENUM  */
    private void takeScreenshot() {





        //switch (screenshotType) {
        //case FULL:
        //If Screenshot type is FULL take full page screenshot i.e our root content.
        //b = com.example.han.testtranslator.ScreenshotUtils.getScreenShot(topLeftView);
        //break;
        //case CUSTOM:
        //If Screenshot type is CUSTOM

        //fullPageScreenshot.setVisibility(View.INVISIBLE);//set the visibility to INVISIBLE of first button
        //hiddenText.setVisibility(View.VISIBLE);//set the visibility to VISIBLE of hidden text

        //b = com.example.summers.overlay.ScreenshotUtils.getScreenShot(rootContent);

        //After taking screenshot reset the button and view again
        //fullPageScreenshot.setVisibility(View.VISIBLE);//set the visibility to VISIBLE of first button again
        //hiddenText.setVisibility(View.INVISIBLE);//set the visibility to INVISIBLE of hidden text

        //NOTE:  You need to use visibility INVISIBLE instead of GONE to remove the view from frame else it wont consider the view in frame and you will not get screenshot as you required.
        //break;


        //If bitmap is not null
       // if (b != null) {
            //showScreenShotImage(b);//show bitmap over imageview

//            File saveFile = com.example.han.testtranslator.ScreenshotUtils.getMainDirectoryName(this);//get the path to save screenshot
//            File file = com.example.han.testtranslator.ScreenshotUtils.store(b, "screenshot"  + ".jpg", saveFile);//save the screenshot to selected path
            Toast.makeText(this, "Screenshot Success", Toast.LENGTH_SHORT).show();
            //shareScreenshot(file);//finally share screenshot
        //} else
            //If bitmap is null show toast message
          //  Toast.makeText(this, "failed", Toast.LENGTH_SHORT).show();

    }



    @Override
    public void onClick(View v) {
        Log.d("clicked", "onClick: Clicked");
        EditText yourEditText= (EditText) overlayedButton.findViewById(R.id.dictionaryEnter);
        switch (v.getId()){
            case R.id.dictionaryEnter:


                yourEditText.requestFocus();

                Log.d("Tryna Keyboard", "onClick: ");
                break;
            case R.id.dictionarySearch:
                Log.d("Tryna Search", "onClick: ");
                EditText editText = overlayedButton.findViewById(R.id.dictionaryEnter);
                String hello = editText.getText().toString();
                stopSelf();
                Intent toSearch = new Intent(this, MainActivity.class);
                toSearch.putExtra(WORD_TO_SEARCH, hello);
                startActivity(toSearch);
                break;
            default:
                yourEditText.clearFocus();
        }

        //Toast.makeText(this, "No u", Toast.LENGTH_SHORT).show();
        //stopSelf();
        //switch (v.getId()) {
        //case R.id.full_page_screenshot:
        // takeScreenshot();

        //break;
        //case R.id.custom_page_screenshot:
        //takeScreenshot(ScreenshotType.CUSTOM);
        //break;

    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        return true;
    }
}
