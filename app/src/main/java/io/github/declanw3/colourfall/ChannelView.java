package io.github.declanw3.colourfall;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;

/**
 * Created by DEC on 7/1/2016.
 */
public class ChannelView extends LinearLayout implements IntValueStore.IntValueStoreListener
{
    private SampleView sampleView;
    private IntValueStore colorModel;
    private EditText colorEdit;
    private SeekBar colorSeek;

    private int channelIndex;
    public void SetChannelIndex(int _channelIndex) { this.channelIndex = _channelIndex; }
    
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

    @Override
    public void onValueChanged(int newValue) {
        // Our color has been updated
        int color = newValue;
        color &= (0xFF << (channelIndex * 8));
        color = color >> (channelIndex * 8);
        colorSeek.setProgress(color);
        colorEdit.setText(String.format("%1$d", color));
    }

    private void init(Context context) {
        // TODO: Min max number input
        colorEdit = (EditText)this.findViewById(R.id.colorEdit);
        colorSeek = (SeekBar)this.findViewById(R.id.colorSeek);

        colorEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

//                String input = colorEdit.getText().toString();
//                if (!input.equals("")) {
//                    int val = Integer.parseInt(input);
//                }
//
//                int value = Integer.parseInt(colorEdit.getText().toString());
//                int color = colorModel.getValue();
//                color &= ~(0xFF << (channelIndex * 8));
//                color |= (value << (channelIndex * 8));
//                colorModel.setValue(color);
                Log.d("BEFORE", String.format("onTextChanged: %1$s", s));
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                Log.d("AFTER", String.format("onTextChanged: %1$s", s));
            }
        });
        colorSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Only send model update through manual editing
                if(fromUser) {
                    int color = colorModel.getValue();
                    color &= ~(0xFF << (channelIndex * 8));
                    color |= (progress << (channelIndex * 8));
                    colorModel.setValue(color);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void Initialise(SampleView _sampleView)
    {
        this.sampleView = _sampleView;
        this.colorModel = this.sampleView.GetColorModel();
        this.colorModel.addListener(this);
    }
}
