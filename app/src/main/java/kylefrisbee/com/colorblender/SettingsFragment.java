package kylefrisbee.com.colorblender;


import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
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
    public static final String COLOR_1_BUTTON = "COLOR_1";
    public static final String COLOR_2_BUTTON = "COLOR_2";
    public static final String SLIDER = "SLIDER";



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
                getColorFromPicker(COLOR_1_BUTTON);
            }
        });

        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getColorFromPicker(COLOR_2_BUTTON);
            }
        });

        mSliderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getColorFromPicker(SLIDER);
            }
        });
    }

    private void getColorFromPicker(String component) {
        getArguments().putString("COMPONENT",component);
        getFragmentManager().popBackStack();
        Intent i = new Intent(Intent.ACTION_RUN);
        i.setComponent(new ComponentName("com.kylefrisbie.colorpicker.app", "MainActivity.java"));
        getActivity().startActivityForResult(i, Activity.RESULT_OK);
    }
}
