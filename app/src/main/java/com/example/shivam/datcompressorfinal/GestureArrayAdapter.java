package com.example.shivam.datcompressorfinal;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Shivam on 2/25/2017.
 */

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
        GestureViewHolder holder=new GestureViewHolder();
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
        }
        return v;
    }

    public class GestureViewHolder
    {
        public TextView gestureid,gesturename,gesturenameref;
        public ImageView gestureimage;

    }

}


