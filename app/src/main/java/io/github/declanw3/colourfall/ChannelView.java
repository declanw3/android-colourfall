package io.github.declanw3.colourfall;

import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.AttributeSet;
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

        colorEdit.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "255"), new InputFilter.LengthFilter(3)} );
        colorEdit.addTextChangedListener(new TextWatcher() {
            private CharSequence previous = "";

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String newColor = s.toString();

                if(!newColor.equals(previous.toString())) {
                    int val = 0;
                    if (!newColor.equals("")) {
                        val = Integer.parseInt(newColor);
                    }

                    int color = colorModel.getValue();
                    color &= ~(0xFF << (channelIndex * 8));
                    color |= (val << (channelIndex * 8));

                    previous = s;
                    colorModel.setValue(color);
                }

                colorEdit.setSelection(colorEdit.getText().length());
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
