package io.github.declanw3.colourfall;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by DEC on 6/18/2016.
 */
public class SampleView extends LinearLayout{
    private boolean locked = false;
    public boolean Locked() {  return locked; }
    public void Locked(boolean locked) { this.locked = locked; }

    public SampleView(Context context) {
        super(context);
        init(context);
    }

    public SampleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {

    }

}
