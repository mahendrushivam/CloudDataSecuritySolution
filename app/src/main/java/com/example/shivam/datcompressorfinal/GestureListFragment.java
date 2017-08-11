package com.example.shivam.datcompressorfinal;


import android.content.Context;
import android.content.DialogInterface;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;


/**
 * A simple {@link Fragment} subclass.
 */
public class GestureListFragment extends Fragment {
    private static final String TAG = "GestureListActivity";
    private String gesturename,navgesname;
    FragmentManager fragmentManager;
    FragmentTransaction transaction;
    Fragment fragment;
    Button addgesture,testgesture;
    private ListView gesturelistview;
    private static ArrayList<GestureHolder> gestlist;
    private GestureArrayAdapter gestureArrayAdapter;
    private GestureLibrary gestureLibrary;
ViewGroup container,placeholder;
    LayoutInflater inflater;
    public GestureListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public void menuoptions(View view)
    {
        LinearLayout linearLayout=(LinearLayout)view.getParent().getParent();
        TextView txt=(TextView)linearLayout.findViewById(R.id.gesturenameref);
        gesturename=txt.getText().toString();
        PopupMenu popup = new PopupMenu(getActivity(), view);
        popup.getMenuInflater().inflate(R.menu.gestureoptions, popup.getMenu());
        popup.show();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.inflater=inflater;
        this.container=container;
        getActivity().openOptionsMenu();
        View v=inflater.inflate(R.layout.fragment_gesture_list, container, false);
        placeholder=(ViewGroup)v;
        gesturelistview=(ListView)v.findViewById(R.id.gesturelist);
        //gestlist=new ArrayList<GestureHolder>();
        getgestureslist();
        registerForContextMenu(gesturelistview);

        addgesture=(Button)v.findViewById(R.id.addgesture);
        testgesture=(Button)v.findViewById(R.id.testgesture);


        return v;
    }

    private void getgestureslist() {
        try {
            gestlist = new ArrayList<GestureHolder>();
            gestureLibrary = GestureLibraries.fromFile(getActivity().getExternalFilesDir(null) + "/" + "gesture.txt");
            gestureLibrary.load();
            Set<String> gestureSet = gestureLibrary.getGestureEntries();
            for(String gestureNaam: gestureSet){
                ArrayList<Gesture> list = gestureLibrary.getGestures(gestureNaam);
                for(Gesture g : list) {
                    gestlist.add(new GestureHolder(g, gestureNaam));
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void savegesture() {
        ArrayList<Gesture> list = gestureLibrary.getGestures(gesturename);
        if (list.size() > 0) {
            gestureLibrary.removeEntry(gesturename);
            gestureLibrary.addGesture(navgesname, list.get(0));
            if (gestureLibrary.save()) {
                Log.e(TAG, "gesture renamed!");
                onResume();
            }
        }
        navgesname = "";
    }










   @Override
    public void onResume(){
        super.onResume();
        View newview=inflater.inflate(R.layout.fragment_gesture_list,container,false);
        placeholder.removeAllViews();
        placeholder.addView(newview);
        Log.d(TAG, getActivity().getApplicationInfo().dataDir);

        getActivity().openOptionsMenu();

        gesturelistview= (ListView) newview.findViewById((R.id.gesturelist));
        gestlist = new ArrayList<GestureHolder>();
        getgestureslist();
        gestureArrayAdapter = new GestureArrayAdapter( getContext(),gestlist);
        gesturelistview.setLongClickable(true);
        gesturelistview.setAdapter(gestureArrayAdapter);
        addgesture=(Button)newview.findViewById(R.id.addgesture);
        testgesture=(Button)newview.findViewById(R.id.testgesture);
        fragmentManager=getActivity().getSupportFragmentManager();
       addgesture.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Log.e("button","addgesture clicked");
               FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
               FragmentTransaction trans=fragmentManager.beginTransaction();
               SaveFragment category=new SaveFragment();
               //category.categoryname=categname;
               trans.replace(R.id.fragmentcontainer,category);
               trans.addToBackStack(null);
               trans.commit();
           }
       });
       testgesture.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
               FragmentTransaction trans=fragmentManager.beginTransaction();
               GestureMainFragment category=new GestureMainFragment();
               //category.categoryname=categname;
               trans.replace(R.id.fragmentcontainer,category);
               trans.addToBackStack(null);
               trans.commit();
           }
       });
        // displays the popup context top_menu to either delete or resend measurement
       registerForContextMenu(gesturelistview);
       }


    public void deletegesture(MenuItem item){
        gestureLibrary.removeEntry(gesturename);
        gestureLibrary.save();
        gesturename = "";
        onResume();
    }

    public void renamegesture(MenuItem item)
    {
        AlertDialog.Builder namePopup = new AlertDialog.Builder(getActivity());
        namePopup.setTitle("Enter New name");
        //namePopup.setMessage(R.string.enter_name);

        final EditText nameField = new EditText(getActivity());
        namePopup.setView(nameField);

        namePopup.setPositiveButton("Save ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (!nameField.getText().toString().matches("")) {
                    navgesname = nameField.getText().toString();
                    savegesture();
                } else {
                    renamegesture(null);  //TODO : validation
                    Toast.makeText(getContext(),"Invalid name",Toast.LENGTH_SHORT).show();
                }
            }
        });
        namePopup.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                navgesname = "";
                gesturename = "";
                return;
            }
        });

        namePopup.show();
    }


    public class GestureArrayAdapter extends ArrayAdapter<GestureHolder>
    {
        Context mgesturecontext;
        ArrayList<GestureHolder> gestureHolders;
        //int resourceid;
        public GestureArrayAdapter(Context context, ArrayList<GestureHolder> gestureHolder) {
            super(context,R.layout.gesturelistlayout,gestureHolder );
            this.mgesturecontext=context;
            this.gestureHolders=gestureHolder;

        }

        public View getView(int position, View convertView, ViewGroup parent)
        {
            View v=convertView;
            GestureViewHolder holder=new GestureArrayAdapter.GestureViewHolder();
            if(convertView==null) {
                LayoutInflater layoutInflater = (LayoutInflater) mgesturecontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = layoutInflater.inflate(R.layout.gesturelist_item, null);
                TextView gesturename, gesturenameref,gestureid;
                ImageView gestureimg;
                gesturename=(TextView)v.findViewById(R.id.gesturename);
                gestureimg=(ImageView)v.findViewById(R.id.gestureimg);
                gesturenameref=(TextView)v.findViewById(R.id.gesturenameref);
                gestureid=(TextView)v.findViewById(R.id.gestureid);
                holder.gestureid=gestureid;
                holder.gesturename=gesturename;
                holder.gesturenameref=gesturenameref;
                holder.gestureimage=gestureimg;
                v.setTag(holder);
            }
            else
            {
                holder=(GestureViewHolder)v.getTag(position);
            }
            GestureHolder gestureHolder=gestureHolders.get(position);
            try {
                holder.gestureid.setText(String.valueOf(gestureHolder.getGesture().getID()));
                holder.gesturename.setText(gestureHolder.getName());
                holder.gesturenameref.setText(gestureHolder.getName());


            try
            {
                holder.gestureimage.setImageBitmap(gestureHolder.getGesture().toBitmap(30,30,3, Color.CYAN));
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }}
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return v;
        }

        public class GestureViewHolder
        {
            public TextView gestureid,gesturename,gesturenameref;
            public ImageView gestureimage;

        }

    }

}
