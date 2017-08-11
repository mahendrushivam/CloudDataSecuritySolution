package com.example.shivam.datcompressorfinal;


import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.angads25.filepicker.controller.DialogSelectionListener;
import com.github.angads25.filepicker.model.DialogConfigs;
import com.github.angads25.filepicker.model.DialogProperties;
import com.github.angads25.filepicker.view.FilePickerDialog;
import com.ning.compress.lzf.LZFOutputStream;


import net.jpountz.lz4.LZ4BlockOutputStream;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import java.io.File;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.RunnableFuture;

import id.zelory.compressor.Compressor;
import id.zelory.compressor.FileUtil;
import it.michelelacorte.elasticprogressbar.ElasticDownloadView;
import it.michelelacorte.elasticprogressbar.OptionView;


/**
 * A simple {@link Fragment} subclass.
 */
public class CompressListFragment extends Fragment {
    ListAdapterClass listAdapterClass;
    private ListView listview;
Button addfiles,startbtn,cancelbtn,settingsbtn;
    TextView nofiles,totsize;
    Button diallogbtn,dialcancelbtn;
    ElasticDownloadView elasticDownloadView;
    long totfilesize=0;
    public static final String TEXTCOMP_QUALPERCENTAGE="TEXTCOMP_QUALPERCENTAGE";
    public static final String TEXTCOMP_TYPE="TEXTCOMP_TYPE";
    public static final String IMGCOMP_QUALPERCENTAGE="IMGCOMP_QUALPERCENTAGE";
    public static final String IMGCOMP_TYPE="TEXTCOMP_QUALPERCENTAGE";
    public static final String IMGCOMP_WIDTH="IMGCOMP_WIDTH";
    public static final String IMGCOMP_HEIGHT="IMGCOMP_HEIGHT";
    ExpandableListView expandableListView;
    Handler handler=new Handler();
    public static final int PICK_MULTIPLE_FILES=1;
    ArrayList<FileArrayAdapter> compressedfiles;
    CheckBox lossy,lossless;
    AlertDialog dialog;
    DiscreteSeekBar qualpercent1,qualpercent2,imgwidth,imgheight;
    LinearLayout lay,layout3;
    int fileSize=0;
    int progressStatus=0;
    File [] compfiles;
ArrayList<FileArrayAdapter> fileArrayAdapter=null;
    public CompressListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fileArrayAdapter=new ArrayList<FileArrayAdapter>();
        compressedfiles=new ArrayList<FileArrayAdapter>();
        listAdapterClass=new ListAdapterClass(getContext(),fileArrayAdapter);
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_compress_list, container, false);
        startbtn=(Button)v.findViewById(R.id.startbtn);
        cancelbtn=(Button)v.findViewById(R.id.cancelbtn);
        addfiles=(Button)v.findViewById(R.id.addfiles);
        handler=new Handler();
        settingsbtn=(Button)v.findViewById(R.id.settingsbtn);
        nofiles=(TextView)v.findViewById(R.id.nooffiles);
        totsize=(TextView)v.findViewById(R.id.totsize);
        expandableListView=(ExpandableListView)v.findViewById(R.id.expandlist);
        expandableListView.setAdapter(listAdapterClass);
        //expandableListView.setDividerHeight(0);

expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        //Toast.makeText(getContext(),"Hello",Toast.LENGTH_LONG).show();
        if(childPosition==1)
        {
        ListAdapterClass mlistAdapterClass=new ListAdapterClass(getContext(),fileArrayAdapter);
        View v1=mlistAdapterClass.getChildView(groupPosition,childPosition,true,null, parent);
            FileArrayAdapter adapter=fileArrayAdapter.get(groupPosition);
            totfilesize-=adapter.getFileSize();

            String value=formatSize(totfilesize);


            totsize.setText(value);

try {
    fileArrayAdapter.remove(groupPosition);
}
catch (Exception e)
{
    e.printStackTrace();
}
        listAdapterClass.modifyarray(fileArrayAdapter);
        listAdapterClass.notifyDataSetChanged();
        expandableListView.setAdapter(listAdapterClass);
            nofiles.setText(String.valueOf(fileArrayAdapter.size()));
        return true;
        }
        else if(childPosition==0)
        {
            final FileArrayAdapter arrayAdapter=fileArrayAdapter.get(groupPosition);
            nofiles.setText(String.valueOf(fileArrayAdapter.size()));
            String mime=arrayAdapter.getFiletype();
            final String technique=arrayAdapter.getTechnique();
            //FOR IMAGE FILE EXTENSIONS
            if(mime.equals("jpg") || mime.equals("webp") || mime.equals("gif") || mime.equals("png") || mime.equals("tif"))
            {
               // Toast.makeText(getContext(),"Image Converter",Toast.LENGTH_SHORT).show();
                LayoutInflater inflater =getActivity().getLayoutInflater();

                final View dialogView = inflater.inflate(R.layout.settings1layout, null);
                qualpercent1=(DiscreteSeekBar)dialogView.findViewById(R.id.qualitypercentage);
                imgheight=(DiscreteSeekBar) dialogView.findViewById(R.id.imgheight);
                imgwidth=(DiscreteSeekBar)dialogView.findViewById(R.id.imgwidth);
                lossy=(CheckBox)dialogView.findViewById(R.id.lossy);
                lossless=(CheckBox)dialogView.findViewById(R.id.lossless);
                lay=(LinearLayout)dialogView.findViewById(R.id.linearlay2);
                layout3=(LinearLayout)dialogView.findViewById(R.id.layout2);
                if(technique.equals("Lossless"))
                {
                    lossless.setChecked(true);
                    lossy.setChecked(false);
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) lay.getLayoutParams();
                    params.height=450;
                    params.width=LinearLayout.LayoutParams.MATCH_PARENT;
                    layout3.setVisibility(View.INVISIBLE);
                    lay.setLayoutParams(params);

                }

                else
                {
                    lossless.setChecked(false);
                    lossy.setChecked(true);
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) lay.getLayoutParams();
                    params.height=1200;
                    params.width=LinearLayout.LayoutParams.MATCH_PARENT;
                    layout3.setVisibility(View.VISIBLE);
                    lay.setLayoutParams(params);
                    int imgw=arrayAdapter.getImageWidth();
                    int imgh=arrayAdapter.getImageHeight();
                    int qualper=arrayAdapter.getQualityPercentage();
                    qualpercent1.setProgress(qualper);
                    imgheight.setProgress(imgh);
                    imgwidth.setProgress(imgw);
                }
                lossless.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked)
                        {
                            lossy.setChecked(false);
                            arrayAdapter.setTechnique("Lossless");
                            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) lay.getLayoutParams();
                            params.height=450;
                            params.width=LinearLayout.LayoutParams.MATCH_PARENT;
                            layout3.setVisibility(View.INVISIBLE);
                            lay.setLayoutParams(params);
                            //Toast.makeText(getContext(),"Checked",Toast.LENGTH_SHORT).show();
                        }

                    }
                });

                lossy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked)
                        {
                            lossless.setChecked(false);
                            arrayAdapter.setTechnique("Lossy");
                            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) lay.getLayoutParams();
                            params.height=1200;
                            params.width=LinearLayout.LayoutParams.MATCH_PARENT;
                            layout3.setVisibility(View.VISIBLE);
                            lay.setLayoutParams(params);
                            int imgw=arrayAdapter.getImageWidth();
                            int imgh=arrayAdapter.getImageHeight();
                            int qualper=arrayAdapter.getQualityPercentage();
                            qualpercent1.setProgress(qualper);
                            imgheight.setProgress(imgh);
                            imgwidth.setProgress(imgw);
                            // Toast.makeText(getContext(),"Checked",Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                new AlertDialog.Builder(getActivity())
                        .setIcon(R.drawable.settings)
                        .setTitle("Settings ")
                        .setView(dialogView)
                        .setCancelable(true)
                        .setPositiveButton("Set", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                if(lossy.isChecked())
                                {
                                    arrayAdapter.setTechnique("Lossy");
                                    lossy.setChecked(true);
                                    lossless.setChecked(false);
                                    arrayAdapter.setQualityPercentage(qualpercent1.getProgress());
                                    arrayAdapter.setImageHeight(imgheight.getProgress());
                                    arrayAdapter.setImageWidth(imgwidth.getProgress());
                                    arrayAdapter.setFileext("webm");

                                }
                                else
                                {
                                    arrayAdapter.setTechnique("Lossless");
                                    lossy.setChecked(false);
                                    lossless.setChecked(true);
                                    arrayAdapter.setQualityPercentage(100);
                                    arrayAdapter.setImageHeight(400);
                                    arrayAdapter.setImageWidth(600);
                                    arrayAdapter.setFileext("lzf");
                                }


                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();

            }
            else {

                LayoutInflater inflater =getActivity().getLayoutInflater();

                final View dialogView = inflater.inflate(R.layout.settings2layout, null);
                qualpercent2=(DiscreteSeekBar)dialogView.findViewById(R.id.qualitypercentage);

                lossy=(CheckBox)dialogView.findViewById(R.id.lossy);
                lossless=(CheckBox)dialogView.findViewById(R.id.lossless);
                lay=(LinearLayout)dialogView.findViewById(R.id.linearlay2);
                layout3=(LinearLayout)dialogView.findViewById(R.id.layout2);
                if(technique.equals("Lossless"))
                {
                    lossless.setChecked(true);
                    lossy.setChecked(false);
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) lay.getLayoutParams();
                    params.height=450;
                    params.width=LinearLayout.LayoutParams.MATCH_PARENT;
                    arrayAdapter.setQualityPercentage(100);
                    layout3.setVisibility(View.INVISIBLE);
                    lay.setLayoutParams(params);

                }

                else
                {
                    lossless.setChecked(false);
                    lossy.setChecked(true);
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) lay.getLayoutParams();
                    params.height=800;
                    params.width=LinearLayout.LayoutParams.MATCH_PARENT;
                    int qualper=arrayAdapter.getQualityPercentage();
                    qualpercent2.setProgress(qualper);
                    layout3.setVisibility(View.VISIBLE);
                    lay.setLayoutParams(params);


                }
                lossless.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked)
                        {
                            lossy.setChecked(false);
                            arrayAdapter.setTechnique("Lossless");
                            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) lay.getLayoutParams();
                            params.height=450;
                            params.width=LinearLayout.LayoutParams.MATCH_PARENT;
                            layout3.setVisibility(View.INVISIBLE);
                            lay.setLayoutParams(params);
                            //arrayAdapter.setFileext("lzf");
                            //Toast.makeText(getContext(),"Checked",Toast.LENGTH_SHORT).show();
                        }

                    }
                });

                lossy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked)
                        {
                            lossless.setChecked(false);
                            arrayAdapter.setTechnique("Lossy");
                            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) lay.getLayoutParams();
                            params.height=800;
                            params.width=LinearLayout.LayoutParams.MATCH_PARENT;
                            layout3.setVisibility(View.VISIBLE);
                            lay.setLayoutParams(params);
                            qualpercent2.setProgress(arrayAdapter.getQualityPercentage());
                            //arrayAdapter.setFileext("l");
                           // Toast.makeText(getContext(),"Checked",Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                new AlertDialog.Builder(getActivity())
                        .setIcon(R.drawable.settings)
                        .setTitle("Settings ")
                        .setView(dialogView)
                        .setCancelable(true)
                        .setPositiveButton("Set", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                               if(lossy.isChecked())
                               {
                                   arrayAdapter.setTechnique("Lossy");
                                   lossy.setChecked(true);
                                   lossless.setChecked(false);
                                   arrayAdapter.setQualityPercentage(qualpercent2.getProgress());
                                   arrayAdapter.setFileext("lzf");
                               }
                                else
                               {
                                   arrayAdapter.setTechnique("Lossless");
                                   lossy.setChecked(false);
                                   lossless.setChecked(true);
                                   arrayAdapter.setQualityPercentage(100);
                                   if(arrayAdapter.getFiletype().equals("pdf"))
                                    arrayAdapter.setFileext("lzf");
                                   else
                                       arrayAdapter.setFileext("lz4");
                               }


                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            }
            fileArrayAdapter.remove(groupPosition);
            fileArrayAdapter.add(groupPosition,arrayAdapter);
            listAdapterClass.modifyarray(fileArrayAdapter);
            listAdapterClass.notifyDataSetChanged();
            expandableListView.setAdapter(listAdapterClass);
            return true;
        }

      return false;
    }
});

        addfiles.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        Button view = (Button) v;
                        view.getBackground().setColorFilter(Color.parseColor("#D4E6F1"), PorterDuff.Mode.SRC_ATOP);
                        view.setTextColor(Color.parseColor("#000000"));
                        //imageView.setBackgroundColor(Color.parseColor("#99cccc"));
                        v.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_UP:
                    {
                        DialogProperties properties = new DialogProperties();
                        properties.selection_mode = DialogConfigs.MULTI_MODE;

                        properties.selection_type = DialogConfigs.FILE_SELECT;
                        properties.root = new File("storage/emulated/0");
                        properties.error_dir = new File(DialogConfigs.DEFAULT_DIR);
                        properties.offset = new File(DialogConfigs.DEFAULT_DIR);
                        properties.extensions = null;
                        FilePickerDialog dialog = new FilePickerDialog(getContext(),properties);
                        dialog.setTitle("Select the files");
                        dialog.show();
                        v.getBackground().clearColorFilter();
                        //imageView.setBackgroundColor(Color.parseColor("#e2e7e7"));
                        v.invalidate();
                        dialog.setDialogSelectionListener(new DialogSelectionListener() {
                            @Override
                            public void onSelectedFilePaths(String[] files) {

                                    for(String f1:files)
                                    {Toast.makeText(getContext(),String.valueOf(files.length),Toast.LENGTH_LONG).show();
                                        File file=new File(f1);
                                        FileArrayAdapter fileselect=new FileArrayAdapter();
                                        fileselect.setFilepath(f1);
                                        fileselect.setFilename(file.getName());
                                        String filetype=file.getName().substring(file.getName().lastIndexOf("."));
                                        String mime=filetype.substring(1,filetype.length());
                                        if(mime.equals("txt") || mime.equals("doc") || mime.equals("ppt") )
                                            fileselect.setFileext("lz4");
                                        else
                                        {
                                            fileselect.setFileext("lzf");
                                        }
                                        fileselect.setFiletype(mime);
                                        fileselect.setTechnique("Lossless");
                                        fileselect.setImageHeight(0);
                                        fileselect.setImageWidth(0);
                                        fileselect.setQualityPercentage(0);
                                        fileselect.setFile1(file);
                                        long s1=getFileSize(file);
                                        totfilesize+=s1;
                                        fileselect.setSize(s1);
                                        fileArrayAdapter.add(fileselect);
                                    }
                                String value=null;

                                value=formatSize(totfilesize);


                                nofiles.setText(String.valueOf(fileArrayAdapter.size()));
                                totsize.setText(value);
                               // listAdapterClass=new ListAdapterClass(getContext(),fileArrayAdapter);
                                listAdapterClass.modifyarray(fileArrayAdapter);
                                listAdapterClass.notifyDataSetChanged();
                                expandableListView.setAdapter(listAdapterClass);

                            }
                        });
                        Button view = (Button) v;
                        view.getBackground().clearColorFilter();
                        view.setTextColor(Color.parseColor("#ffffff"));
                        //imageView.setBackgroundColor(Color.parseColor("#e2e7e7"));
                        view.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_CANCEL: {
                        Button view = (Button) v;
                        view.getBackground().clearColorFilter();
                        view.setTextColor(Color.parseColor("#000000"));
                        //imageView.setBackgroundColor(Color.parseColor("#e2e7e7"));
                        view.invalidate();
                        break;
                    }
                }

                return true;
            }
        });


        startbtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, final MotionEvent event) {

                switch(event.getAction())
                {
                    case MotionEvent.ACTION_DOWN: {
                        Button view = (Button) v;
                        view.getBackground().setColorFilter(Color.parseColor("#D4E6F1"), PorterDuff.Mode.SRC_ATOP);
                        view.setTextColor(Color.parseColor("#000000"));
                        //imageView.setBackgroundColor(Color.parseColor("#99cccc"));
                        v.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_UP:
                    {LayoutInflater inflater =getActivity().getLayoutInflater();

                        final View dialogView = inflater.inflate(R.layout.layout1, null);
                        elasticDownloadView=(ElasticDownloadView)dialogView.findViewById(R.id.elastic_download_view);


                        //handler=new Handler();
        if(fileArrayAdapter.size()>0) {
            elasticDownloadView.startIntro();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    while (progressStatus < 100) {
                        progressStatus =downloadFile();
                        handler.post(new Runnable() {
                            public void run() {
                                //Set progress dinamically
                                elasticDownloadView.setProgress(progressStatus);
                                Log.d("Progress:", "" + elasticDownloadView.getProgress());
                            }
                        });

                    }
                    if (progressStatus >= 100)
                        {
                            elasticDownloadView.success();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    nofiles.setText("0");
                                    totsize.setText("0 KB");
                                  dialog.dismiss();
                                    LayoutInflater inflater =getActivity().getLayoutInflater();
                                    final View dView = inflater.inflate(R.layout.complayoutouter, null);
                                    listview=(ListView)dView.findViewById(R.id.complistview) ;
                                    final MyArrayAdapter arradapter=new MyArrayAdapter(getActivity(), R.layout.complayout1, R.id.compfilename, compressedfiles);
                                    listview.setAdapter(arradapter);
                                    new AlertDialog.Builder(getActivity())
                                     .setTitle("Compressed Filelist")
                                     .setIcon(R.drawable.listicon)
                                     .setView(dView)
                                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {

                                                   compressedfiles.clear();
                                                    arradapter.notifyDataSetChanged();
                                                    listview.setAdapter(arradapter);


                                                }
                                            })
                                     .show();
                            }
                        },3500);


                    }
                    //dialog.dismiss();
                }
            }, 1500);
            dialcancelbtn=(Button)dialogView.findViewById(R.id.dialcancelbtn);


            // LOGS BUTTON FUNCTIONALITY
            diallogbtn=(Button)dialogView.findViewById(R.id.diallogbtn);

            dialog=new AlertDialog.Builder(getActivity())

                    .setView(dialogView)
                    .setCancelable(true)

                    .show();
            Toast.makeText(getContext(), String.valueOf(fileArrayAdapter.size()), Toast.LENGTH_SHORT).show();
            try {
                compfiles = new File[fileArrayAdapter.size()];
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "You selected zero files to be compressed", Toast.LENGTH_SHORT).show();

            }
            int k = -1;
            for (FileArrayAdapter f1 : fileArrayAdapter) {
                k++;
                long startTime,endTime;
                String mime = f1.getFiletype();
                if (mime.equals("pdf") || mime.equals("ppt")) {
                    //GO FOR LZF IMPLEMENTATION
                    startTime= System.currentTimeMillis();
                    compfiles[k] = LZFImplementation(f1, compfiles[k]);
                    endTime=System.currentTimeMillis();

                } else if (mime.equals("jpg") || mime.equals("gif") || mime.equals("png") || mime.equals("webp") || mime.equals("db")) {
                    //GO FOR LOSSLESS IMPLEMENTATION FOR LZF ALGORITHM
                    if (f1.getTechnique().equals("Lossless") ) {
                        startTime= System.currentTimeMillis();
                        compfiles[k] = LZFImplementation(f1, compfiles[k]);
                        endTime=System.currentTimeMillis();
                    }
                    //     GO FOR IMAGE CONVERTER METHOD I.E CONVERT TO WEBM FORMAT
                    else {
                        startTime= System.currentTimeMillis();
                        compfiles[k] = WebmConverterImplementation(f1, compfiles[k]);
                        endTime=System.currentTimeMillis();
                    }
                }
                            else
                             {
                                 //GO FOR LZ4 IMPLEMENTATION
                                 startTime= System.currentTimeMillis();
                               compfiles[k]=LZ4Implemenation(f1,compfiles[k]) ;
                                 endTime=System.currentTimeMillis();
                         }
                //Log.e("FILE PATH",compfiles[k].getAbsolutePath());
                FileArrayAdapter f4=new FileArrayAdapter();
                f4.setFilepath(compfiles[k].getAbsolutePath());
                f4.setFilename(compfiles[k].getName());
                f4.setFile1(compfiles[k]);
                f4.setSize(getFileSize(compfiles[k]));
                f4.setTimer(endTime-startTime);
                f4.setTechnique(f1.getTechnique());
                compressedfiles.add(f4);


            }

            dialcancelbtn.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {


                        case MotionEvent.ACTION_DOWN: {
                            Button view = (Button) v;
                            view.getBackground().setColorFilter(Color.parseColor("#D4E6F1"), PorterDuff.Mode.SRC_ATOP);
                            view.setTextColor(Color.parseColor("#000000"));
                            //imageView.setBackgroundColor(Color.parseColor("#99cccc"));
                            v.invalidate();
                            break;
                        }
                        case MotionEvent.ACTION_CANCEL: {
                            Button view = (Button) v;
                            view.getBackground().clearColorFilter();
                            view.invalidate();
                            break;

                        }
                        case MotionEvent.ACTION_UP:
                        {
                            Button view = (Button) v;
                            view.getBackground().clearColorFilter();
                            ((Button) v).setTextColor(Color.parseColor("#54608f"));
                            fileArrayAdapter.clear();
                            handler.removeCallbacksAndMessages(null);
                            listAdapterClass.modifyarray(fileArrayAdapter);
                            listAdapterClass.notifyDataSetChanged();
                            expandableListView.setAdapter(listAdapterClass);
                            totsize.setText("0 KB");
                            nofiles.setText("0");
                            view.invalidate();

                            try
                            {
                                if(compressedfiles.size()>0)
                                {
                                    for(int i=0;i<compressedfiles.size();i++)
                                    {
                                        FileArrayAdapter adap1=compressedfiles.get(i);
                                        File oufile=new File(adap1.getFilepath());
                                        if(oufile.exists())
                                        {
                                            //Log.e("FILE EXISTS","file"+oufile.getName());
                                            oufile.delete();
                                        }
                                    }
                                }
                            }
                            catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                            compressedfiles.clear();

                            elasticDownloadView.fail();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    dialog.dismiss();

                                }
                            },1000);

                            break;
                        }


                    }
                    return true;
                }
            });
            fileArrayAdapter.clear();
            listAdapterClass.modifyarray(fileArrayAdapter);
            listAdapterClass.notifyDataSetChanged();
            expandableListView.setAdapter(listAdapterClass);
        }
                        Button view = (Button) v;
                        view.getBackground().clearColorFilter();
                        ((Button) v).setTextColor(Color.parseColor("#54608f"));
                        view.invalidate();
                        return true;
                    }
                    case MotionEvent.ACTION_CANCEL: {
                        Button view = (Button) v;
                        view.getBackground().clearColorFilter();
                        view.invalidate();
                        return true;
                    }

                }
                return false;
            }
        });


        settingsbtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction())
                {
                    case MotionEvent.ACTION_CANCEL: {
                        Button view = (Button) v;
                        view.getBackground().clearColorFilter();
                        view.invalidate();
                        return true;
                    }
                    case MotionEvent.ACTION_DOWN: {
                        Button view = (Button) v;
                        view.getBackground().setColorFilter(Color.parseColor("#D4E6F1"), PorterDuff.Mode.SRC_ATOP);
                        view.setTextColor(Color.parseColor("#000000"));
                        //imageView.setBackgroundColor(Color.parseColor("#99cccc"));
                        v.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_UP:
                    {
                        FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                        FragmentTransaction transaction=fragmentManager.beginTransaction();
                        Fragment fragment=new DataCompressionSettingsFragment();
                        transaction.replace(R.id.fragmentcontainer,fragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                }
                return false;
            }
        });


    return v;
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


    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getPathURL(final Context context, final Uri uri) {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {

            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }

            else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            }

            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};
                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }

        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();
            return getDataColumn(context, uri, null, null);
        }

        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    public long getFileSize(File f) {
        long size = 0;
        if (f.isDirectory()) {
            for (File file : f.listFiles()) {
                size += getFileSize(file);
            }
        } else {
            size=f.length();
        }
        //totfilesize+=size;
        return size;
    }

    public File LZFImplementation(FileArrayAdapter f1,File f2)
    {
        File file = new File(f1.getFilepath());

        FileInputStream input = null;
        try {
            input = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        LZFOutputStream out = null;
        byte[] buf = new byte[2048];
        f2 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),f1.getFilename()+".lzf");
        try {
            out=new LZFOutputStream(new FileOutputStream(f2));
            int len;

            while ((len = input.read(buf)) > 0) {
                out.write(buf, 0, len);
            }

            input.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return f2;
    }

    public File WebmConverterImplementation(FileArrayAdapter f1,File f2)
    {

        Compressor.Builder builder=new  Compressor.Builder(getContext());
        builder.setDestinationDirectoryPath("");
        File f3=new File(f1.getFilepath());
        f2 = new Compressor.Builder(getContext())
                .setMaxWidth(f1.getImageWidth())
                .setMaxHeight(f1.getImageHeight())
                .setQuality(f1.getQualityPercentage())
                .setCompressFormat(Bitmap.CompressFormat.WEBP)
                .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DOWNLOADS).getAbsolutePath())
                .build()
                .compressToFile(f3);

        return f2;
    }
    public int downloadFile() {

        while (fileSize <= totfilesize) {

            fileSize++;

            if (fileSize == 100000) {
                return 10;

            } else if (fileSize == 200000) {
                return 20;

            } else if (fileSize == 300000) {
                return 30;

            } else if (fileSize == 400000) {
                return 40;

            } else if (fileSize == 500000) {

                return 50;
            } else if (fileSize == 700000) {

                return 70;
            } else if (fileSize == 800000) {

                return 80;
            } else if (fileSize == 900000) {

                return 90;
            }
        }

        return 100;

    }


    //LZ4IMPLEMENTATION

    public File LZ4Implemenation(FileArrayAdapter f1,File f2)
    {
        File file = new File(f1.getFilepath());

        FileInputStream input = null;
        try {
            input = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        LZ4BlockOutputStream out = null;
        byte[] buf = new byte[2048];
        f2 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),f1.getFilename()+".lz4" );
        try {
            out = new LZ4BlockOutputStream(new FileOutputStream(f2), 32 * 1024 * 1024);
            int len;

            while ((len = input.read(buf)) > 0) {
                out.write(buf, 0, len);
            }

            input.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return f2;
    }

