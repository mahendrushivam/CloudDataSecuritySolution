package com.example.shivam.datcompressorfinal;


import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class CloudStorageFragment extends Fragment {

AlertDialog builder;
    int []resimageid={R.drawable.googledriveicon,R.drawable.onedriveicon,R.drawable.dropboxicon,R.drawable.s3icon,R.drawable.sugarsyncicon,R.drawable.boxicon};
    String [] cloudlist={"Drive","OneDrive","Dropbox","Amazon S3","SugarSync","Box"};
    GridView cloudlistfold;
    CloudArrayAdapter cloudArrayAdapter;
    public CloudStorageFragment() {
        // Required empty public constructor
    }

    Button addcloudaccount;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v=inflater.inflate(R.layout.fragment_cloud_storage, container, false);
        addcloudaccount=(Button)v.findViewById(R.id.addcloudbtn);
        addcloudaccount.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction())
                {
                    case MotionEvent.ACTION_UP:
                            {
                                LayoutInflater inflater =getActivity().getLayoutInflater();

                                final View dialogView = inflater.inflate(R.layout.cloudlisticon, null);
                                cloudlistfold=(GridView)dialogView.findViewById(R.id.cloudlist);
                                cloudArrayAdapter=new CloudArrayAdapter(getContext(),R.layout.cloudchildlayout,R.id.cloudtxt,cloudlist);
                                cloudlistfold.setAdapter(cloudArrayAdapter);
                                builder=new AlertDialog.Builder(getActivity())
                                        .setIcon(R.drawable.cloudicon)
                                        .setTitle("Select Storage Type ")
                                        .setView(dialogView)
                                        .setCancelable(true)
                                        .show();
                                cloudlistfold.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        builder.dismiss();
                                    }
                                });


                                Button view = (Button) v;
                                view.getBackground().setColorFilter(Color.parseColor("#26A9E1"), PorterDuff.Mode.SRC_ATOP);
                                view.setTextColor(Color.parseColor("#ffffff"));
                                //imageView.setBackgroundColor(Color.parseColor("#99cccc"));
                                v.invalidate();
                               break; }
                    case MotionEvent.ACTION_CANCEL:
                    {
                        Button view = (Button) v;
                        view.getBackground().setColorFilter(Color.parseColor("#26A9E1"), PorterDuff.Mode.SRC_ATOP);
                        view.setTextColor(Color.parseColor("#ffffff"));
                        //imageView.setBackgroundColor(Color.parseColor("#99cccc"));
                        v.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_DOWN:
                    {
                        Button view = (Button) v;
                        view.getBackground().setColorFilter(Color.parseColor("#96C342"), PorterDuff.Mode.SRC_ATOP);
                        view.setTextColor(Color.parseColor("#ffffff"));
                        //imageView.setBackgroundColor(Color.parseColor("#99cccc"));
                        v.invalidate();
                        break;
                    }
                }
                return true;
            }
        });
        return v;
    }


    public class CloudArrayAdapter extends ArrayAdapter<String>
    {
        public  CloudArrayAdapter (Context cont,int row_item,int text1,String [] str)
        {
            super(cont,row_item,text1,str);

        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = super.getView(position,convertView,parent);
            CloudStorageFragment.ViewHolder holder = (CloudStorageFragment.ViewHolder)row.getTag();

            if(holder==null)
            {
                holder = new CloudStorageFragment.ViewHolder(row);
                row.setTag(holder);
            }
            holder.cloudicon.setImageResource(resimageid[position]);
            holder.cloudtxtview.setText(cloudlist[position]);

            return row;
        }
    }
    class ViewHolder
    {
        TextView cloudtxtview;
        ImageView cloudicon;
        ViewHolder(View row)
        {
            cloudtxtview=(TextView)row.findViewById(R.id.cloudtxt);
            cloudicon=(ImageView)row.findViewById(R.id.cloudimg);
        }
    }

}
