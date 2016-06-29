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
    private int activeSample = -1;

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
            View test = inflater.inflate(R.layout.view_sample, this);
            samples.add((SampleView)this.getChildAt(i));
            samples.get(i).Index(i);

            OnTouchListener onMoveTouched = new OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return OnMoveTouched(v, event);
                }
            };
            samples.get(i).SetOnMoveTouched(onMoveTouched);
        }
    }

    public void randomiseColors()
    {
        for(SampleView s : samples)
        {
            if(!s.Locked())
            {
                Random rnd = new Random();
                int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
                s.setBackgroundColor(color);
            }
        }
    }

    private boolean OnMoveTouched(View v, MotionEvent event)
    {
        Log.d("onTouch", "ENTRY");
        switch(event.getAction()) {
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
}
