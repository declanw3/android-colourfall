package io.github.declanw3.colourfall;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * Created by DEC on 6/27/2016.
 */
public class ToolsView extends LinearLayout{
    private Button randomiseButton;
    private Button expandButton;
    private Button toggleButton;

    public void SetOnRandomiseClicked(OnClickListener _onRandomiseClicked) {
        this.randomiseButton.setOnClickListener(_onRandomiseClicked);
    }
    public void SetOnExpandClicked(OnClickListener _onExpandClicked) {
        this.expandButton.setOnClickListener(_onExpandClicked);
    }
    public void SetOnToggleClicked(OnClickListener _onToggleClicked) {
        this.toggleButton.setOnClickListener(_onToggleClicked);
    }

    public ToolsView(Context context) {
        super(context);
    }

    public ToolsView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        init(getContext());
    }

    private void init(Context context)
    {
        this.randomiseButton = (Button)this.findViewById(R.id.randomiseButton);
        this.expandButton = (Button)this.findViewById(R.id.expandButton);
        this.toggleButton = (Button)this.findViewById(R.id.toggleButton);
    }
}
