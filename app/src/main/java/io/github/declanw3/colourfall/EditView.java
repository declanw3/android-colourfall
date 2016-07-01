package io.github.declanw3.colourfall;

import android.animation.Animator;
import android.animation.FloatEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by DEC on 7/1/2016.
 */
public class EditView extends LinearLayout
{
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

    public void ToggleState()
    {
        EditState(!EditState());
        float targetScale = EditState() ? 1.0f : 0.0f;

        scaleAnim.setObjectValues(this.getScaleX(), targetScale);
        scaleAnim.start();
    }
}
