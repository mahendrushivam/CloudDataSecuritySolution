package com.example.shivam.datcompressorfinal;

import android.gesture.Gesture;

/**
 * Created by Shivam on 2/25/2017.
 */

public class GestureHolder {


    private Gesture gesture;
    private String gestureName;

    public GestureHolder(Gesture gesture, String name){
        this.gesture = gesture;
        this.gestureName = name;
    }

    public Gesture getGesture(){
        return gesture;
    }

    public void setGesture(Gesture gesture){
        this.gesture = gesture;
    }

    public String getName(){
        return gestureName;
    }

    public void setName(String name){
        this.gestureName = name;
    }
}
