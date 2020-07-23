package com.example.profilecomparator;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends Fragment
{
    TextView slideHeading,heading1,heading2,heading3,heading4,heading5,heading6;
    RelativeLayout divider;
    static float d,SCREEN_HEIGHT,SCREEN_WIDTH;


    public AboutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        d = this.getResources().getDisplayMetrics().density;

        SCREEN_WIDTH=getActivity().getWindowManager().getDefaultDisplay().getWidth();
        SCREEN_HEIGHT=getActivity().getWindowManager().getDefaultDisplay().getHeight();

        View view=inflater.inflate(R.layout.about_fragment_layout,container,false);

        Bundle bundle=getArguments();
        String num=Integer.toString(bundle.getInt("count"));
        //MainActivity.setHeadingBackground(Integer.parseInt(num));
        return view;
    }
}
