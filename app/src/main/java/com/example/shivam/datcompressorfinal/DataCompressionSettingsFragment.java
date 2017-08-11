package com.example.shivam.datcompressorfinal;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.diegocarloslima.fgelv.lib.FloatingGroupExpandableListView;
import com.diegocarloslima.fgelv.lib.WrapperExpandableListAdapter;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;


public class DataCompressionSettingsFragment extends Fragment {
    FloatingGroupExpandableListView compressetlist;
AlertDialog builder1,builder2;
    public static final String TEXTCOMP_QUALPERCENTAGE="TEXTCOMP_QUALPERCENTAGE";
    public static final String TEXTCOMP_TYPE="TEXTCOMP_TYPE";
    public static final String IMGCOMP_QUALPERCENTAGE="IMGCOMP_QUALPERCENTAGE";
    public static final String IMGCOMP_TYPE="TEXTCOMP_QUALPERCENTAGE";
    public static final String IMGCOMP_WIDTH="IMGCOMP_WIDTH";
    public static final String IMGCOMP_HEIGHT="IMGCOMP_HEIGHT";

    public DataCompressionSettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_data_compression_settings, container, false);
        compressetlist=(FloatingGroupExpandableListView)v.findViewById(R.id.compresssettlist);
        SettingsCompressionAdapter settingsCompressionAdapter=new SettingsCompressionAdapter(getContext());
        WrapperExpandableListAdapter wrapperExpandableListAdapter=new WrapperExpandableListAdapter(settingsCompressionAdapter);
        compressetlist.setAdapter(wrapperExpandableListAdapter);
        compressetlist.setDividerHeight(0);
        compressetlist.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {


                return false;
            }
        });

        compressetlist.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                if(childPosition==0 && groupPosition==0)
                {
                    LayoutInflater inflater =getActivity().getLayoutInflater();
                    final View dialogView = inflater.inflate(R.layout.settingcompgroup1, null);
                    builder1=new AlertDialog.Builder(getActivity())
                            .setIcon(R.drawable.settings)
                            .setTitle("Settings ")
                            .setView(dialogView)
                            .setCancelable(true)
                            .setPositiveButton("Set", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    DiscreteSeekBar seekBar=(DiscreteSeekBar)dialogView.findViewById(R.id.qualitypercentage);
                                    int width=seekBar.getProgress();
                                }
                            })
                            .setNegativeButton("Cancel", null)
                            .show();
                }
                else if(childPosition==1)
                {
                    LayoutInflater inflater =getActivity().getLayoutInflater();
                    final View dialogView = inflater.inflate(R.layout.settingscompgroup1lay2, null);
                    builder1=new AlertDialog.Builder(getActivity())
                            .setIcon(R.drawable.settings)
                            .setTitle("Settings ")
                            .setView(dialogView)
                            .setCancelable(true)
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .setNegativeButton("Cancel", null)
                            .show();

                }
                else if(childPosition==0 && groupPosition==1)
                {
                    LayoutInflater inflater =getActivity().getLayoutInflater();
                    final View dialogView = inflater.inflate(R.layout.settinglayoutgroup2, null);
                    builder1=new AlertDialog.Builder(getActivity())
                            .setIcon(R.drawable.settings)
                            .setTitle("Settings ")
                            .setView(dialogView)
                            .setCancelable(true)
                            .setPositiveButton("Set", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .setNegativeButton("Cancel", null)
                            .show();
                }

                return false;
            }
        });
    return v;
    }


}
