package io.github.declanw3.colourfall;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by DEC on 6/17/2016.
 */
public class AppActivity extends Activity{
    private PaletteView paletteView;
    private Button randomiseButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);

        paletteView = (PaletteView)findViewById(R.id.paletteView);

        randomiseButton = (Button)findViewById(R.id.randomiseButton);
        if(randomiseButton != null) {
            randomiseButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Do something in response to button click
                    paletteView.randomiseColors();
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
