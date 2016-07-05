package io.github.declanw3.colourfall;

import android.animation.Animator;
import android.animation.FloatEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * Created by DEC on 7/1/2016.
 */
public class EditView extends LinearLayout
{
    private ArrayList<ChannelView> channels;
    private ValueAnimator scaleAnim;

    private boolean editState = false;
    public boolean EditState() {  return editState; }
    public void EditState(boolean _editState)
    {
        this.editState = _editState;
    }

    public EditView(Context context) {
        super(context);
    }

    public EditView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EditView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        init(getContext());
    }

    private void init(Context context) {
        this.channels = new ArrayList<>();

        this.setBackgroundColor(Color.argb(127,0,0,0));

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        for(int i = 0; i < 3; ++i)
        {
            inflater.inflate(R.layout.view_channel, this);
            channels.add((ChannelView)this.getChildAt(i));
            channels.get(i).SetChannelIndex(2 - i);      // Reverse RGB due to bit ordering
        }
        scaleAnim = ValueAnimator.ofObject(new FloatEvaluator(), 0.0f, 0.0f);
        scaleAnim.setDuration(300);
        scaleAnim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                setVisibility(VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if(!EditState()) {
                    setVisibility(GONE);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        scaleAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                setScaleX((float)animator.getAnimatedValue());
            }
        });
    }

    public void Initialise(SampleView _sampleView)
    {
        for(ChannelView cv: channels)
        {
            cv.Initialise(_sampleView);
        }
    }

    public void ToggleState()
    {
        EditState(!EditState());
        float targetScale = EditState() ? 1.0f : 0.0f;

        scaleAnim.setObjectValues(this.getScaleX(), targetScale);
        scaleAnim.start();
    }
}
