package com.example.shivam.datcompressorfinal;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.itangqi.waveloadingview.WaveLoadingView;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingMenu extends Fragment {

WaveLoadingView waveLoadingView;
    public static final String WAVE_OBJECT="WAVE_OBJECT";
    public SettingMenu()
    {

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting_menu, container, false);
    }

}
