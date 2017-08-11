package com.example.shivam.datcompressorfinal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Shivam on 3/19/2017.
 */

public class SettingsCompressionAdapter extends BaseExpandableListAdapter{
    Context mContext;
    LayoutInflater mLayoutInflater;
    ArrayList<String> menutitles=null;
    String []subtitles={"Lossy","Lossless"};
    SettingsCompressionAdapter(Context context)
    {
        this.mContext=context;
        menutitles=new ArrayList<String>();
        menutitles.add("Document Files");
        menutitles.add("Image Files");
        menutitles.add("Videos Files");
        menutitles.add("Audio Files");
        menutitles.add("Others");

        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getGroupCount() {
        return menutitles.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 2;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return menutitles.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return subtitles[childPosition];
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent)
    {
        if(convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.compsettingslay, parent, false);
        }

        //final ImageView image = (ImageView) convertView.findViewById(R.id.sample_activity_list_group_item_image);


        final TextView text = (TextView) convertView.findViewById(R.id.sample_activity_list_group_item_text);
        text.setText(menutitles.get(groupPosition));

        final ImageView expandedImage = (ImageView) convertView.findViewById(R.id.sample_activity_list_group_expanded_image);
        final int resId = isExpanded ? R.drawable.uparrow :R.drawable.arrow ;
        expandedImage.setImageResource(resId);



        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if(convertView==null)
        {
            convertView = mLayoutInflater.inflate(R.layout.compsettingchildlayout, parent, false);
        }
        if(childPosition==0)
        {
           TextView childtext=(TextView)convertView.findViewById(R.id.compressiontype);
            childtext.setText(subtitles[0]);

        }
        else
        {
            TextView childtext=(TextView)convertView.findViewById(R.id.compressiontype);
            childtext.setText(subtitles[1]);

        }

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
