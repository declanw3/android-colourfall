package io.github.declanw3.colourfall;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.text.method.Touch;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

/**
 * Created by DEC on 6/18/2016.
 */
public class SampleView extends RelativeLayout implements IntValueStore.IntValueStoreListener{
    private EditView editView;
    private Button editButton;
    private Button moveButton;
    private Button lockButton;
    private TextView colorText;

    private IntValueStore colorModel;
    private int colorCurrent;
    private ValueAnimator colorAnim;

    private TouchRunnable onSampleTouched;

    private int index = -1;
    public int Index() {  return index; }
    public void Index(int _index) { this.index = _index; }

    private boolean lockedState = false;
    public boolean LockedState() {  return lockedState; }
    public void LockedState(boolean _lockedState) { this.lockedState = _lockedState; }

    public void SetOnSampleTouched(TouchRunnable _onSampleTouched) {
        this.onSampleTouched = _onSampleTouched;
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

    @Override
    public void onValueChanged(int newValue) {
        // Our color has been updated
        this.colorCurrent = newValue;
        setBackgroundColor(this.colorCurrent);
        UpdateColorText();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        this.onSampleTouched.OnTouch(this, ev);

        return super.onInterceptTouchEvent(ev);
    }

    private void init(Context context) {
        colorModel = new IntValueStore(Color.WHITE);
        colorModel.addListener(this);

        editView = (EditView) this.findViewById(R.id.editView);
        editButton = (Button)this.findViewById(R.id.editButton);
        moveButton = (Button)this.findViewById(R.id.moveButton);
        lockButton = (Button)this.findViewById(R.id.lockButton);
        colorText = (TextView)this.findViewById(R.id.colorText);

        // Allow EditView to modify color value. (Unique to each sample)
        editView.Initialise(this);
        // Start edit view as hidden
        editView.setScaleX(0.0f);
        editView.EditState(false);
        editView.setVisibility(GONE);
        // Set pivot to scale from the left
        editView.setPivotX(0.0f);

        // Update all corresponding listeners
        colorModel.setValue(Color.WHITE);

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
        String rgb = String.format("RGB(%1$s,%2$s,%3$s)", Color.red(this.colorCurrent), Color.green(this.colorCurrent), Color.blue(this.colorCurrent));
        colorText.setText(rgb);
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

    public IntValueStore GetColorModel()
    {
        return this.colorModel;
    }

    public void SetContentState(int _contentState)
    {
        for (int i = 0; i < this.getChildCount(); i++) {
            View child = this.getChildAt(i);
            Utility.SetContentState(child, _contentState);
        }
    }

    private void OnBackgroundColorUpdate(ValueAnimator animator)
    {
        colorModel.setValue((int)animator.getAnimatedValue());
    }

    private void OnEditClicked() {
        editView.ToggleState();
    }

    private void OnLockClicked() {
        LockedState(!LockedState());
        UpdateLockButton();
    }
}
