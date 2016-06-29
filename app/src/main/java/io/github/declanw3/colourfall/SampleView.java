package io.github.declanw3.colourfall;

import android.content.Context;
import android.graphics.Color;
import android.os.Debug;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

/**
 * Created by DEC on 6/18/2016.
 */
public class SampleView extends RelativeLayout{
    private Button moveButton;
    private Button lockButton;

    private int index = -1;
    public int Index() {  return index; }
    public void Index(int _index) { this.index = _index; }

    private boolean locked = false;
    public boolean Locked() {  return locked; }
    public void Locked(boolean _locked) { this.locked = _locked; }

    public void SetOnMoveTouched(OnTouchListener _onMoveTouched) {
        this.moveButton.setOnTouchListener(_onMoveTouched);
    };
    public void SetOnLockClicked(OnClickListener _onLockClicked) {
        this.lockButton.setOnClickListener(_onLockClicked);
    };

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
        moveButton = (Button)this.findViewById(R.id.moveButton);
        lockButton = (Button)this.findViewById(R.id.lockButton);

        OnClickListener onLockClicked = new OnClickListener() {
            @Override
            public void onClick(View v) {
                OnLockClicked();
            }
        };
        SetOnLockClicked(onLockClicked);

        UpdateLockButton();
    }

    private void OnLockClicked() {
        Locked(!Locked());
        UpdateLockButton();
    }

    private void UpdateLockButton() {
        if(Locked())
        {
            lockButton.setBackgroundColor(Color.argb(255, 200, 200, 200));
        }
        else
        {
            lockButton.setBackgroundColor(Color.argb(70, 200, 200, 200));
        }
    }
}
