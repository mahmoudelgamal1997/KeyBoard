package com.example2017.android.keyboard;

import android.app.Service;
import android.content.Intent;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.media.AudioManager;
import android.os.IBinder;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputConnection;

public class MyService extends InputMethodService implements KeyboardView.OnKeyboardActionListener {


    private KeyboardView keyboardView;
    private Keyboard keyboard;

    private boolean isCaps=false;


    @Override
    public View onCreateInputView() {

        keyboardView=(KeyboardView)getLayoutInflater().inflate(R.layout.keyboard,null);
        keyboard=new Keyboard(this,R.xml.qwerty);
        keyboardView.setKeyboard(keyboard);
        keyboardView.setOnKeyboardActionListener(this);

        return keyboardView;
    }

    @Override
    public void onPress(int i) {

    }

    @Override
    public void onRelease(int i) {

    }

    @Override
    public void onKey(int i, int[] ints) {

        InputConnection ic= getCurrentInputConnection();
        playclick(i);
        switch(i){

            //Action when Delete
            case Keyboard.KEYCODE_DELETE:
                ic.deleteSurroundingText(1,0);
                Log.e("caps","Delete");
                break;

            //Action when Done
            case Keyboard.KEYCODE_DONE:
                ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN,KeyEvent.KEYCODE_ENTER));
                Log.e("caps","DONE");

                break;

            //Action when Shift to get upper case
            case Keyboard.KEYCODE_SHIFT:
                isCaps=!isCaps;
                keyboard.setShifted(isCaps);
                keyboardView.invalidateAllKeys();
                Log.e("caps","SHIFT");
                break;


            //when any Character pressed
            default:
                char code = (char) i;
                if(Character.isLetter(code) && isCaps){
                    code = Character.toUpperCase(code);
                }
                ic.commitText(String.valueOf(code), 1);


        }

    }

    private void playclick(int i) {

        AudioManager am= (AudioManager)getSystemService(AUDIO_SERVICE);
        switch (i){
            case 32:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_SPACEBAR);
                break;
            case Keyboard.KEYCODE_DONE:
            case  10:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_RETURN);
                break;
            case Keyboard.KEYCODE_DELETE:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_DELETE);
                break;

            default:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_STANDARD);
        }

    }


    @Override
    public void onText(CharSequence charSequence) {

    }

    @Override
    public void swipeLeft() {

    }

    @Override
    public void swipeRight() {

    }

    @Override
    public void swipeDown() {

    }

    @Override
    public void swipeUp() {

    }
}