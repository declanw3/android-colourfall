package io.github.declanw3.colourfall;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * Created by DEC on 6/17/2016.
 */
public class PaletteView extends LinearLayout
{
    private ArrayList<SampleView> samples;
    private ArrayList<Rect> sampleBounds;

    private TranslateAnimation moveAnim;
    private boolean sampleHeld;
    private float relativeY;

    private SampleView focusedSample;

    private int sampleCount = 5;

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
        sampleBounds = new ArrayList<>();

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        for(int i = 0; i < sampleCount; ++i)
        {
            inflater.inflate(R.layout.view_sample, this);
            samples.add((SampleView)this.getChildAt(i));
            samples.get(i).Index(i);

            TouchRunnable onSampleTouched = new TouchRunnable() {
                @Override
                public void run() {
                }
                public void OnTouch(View v, MotionEvent event)
                {
                    OnSampleTouched(v, event);
                }
            };
            samples.get(i).SetOnSampleTouched(onSampleTouched);

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

    public void InitialiseBounds()
    {
        SampleView example = samples.get(0);
        for(int i = 0; i < sampleCount; ++i)
        {
            sampleBounds.add(new Rect(0,
                    example.getHeight() * i,
                    example.getWidth(),
                    (example.getHeight() * i) + example.getHeight()));
        }
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

    public void SetContentState(int _contentState)
    {
        for(SampleView sv: samples)
        {
            sv.SetContentState(_contentState);
        }
    }

    private void OnSampleTouched(View v, MotionEvent event)
    {
        switch(event.getAction()) {
            case MotionEvent.ACTION_MOVE: {
                if(sampleHeld) {
                    float centerY = v.getHeight() * 0.5f;
                    relativeY += event.getY() - centerY;
                    float touchY = relativeY + centerY;

                    for(int i = 0; i < sampleCount; ++i)
                    {
                        SampleView s = samples.get(i);
                        if(i != focusedSample.Index())
                        {
                            if(((int)touchY > sampleBounds.get(i).top) && ((int)touchY < sampleBounds.get(i).bottom))
                            {
                                SampleView sv = GetSample(i);
                                ValueAnimator anim = ObjectAnimator.ofFloat(sv, "y", sv.getY(), sampleBounds.get(focusedSample.Index()).top);
                                anim.start();

                                sv.Index(focusedSample.Index());
                                focusedSample.Index(i);

                                break;
                            }
                        }
                    }

                    focusedSample.setY(relativeY);
                }
            }
            break;
            case MotionEvent.ACTION_DOWN: {
                focusedSample = (SampleView)v;

                relativeY = v.getY();
            }
            break;
            default:break;
        }
    }

    private SampleView GetSample(int _index)
    {
        SampleView sv = null;
        for(int i = 0; i < sampleCount; ++i)
        {
            if(samples.get(i).Index() == _index)
            {
                sv = samples.get(i);
            }
        }
        return sv;
    }

    private boolean OnMoveTouched(View v, MotionEvent event)
    {
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                sampleHeld = true;
                focusedSample.setY(relativeY);

                return true;
            }
            case MotionEvent.ACTION_UP: {
                sampleHeld = false;
                relativeY = 0;


                int index = focusedSample.Index();
                ValueAnimator anim = ObjectAnimator.ofFloat(focusedSample, "y", focusedSample.getY(), sampleBounds.get(index).top);
                anim.start();

                return true;
            }
            default:break;
        }
        return false;
    }
}
