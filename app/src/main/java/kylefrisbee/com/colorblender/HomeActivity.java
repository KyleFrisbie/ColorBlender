package kylefrisbee.com.colorblender;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

/**
 * The main activity that will be used. It contains the widgets, and will call a fragment when settings is selected from the menu
 */
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

    private SensorManager mSensorManager;
    private Sensor mSensor;
    private float[] mValuesAcceleration;
    private float[] mRotationMatrix;
    private final SensorEventListener M_SENSOR_LISTNER = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            int progress;
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                System.arraycopy(event.values, 0, mValuesAcceleration, 0, 3);
                if (event.values[0] < 0) {
                    while ((event.values[0] < 0)) {
                        progress = mSlider.getProgress();
                        mSlider.setProgress(progress--);
                        blendColors(progress);
                    }
                } else if (event.values[0] > 0) {
                    while ((event.values[0] > 0)) {
                        progress = mSlider.getProgress();
                        mSlider.setProgress(progress++);
                        blendColors(progress);
                    }
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mActionBar = getActionBar();
        mColor1 = (Button) findViewById(R.id.color1_button);
        mColor2 = (Button) findViewById(R.id.color2_button);
        mSlider = (SeekBar) findViewById(R.id.seekBar);
        mSquareColor = (RelativeLayout) findViewById(R.id.MainLayout);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        mValuesAcceleration = new float[3];
        mRotationMatrix = new float[9];

        addListeners();
        mBundle = new Bundle();
        blendColors(50);
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

        mSensorManager.registerListener(M_SENSOR_LISTNER, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);

    }



    private void getColor(String component) {
        mBundle.putString("COMPONENT", component);
        Intent i = getPackageManager().getLaunchIntentForPackage("com.kylefrisbie.colorpicker.app");
        i.setFlags(0);
        startActivityForResult(i, 0);
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
//            FragmentTransaction ft = getFragmentManager().beginTransaction();
//            ft.replace(R.id.MainLayout, new SettingsFragment());
//            ft.commit();
//            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 0) {
            if (data != null) {
                mRedValue = data.getIntExtra("Red", -1);
                mGreenValue = data.getIntExtra("Green", -1);
                mBlueValue = data.getIntExtra("Blue", -1);
                changeColor(mBundle.getString("COMPONENT", ""));
                blendColors(50);
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

    private void blendColors(float ratio) {
        ratio = ratio/100;
        ColorDrawable buttonColor = (ColorDrawable) mColor1.getBackground();
        int color2 = buttonColor.getColor();
        ColorDrawable buttonColor2 = (ColorDrawable) mColor2.getBackground();
        int color1 = buttonColor2.getColor();
        final float inverseRation = 1 - ratio;
        float r = (Color.red(color1) * ratio) + (Color.red(color2) * inverseRation);
        float g = (Color.green(color1) * ratio) + (Color.green(color2) * inverseRation);
        float b = (Color.blue(color1) * ratio) + (Color.blue(color2) * inverseRation);
        mSquareColor.setBackgroundColor(Color.rgb((int) r, (int) g, (int) b));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
