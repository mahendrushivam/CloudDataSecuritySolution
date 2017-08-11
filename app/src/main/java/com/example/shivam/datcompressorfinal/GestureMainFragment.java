package com.example.shivam.datcompressorfinal;


import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import me.itangqi.waveloadingview.WaveLoadingView;

/**
 * A simple {@link Fragment} subclass.
 */
public class GestureMainFragment extends Fragment {
    WaveLoadingView waveLoadingView;
GestureLibrary gestureLibrary;
    DataBaseHelper dataBaseHelper;
    private static final String TAG="GestureMainFragment";

    public GestureMainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_gesture_main, container, false);
        gestureLibrary= GestureLibraries.fromFile(getActivity().getExternalFilesDir(null) + "/" + "gesture.txt");
        gestureLibrary.load();
        GestureOverlayView gestures = (GestureOverlayView) v.findViewById(R.id.gestures);
        getActivity().openOptionsMenu();
        dataBaseHelper=new DataBaseHelper(getContext());

        gestures.addOnGesturePerformedListener(handleGestureListener);
        gestures.setGestureStrokeAngleThreshold(90.0f);
    return v;
    }

    private GestureOverlayView.OnGesturePerformedListener handleGestureListener = new GestureOverlayView.OnGesturePerformedListener() {
        @Override
        public void onGesturePerformed(GestureOverlayView gestureView,
                                       Gesture gesture) {
            //gestureLibrary= GestureLibraries.fromFile(getActivity().getExternalFilesDir(null) + "/" + "gesture.txt");
            ArrayList<Prediction> predictions = gestureLibrary.recognize(gesture);
            Log.d(TAG, "recognized");
            //if(predictions==null)
            boolean flag=false;
            for(int i=0;i<predictions.size();i++)
            {
                Prediction prediction=predictions.get(i);
                Log.e("PREDICTION NAME",prediction.name);
            flag=dataBaseHelper.checkgesturename(prediction.name);
                if(flag==true)
                    break;
            }
            //Log.d("ERROR degug",String.valueOf(predictions.size()));
//Log.e("STRING GESTURE",String.valueOf(flag));
            if (predictions.size() > 0 && flag==true) {

                Prediction prediction = predictions.get(0);


                if (prediction.score > 2.0) {
                    Toast.makeText(getActivity(), prediction.name,
                            Toast.LENGTH_SHORT).show();

                }
            }
        }
    };

}
