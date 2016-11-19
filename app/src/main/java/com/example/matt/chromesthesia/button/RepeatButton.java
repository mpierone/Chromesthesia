package com.example.matt.chromesthesia.button;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;

import com.example.matt.chromesthesia.R;
import com.example.matt.chromesthesia.enums.Repeat;
/**
 * Created by Dave on 10/24/2016.
 */

public class RepeatButton extends ImageButton {
    public interface RepeatListener {
        void onOne();
        void onAll();
        void onNone();
    }
    private Repeat state;
    private RepeatListener stateListener;

    public RepeatButton(Context context) {
        super(context);
    }
    public RepeatButton (Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int next = (state.ordinal()+1) % Repeat.values().length;
                setState(Repeat.values()[next]);
                performRepeatClick();
            }
        });
        //INIT STATE
        setState(Repeat.ALL);
    }
    private void performRepeatClick() {
        if(stateListener == null) {
            return;
        }
        switch(state){
            case ALL:
                stateListener.onAll();
                break;
            case ONE:
                stateListener.onOne();
                break;
            case NONE:
                stateListener.onNone();
                break;
        }
    }
    private void createDrawableState() {
        switch (state) {
            case ALL:
                setImageResource(R.drawable.all);
                break;
            case ONE:
                setImageResource(R.drawable.one);
                break;
            case NONE:
                setImageResource(R.drawable.none);
                break;
        }
    }


    public Repeat getState() {
        return state;
    }

    public void setState(Repeat newstate) {
        if(newstate == null)return;
        this.state = newstate;
        createDrawableState();

    }

    public RepeatListener getRepeatListener() {
        return stateListener;
    }

    public void setRepeatListener(RepeatListener newListener) {
        this.stateListener = newListener;
    }

}
