package com.example.shivam.datcompressorfinal;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Shivam on 3/12/2017.
 */

public class ListAdapterClass extends BaseExpandableListAdapter {

    Context mContext;
    ArrayList<FileArrayAdapter> array=null;
    LayoutInflater mLayoutInflater;
    ListAdapterClass(Context context,ArrayList<FileArrayAdapter> array)
    {
        this.mContext=context;
        array=new ArrayList<FileArrayAdapter>();
        this.array=array;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getGroupCount() {
        return array.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 2;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.array.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
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
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
if(convertView==null)
{
    convertView = mLayoutInflater.inflate(R.layout.selectfilelayout, parent, false);
}
       // Toast.makeText()
        FileArrayAdapter fileArrayAdapter=array.get(groupPosition);
        TextView filename,filedate,filesize;
        filename=(TextView) convertView.findViewById(R.id.filename);
        filename.setText(fileArrayAdapter.getFilename());
        filesize=(TextView)convertView.findViewById(R.id.filesize);
        long size=fileArrayAdapter.getFileSize();
        String value=formatSize(size);
        filesize.setText(value);
        final ImageView expandedImage = (ImageView) convertView.findViewById(R.id.arrow);
        final int resId = isExpanded ? R.drawable.uparrow :R.drawable.arrow ;
        expandedImage.setImageResource(resId);
                fileArrayAdapter.getFilename();
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if(convertView==null)
        {
            convertView = mLayoutInflater.inflate(R.layout.selectchildlayout, parent, false);
        }
       if(childPosition==0)
       {
           TextView childtext=(TextView)convertView.findViewById(R.id.childtext);
           childtext.setText("Settings");
           ImageView imageView=(ImageView)convertView.findViewById(R.id.childicon);
           imageView.setImageResource(R.drawable.settings);
       }
        else
       {
           TextView childtext=(TextView)convertView.findViewById(R.id.childtext);
           childtext.setText("Remove");
           ImageView imageView=(ImageView)convertView.findViewById(R.id.childicon);
           imageView.setImageResource(R.drawable.deleteicon);
       }

        return convertView;
    }

    public static String formatSize(long size) {
        String suffix = null;

        if (size >= 1024) {
            suffix = " KB";
            size /= 1024;
            if (size >= 1024) {
                suffix = " MB";
                size /= 1024;
            }

        }
        StringBuilder resultBuffer = new StringBuilder(Long.toString(size));

        int commaOffset = resultBuffer.length() - 3;
        while (commaOffset > 0) {
            resultBuffer.insert(commaOffset, '.');
            suffix=" GB";
            commaOffset -= 3;
        }
        if (suffix != null) resultBuffer.append(suffix);
        return resultBuffer.toString();
    }




    public void modifyarray(ArrayList<FileArrayAdapter> array)
    {
        this.array=array;

    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
