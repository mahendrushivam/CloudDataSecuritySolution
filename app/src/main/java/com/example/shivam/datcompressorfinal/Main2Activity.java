package com.example.shivam.datcompressorfinal;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import it.michelelacorte.elasticprogressbar.ElasticDownloadView;
import it.michelelacorte.elasticprogressbar.OptionView;

//import com.github.jlmd.animatedcircleloadingview.AnimatedCircleLoadingView;


public class Main2Activity extends AppCompatActivity {
   // private AnimatedCircleLoadingView animatedCircleLoadingView;
    FragmentManager fragmentManager;
    FragmentTransaction transaction;
    Fragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        fragmentManager=getSupportFragmentManager();
        transaction=fragmentManager.beginTransaction();
        fragment=new CloudStorageFragment();
        transaction.add(R.id.fragmentcontainer,fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }
}
