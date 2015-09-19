package kylefrisbee.com.colorblender;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

public class HomeActivity extends AppCompatActivity {
    private ActionBar mActionBar;
    private Button mColor1;
    private Button mColor2;
    private SeekBar mSlider;
    private int mRedValue;
    private int mGreenValue;
    private int mBlueValue;
    private Bundle mBundle;
    private RelativeLayout mSquareColor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mActionBar = getActionBar();
        mColor1 = (Button) findViewById(R.id.color1_button);
        mColor2 = (Button) findViewById(R.id.color2_button);
        mSlider = (SeekBar) findViewById(R.id.seekBar);
        mSquareColor = (RelativeLayout) findViewById(R.id.MainLayout);

        addListeners();
        mBundle = savedInstanceState;
    }

    private void addListeners() {
        mColor1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getColor(SettingsFragment.COLOR_1_BUTTON);
            }
        });

        mColor2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getColor(SettingsFragment.COLOR_2_BUTTON);
            }
        });

        mSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                blendColors(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void getColor(String component) {
        mBundle.putString("COMPONENT", component);
        Intent i = new Intent(Intent.ACTION_RUN);
        i.setComponent(new ComponentName("com.kylefrisbie.colorpicker.app", "MainActivity.java"));
        startActivityForResult(i, RESULT_OK);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.MainLayout, new SettingsFragment());
            ft.commit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RESULT_OK) {
            if (data != null) {
                mRedValue = data.getIntExtra("R", -1);
                mGreenValue = data.getIntExtra("G", -1);
                mBlueValue = data.getIntExtra("B", -1);
                changeColor(mBundle.getString("COMPONENT", ""));
            }
        }
    }

    /**
     * Changed the color of the component the user requested based off the color they chose with the color picker
     * @param component - the widget to change
     */
    private void changeColor(String component) {
        switch(component){
            case SettingsFragment.COLOR_1_BUTTON:
                mColor1.setBackgroundColor(Color.rgb(mRedValue, mGreenValue, mBlueValue));
                break;
            case SettingsFragment.COLOR_2_BUTTON:
                mColor2.setBackgroundColor(Color.rgb(mRedValue, mGreenValue, mBlueValue));
                break;
            case SettingsFragment.SLIDER:
                mSlider.setBackgroundColor(Color.rgb(mRedValue, mGreenValue, mBlueValue));
                break;

        }
    }

    private int blendColors(float ratio) {
        int color1 = mColor1.getSolidColor();
        int color2 = mColor2.getSolidColor();
        final float inverseRation = 1f - ratio;
        float r = (Color.red(color1) * ratio) + (Color.red(color2) * inverseRation);
        float g = (Color.green(color1) * ratio) + (Color.green(color2) * inverseRation);
        float b = (Color.blue(color1) * ratio) + (Color.blue(color2) * inverseRation);
        return Color.rgb((int) r, (int) g, (int) b);
    }

}
