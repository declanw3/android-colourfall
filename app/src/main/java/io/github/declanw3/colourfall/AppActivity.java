package io.github.declanw3.colourfall;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

/**
 * Created by DEC on 6/17/2016.
 */
public class AppActivity extends Activity{
    private PaletteView paletteView;
    private ToolsView toolsView;

    private boolean contentState = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);

        paletteView = (PaletteView)findViewById(R.id.paletteView);
        toolsView = (ToolsView)findViewById(R.id.toolsView);

        View.OnClickListener onRandomiseClicked = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnRandomiseClicked(v);
            }
        };
        toolsView.SetOnRandomiseClicked(onRandomiseClicked);
        View.OnClickListener onToggleClicked = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnToggleClicked(v);
            }
        };
        toolsView.SetOnToggleClicked(onToggleClicked);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    private void OnRandomiseClicked(View v)
    {
        this.paletteView.RandomiseColors();
    }
    private void OnToggleClicked(View v)
    {
        contentState = !contentState;
        this.paletteView.SetContentState(contentState);
    }
}
