package io.github.declanw3.colourfall;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by DEC on 6/17/2016.
 */
public class PaletteView extends LinearLayout {

    private ArrayList<SampleView> samples;

    public PaletteView(Context context) {
        super(context);
        init(context);
    }

    public PaletteView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        samples = new ArrayList<>();

        LayoutInflater inflater = LayoutInflater.from(context);

        for(int i = 0; i < 5; ++i)
        {
            inflater.inflate(R.layout.view_sample, this);
            samples.add((SampleView)this.getChildAt(i));
        }

        samples.get(0).setBackgroundColor(Color.parseColor("#000000"));
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
}
