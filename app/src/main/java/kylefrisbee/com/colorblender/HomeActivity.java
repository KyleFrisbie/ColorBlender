package kylefrisbee.com.colorblender;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.SeekBar;

public class HomeActivity extends AppCompatActivity {
    private ActionBar mActionBar;
    private Button mColor1;
    private Button mColor2;
    private SeekBar mSlider;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mActionBar = getActionBar();
        mColor1 = (Button) findViewById(R.id.color1_button);
        mColor2 = (Button) findViewById(R.id.color2_button);
        mSlider = (SeekBar) findViewById(R.id.seekBar);
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
}
