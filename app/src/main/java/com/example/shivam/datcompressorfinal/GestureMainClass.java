package com.example.shivam.datcompressorfinal;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;


public class GestureMainClass extends AppCompatActivity {
    FragmentManager fragmentManager;
    Fragment fragment;
    FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture_main_class);
        fragmentManager=getSupportFragmentManager();
        transaction=fragmentManager.beginTransaction();
        fragment=new MainLayoutFragment();
        transaction.replace(R.id.fragmentcontainer,fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }
}