// ARRAY ADAPTER CLASS FOR THE COMPRESSED FILES LIST VIEW
    public class MyArrayAdapter extends ArrayAdapter<FileArrayAdapter>
    {
        public  MyArrayAdapter(Context cont,int row_item,int text1,ArrayList<FileArrayAdapter> str)
        {
            super(cont,row_item,text1,str);

        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = super.getView(position,convertView,parent);
            ViewHolder holder = (ViewHolder)row.getTag();
            FileArrayAdapter f1=compressedfiles.get(position);
            if(holder==null)
            {
                holder = new ViewHolder(row);
                row.setTag(holder);
            }
            String hldfilename,hldfilesize,hldtimer;
            hldfilename=f1.getFilename();
            hldfilesize=formatSize(f1.getFileSize());
            hldtimer=f1.getTimer();
            holder.filename.setText(hldfilename);
            holder.txtfilesize.setText(hldfilesize);
            holder.txttimer.setText(hldtimer+" ms");

            return row;
        }
    }
    class ViewHolder
    {
        TextView filename,txttimer,txtfilesize;
        ViewHolder(View row)
        {
            filename=(TextView)row.findViewById(R.id.compfilename);
            txttimer=(TextView)row.findViewById(R.id.compfiletime);
            txtfilesize=(TextView)row.findViewById(R.id.compfilesize);
        }
    }

}
