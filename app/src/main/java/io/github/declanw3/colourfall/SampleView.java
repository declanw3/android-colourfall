package io.github.declanw3.colourfall;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

/**
 * Created by DEC on 6/18/2016.
 */
public class SampleView extends RelativeLayout{
    private EditView editView;
    private Button editButton;
    private Button moveButton;
    private Button lockButton;
    private TextView colorText;

    private int colorCurrent;
    private ValueAnimator colorAnim;

    private int index = -1;
    public int Index() {  return index; }
    public void Index(int _index) { this.index = _index; }

    private boolean editState = false;

    private boolean lockedState = false;
    public boolean LockedState() {  return lockedState; }
    public void LockedState(boolean _lockedState) { this.lockedState = _lockedState; }

    public void SetOnSampleClicked(OnClickListener _onSampleClicked) {
        this.setOnClickListener(_onSampleClicked);
    }
    public void SetOnEditClicked(OnClickListener _onEditClicked) {
        this.editButton.setOnClickListener(_onEditClicked);
    }
    public void SetOnMoveTouched(OnTouchListener _onMoveTouched) {
        this.moveButton.setOnTouchListener(_onMoveTouched);
    }
    public void SetOnLockClicked(OnClickListener _onLockClicked) {
        this.lockButton.setOnClickListener(_onLockClicked);
    }

    public SampleView(Context context) {
        super(context);
    }

    public SampleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        init(getContext());
    }

    private void init(Context context) {
        editView = (EditView) this.findViewById(R.id.editView);
        editButton = (Button)this.findViewById(R.id.editButton);
        moveButton = (Button)this.findViewById(R.id.moveButton);
        lockButton = (Button)this.findViewById(R.id.lockButton);
        colorText = (TextView)this.findViewById(R.id.colorText);

        // Start edit view as hidden
        editView.setScaleX(0.0f);
        editView.EditState(false);
        editView.setVisibility(GONE);
        // Set pivot to scale from the left
        editView.setPivotX(0.0f);

        OnClickListener onEditClicked = new OnClickListener() {
            @Override
            public void onClick(View v) {
                OnEditClicked();
            }
        };
        SetOnEditClicked(onEditClicked);

        OnClickListener onLockClicked = new OnClickListener() {
            @Override
            public void onClick(View v) {
                OnLockClicked();
            }
        };
        SetOnLockClicked(onLockClicked);

        colorAnim = ValueAnimator.ofObject(new ArgbEvaluator(), Color.WHITE, Color.WHITE);
        colorAnim.setDuration(300);
        colorAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                OnBackgroundColorUpdate(animator);
            }

        });

        UpdateLockButton();
        UpdateColorText();
    }

    public void UpdateSample()
    {
        RandomiseColor();
        UpdateColorText();
    }

    private void RandomiseColor()
    {
        Random rnd = new Random();
        int colorFrom = this.colorCurrent;
        int colorTo = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));

        colorAnim.setObjectValues(colorFrom, colorTo);
        colorAnim.start();
    }

    private void UpdateColorText()
    {
        int color = Color.TRANSPARENT;
        Drawable background = this.getBackground();
        if (background instanceof ColorDrawable) {
            color = ((ColorDrawable) background).getColor();
        }

        String rbg = String.format("RGB(%1$s,%2$s,%3$s)", Color.red(color), Color.green(color), Color.blue(color));
        colorText.setText(rbg);
    }

    public void SetContentState(View v, boolean _visible)
    {
        v.setVisibility(_visible ? VISIBLE : INVISIBLE);
        if (v instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup)v;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View child = viewGroup.getChildAt(i);
                SetContentState(child, _visible);

                // TODO: Fade over time
            }
        }
    }

    private void UpdateLockButton() {
        if(LockedState())
        {
            lockButton.setBackgroundColor(Color.argb(255, 200, 200, 200));
        }
        else
        {
            lockButton.setBackgroundColor(Color.argb(70, 200, 200, 200));
        }
    }

    private void OnBackgroundColorUpdate(ValueAnimator animator)
    {
        this.colorCurrent = (int)animator.getAnimatedValue();
        setBackgroundColor(this.colorCurrent);
        UpdateColorText();
    }

    private void OnEditClicked() {
        editView.ToggleState();
    }

    private void OnLockClicked() {
        LockedState(!LockedState());
        UpdateLockButton();
    }
}
