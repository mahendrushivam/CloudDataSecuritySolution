package com.example.shivam.datcompressorfinal;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
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


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment2 extends Fragment {

    private Unbinder unbinder;

    public BlankFragment2() {
        // Required empty public constructor
    }


    ViewPager viewPager;

    SmartTabLayout viewPagerTab;
    View v;
    //FragmentPagerItemAdapter adapter;
    private static final String[] fragmentClasses = {"com.example.shivam.datcompressorfinal.MainLayoutFragment", "com.example.shivam.datcompressorfinal.CloudStorageFragment"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_blank_fragment2, container, false);
        viewPager=(ViewPager)v.findViewById(R.id.viewpager);
        //viewPager.setAdapter(pageAdapter);
        //viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(new FragmentStatePagerAdapter(getChildFragmentManager()) {

            @Override
            public Fragment getItem(int position) {

                Fragment fragmentAtPosition = null;

                // $$$$ This is the Important Part $$$$$
                // Check to make sure that your array is not null, size is greater than 0 , current position is greater than equal to 0, and position is less than length
                if((fragmentClasses != null) && (fragmentClasses.length > 0)&&(position >= 0)&& (position < fragmentClasses.length))
                {
                    // Instantiate the Fragment at the current position of the Adapter
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



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.mainmenu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings: {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                Fragment fragment = new ExpandableFragment();
                transaction.replace(R.id.fragmentcontainer, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
                return true;

            }
            case R.id.home: {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                Fragment fragment = new BlankFragment2();
                transaction.replace(R.id.fragmentcontainer, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }




}
