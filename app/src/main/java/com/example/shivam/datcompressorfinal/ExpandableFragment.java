package com.example.shivam.datcompressorfinal;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;

import com.diegocarloslima.fgelv.lib.FloatingGroupExpandableListView;
import com.diegocarloslima.fgelv.lib.WrapperExpandableListAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class ExpandableFragment extends Fragment {

SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    BaseExpandableListAdapter myAdapter;
    ExpandableListView par;
    FloatingGroupExpandableListView myList;
    int child=-1;
    public ExpandableFragment() {
        // Required empty public constructor
    }

int count;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_expandable, container, false);
        sharedPreferences=PreferenceManager.getDefaultSharedPreferences(getContext());
        count=sharedPreferences.getInt(MainLayoutFragment.MEMORY_CHOICE,0);
        myList = (FloatingGroupExpandableListView) v.findViewById(R.id.my_list);
        myAdapter = new SampleAdapter(getContext(),count);
        WrapperExpandableListAdapter wrapperAdapter = new WrapperExpandableListAdapter(myAdapter);
        myList.setAdapter(wrapperAdapter);
        myList.setDividerHeight(0);
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getContext());

        myList.setOnGroupClickListener(new FloatingGroupExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if (groupPosition == 1)
                {
                    FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                    FragmentTransaction transaction=fragmentManager.beginTransaction();
                    Fragment fragment=new ProgressLoader();
                    transaction.replace(R.id.fragmentcontainer,fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                    return true;
                }





                return false;
            }
        });
        myList.setOnChildClickListener(new FloatingGroupExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                View v1,v2;

boolean flag=false;
                    if (groupPosition == 0) {
                            count=childPosition;
                        SampleAdapter adapter=new SampleAdapter(getContext(),childPosition);
                        adapter.updateposition(childPosition);
                        adapter.notifyDataSetChanged();
                        sharedPreferences=PreferenceManager.getDefaultSharedPreferences(getActivity());
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.putInt(MainLayoutFragment.MEMORY_CHOICE,childPosition);
                        editor.commit();

                        WrapperExpandableListAdapter wrapperAdapter = new WrapperExpandableListAdapter(adapter);
                        wrapperAdapter.notifyDataSetChanged();
                        myList.setAdapter(wrapperAdapter);
                        myList.setDividerHeight(0);
                        //myList.collapseGroup(groupPosition);
                    }

                return true;

            }});

return  v;
    }
}
