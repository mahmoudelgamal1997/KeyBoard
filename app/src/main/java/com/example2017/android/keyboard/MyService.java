package com.example2017.android.keyboard;

import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.media.AudioManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputConnection;

public class MyService extends InputMethodService implements KeyboardView.OnKeyboardActionListener {


    private KeyboardView keyboardView;
    private Keyboard keyboard;

    //to change between different Keyboards (Mode_Change)
    int FlagToModeChange =1;
    int FlagToShift=1;
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
                // KEYCODE_MODE_CHANGE =-2
            case Keyboard.KEYCODE_MODE_CHANGE:

                if (FlagToModeChange ==1) {
                    keyboard = new Keyboard(this, R.xml.symbol);
                    keyboardView.setKeyboard(keyboard);
                    FlagToModeChange++;
                }else{
                    keyboard = new Keyboard(this, R.xml.qwerty);
                    keyboardView.setKeyboard(keyboard);
                    FlagToModeChange = 1;
                }

                break;



            //Action when Delete
            // ASCII Code for KEYCODE_DELETE = -5
            case Keyboard.KEYCODE_DELETE:
                ic.deleteSurroundingText(1,0);
                break;

            //Action when Done
            case Keyboard.KEYCODE_DONE:
                ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN,KeyEvent.KEYCODE_ENTER));

                break;

            //Action when Shift to get upper case
            case Keyboard.KEYCODE_SHIFT:

                if (FlagToModeChange ==2 &&FlagToShift==1 ){
                    keyboard = new Keyboard(this, R.xml.symbol_shift);
                    keyboardView.setKeyboard(keyboard);
                    FlagToShift++;
                }else {
                    keyboard = new Keyboard(this, R.xml.symbol);
                    keyboardView.setKeyboard(keyboard);
                    FlagToShift=1;
                }

                isCaps=!isCaps;
                keyboard.setShifted(isCaps);
                keyboardView.invalidateAllKeys();
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
