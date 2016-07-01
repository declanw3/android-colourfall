package io.github.declanw3.colourfall;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by DEC on 7/1/2016.
 */
public class ChannelView extends LinearLayout
{

    public ChannelView(Context context) {
        super(context);
    }

    public ChannelView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ChannelView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        init(getContext());
    }

    private void init(Context context) {
        // TODO: Min max number input
    }
}
