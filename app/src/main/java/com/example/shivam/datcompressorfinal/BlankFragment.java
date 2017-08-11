package com.example.shivam.datcompressorfinal;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.ogaclejapan.smarttablayout.SmartTabLayout;

import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends Fragment {

    private Unbinder unbinder;
    public BlankFragment() {
        // Required empty public constructor
    }

    ViewPager viewPager;
    SmartTabLayout viewPagerTab;
    View v;

    private static final String [] fragmentClasses = {"com.example.shivam.datcompressorfinal.MainLayoutFragment","com.example.shivam.datcompressorfinal.CloudStorageFragment"};
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setHasOptionsMenu(true);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v=inflater.inflate(R.layout.fragment_blank, container, false);
        viewPager=(ViewPager)v.findViewById(R.id.viewpager);

        viewPager.setAdapter(new FragmentStatePagerAdapter(getChildFragmentManager()) {

            @Override
            public Fragment getItem(int position) {

                Fragment fragmentAtPosition = null;


                if((fragmentClasses != null) && (fragmentClasses.length > 0)&&(position >= 0)&& (position < fragmentClasses.length))
                {

                    fragmentAtPosition = Fragment.instantiate(getActivity(), fragmentClasses[position]);
                    fragmentAtPosition.setRetainInstance(true);

                }

                return fragmentAtPosition;
            }

            @Override
            public int getCount() {
                return fragmentClasses.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0:
                        return "Storage";
                    case 1:
                        return "Category";

                }
                return null;
            }
        });
        viewPagerTab = (SmartTabLayout)v.findViewById(R.id.viewpagertab);


        viewPagerTab.setViewPager(viewPager);
        //viewPagerTab.setOnPageChangeListener(mPageChangeListener);





        return v;


    }






}
