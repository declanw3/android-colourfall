package io.github.declanw3.colourfall;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by DEC on 6/17/2016.
 */
public class PaletteView extends LinearLayout {

    private ArrayList<SampleView> samples;
    private int focusedSample = -1;

    public PaletteView(Context context) {
        super(context);
    }

    public PaletteView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        init(getContext());
    }

    private void init(Context context) {
        samples = new ArrayList<>();

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        for(int i = 0; i < 5; ++i)
        {
            inflater.inflate(R.layout.view_sample, this);
            samples.add((SampleView)this.getChildAt(i));
            samples.get(i).Index(i);

            OnClickListener onSampleClicked = new OnClickListener() {
                @Override
                public void onClick(View v) {
                    OnSampleClicked(v);
                }
            };
            samples.get(i).SetOnSampleClicked(onSampleClicked);

            OnTouchListener onMoveTouched = new OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return OnMoveTouched(v, event);
                }
            };
            samples.get(i).SetOnMoveTouched(onMoveTouched);
        }

        // TODO: Orientation change
    }

    public void RandomiseColors()
    {
        for(SampleView s : samples)
        {
            if(!s.LockedState())
            {
                s.UpdateSample();
            }
        }
    }

    public void SetContentState(boolean _visible)
    {
        for(SampleView s : samples)
        {
            for(int i = 0; i < s.getChildCount(); ++i) {
                s.SetContentState(s.getChildAt(i), _visible);
            }
        }
    }

    private boolean OnMoveTouched(View v, MotionEvent event)
    {
        switch(event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                // No longer down
                Log.d("MOTION", "ACTION_UP");
                return true;
            case MotionEvent.ACTION_DOWN:
                // Do something
                Log.d("MOTION", "ACTION_DOWN");
                return true;
            case MotionEvent.ACTION_UP:
                // No longer down
                Log.d("MOTION", "ACTION_UP");
                return true;
            case MotionEvent.ACTION_CANCEL:
                Log.d("MOTION", "ACTION_CANCEL");
                return true;
        }
        return false;
    }

    private void OnSampleClicked(View v)
    {
//        int previousSample = focusedSample;
//        SampleView clickedSample = (SampleView)v;
//        focusedSample = clickedSample.Index();
//        if((previousSample >= 0) && (previousSample < samples.size()))
//        {
//            samples.get(previousSample).SetFocus(false);
//        }
//
//        clickedSample.SetFocus(true);
    }
}
