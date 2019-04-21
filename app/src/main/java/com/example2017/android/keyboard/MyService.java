package com.example2017.android.keyboard;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.media.AudioManager;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputConnection;

public class MyService extends InputMethodService implements KeyboardView.OnKeyboardActionListener {


    private KeyboardView keyboardView;
    private Keyboard keyboard;
    int BackgroundImages[] = new int[]{R.drawable.ramos, R.drawable.marcelo, R.drawable.reallogo, R.drawable.team};

    int ii = 0;
    //to change between different Keyboards (Mode_Change)
    boolean FlagToModeChange = false;
    //  boolean FlagToShift=false;
    private boolean isCaps = false;
    boolean english = true;
    boolean arabic = false;
    char[] arabicChars = {'٠', '١', '٢', '٣', '٤', '٥', '٦', '٧', '٨', '٩'};

    public View onCreateInputView() {

        keyboardView = (KeyboardView) getLayoutInflater().inflate(R.layout.keyboard, null);
        keyboard = new Keyboard(this, R.xml.arabic_letter);
        keyboardView.setKeyboard(keyboard);
        keyboardView.setOnKeyboardActionListener(this);


        Log.e("start","start");

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

        InputConnection ic = getCurrentInputConnection();
        playclick(i);
        switch (i) {
            // KEYCODE_MODE_CHANGE =-2
            case Keyboard.KEYCODE_MODE_CHANGE:

                if (FlagToModeChange == false) {
                    keyboard = new Keyboard(this, R.xml.symbol);
                    keyboardView.setKeyboard(keyboard);
                    FlagToModeChange = true;
                    isCaps = false;
                } else {
                    keyboard = new Keyboard(this, R.xml.qwerty);
                    keyboardView.setKeyboard(keyboard);
                    FlagToModeChange = false;
                }

                break;

/*
            //change language
            case Keyboard.EDGE_TOP:

                if (english) {
                    //change to arabic
                    keyboard = new Keyboard(this, R.xml.arabic_letter);
                    keyboardView.setKeyboard(keyboard);
                    arabic = true;
                    english = false;
                } else {
                    //change to english
                    keyboard = new Keyboard(this, R.xml.qwerty);
                    keyboardView.setKeyboard(keyboard);
                    arabic = false;
                    english = true;

                }
            break;

*/

                //to change background
            case Keyboard.EDGE_BOTTOM:
                if (ii < BackgroundImages.length) {
                    keyboardView.setBackgroundResource(BackgroundImages[ii]);
                    keyboardView.setKeyboard(new Keyboard(this,R.xml.qwerty));
                    ii++;

                    Log.e("change","change");
                } else {
                    ii = 0;
                    keyboardView.setBackgroundResource(BackgroundImages[ii]);
                    Log.e("change","change");

                }
                break;


            //Action when Delete
            // ASCII Code for KEYCODE_DELETE = -5
            case Keyboard.KEYCODE_DELETE:
                ic.deleteSurroundingText(1, 0);
                break;

            //Action when Done
            case Keyboard.KEYCODE_DONE:
                ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));

                break;

            //Action when Shift to get upper case
            case Keyboard.KEYCODE_SHIFT:

                if (!FlagToModeChange && !isCaps) {
                    isCaps = !isCaps;
                    keyboard.setShifted(isCaps);
                    keyboardView.invalidateAllKeys();
                    break;
                } else if (!FlagToModeChange && isCaps) {
                    isCaps = !isCaps;
                    keyboard.setShifted(isCaps);
                    keyboardView.invalidateAllKeys();
                    break;
                } else if (FlagToModeChange && !isCaps) {
                    isCaps = true;
                    keyboard = new Keyboard(this, R.xml.symbol_shift);
                    keyboard.setShifted(isCaps);
                    keyboardView.setKeyboard(keyboard);
                    break;

                } else if (FlagToModeChange && isCaps) {
                    isCaps = false;
                    keyboard = new Keyboard(this, R.xml.symbol);
                    keyboardView.setKeyboard(keyboard);
                    keyboard.setShifted(isCaps);
                    break;

                }


                //when any Character pressed
            default:
                char code = (char) i;
                if (Character.isLetter(code) && isCaps) {
                    code = Character.toUpperCase(code);
                }


                if (arabic){
                    ic.commitText(String.valueOf(arabicToDecimal(code,arabicChars)), 1);

                }else {
                    ic.commitText(String.valueOf(code), 1);
                }
        }

    }

    private void playclick(int i) {

        AudioManager am = (AudioManager) getSystemService(AUDIO_SERVICE);
        switch (i) {
            case 32:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_SPACEBAR);
                break;
            case Keyboard.KEYCODE_DONE:
            case 10:
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


    private static char arabicToDecimal(char number, char[] arabicChars) {

        if (number == '0') {
            return arabicChars[0];
        } else if (number == '1') {
            return arabicChars[1];
        } else if (number == '2') {
            return arabicChars[2];
        } else if (number == '3') {
            return arabicChars[3];
        } else if (number == '4') {
            return arabicChars[4];
        } else if (number == '5') {
            return arabicChars[5];
        } else if (number == '6') {
            return arabicChars[6];
        } else if (number == '7') {
            return arabicChars[7];
        } else if (number == '8') {
            return arabicChars[8];
        } else if (number == '9') {
            return arabicChars[9];
        }
        return number;
    }
}