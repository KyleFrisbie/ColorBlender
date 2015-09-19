package kylefrisbee.com.colorblender;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class SettingsFragment extends Fragment {

    private Button mButton1;
    private Button mButton2;
    private Button mSliderButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mButton1 = (Button) getView().findViewById(R.id.button);
        mButton2 = (Button) getView().findViewById(R.id.button2);
        mSliderButton = (Button) getView().findViewById(R.id.button3);

        addListeners();
    }

    private void addListeners() {
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getColorFromPicker();
            }
        });

        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getColorFromPicker();
            }
        });

        mSliderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getColorFromPicker();
            }
        });
    }

    private void getColorFromPicker() {
        //getArguments().putBoolean("", true );
        //remove this fragment
        //go to color picker app and get color.
        //once color is determined, set it to one of the views
    }
}
