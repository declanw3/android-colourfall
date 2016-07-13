package io.github.declanw3.colourfall;

import android.text.method.Touch;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by DEC on 7/6/2016.
 */
public class Utility
{
    public static void SetContentState(View v, int _visible)
    {
        v.setVisibility(_visible);
        if (v instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup)v;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View child = viewGroup.getChildAt(i);
                SetContentState(child, _visible);

                // TODO: Fade over time
            }
        }
    }
}
