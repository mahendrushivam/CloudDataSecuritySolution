package com.example.shivam.datcompressorfinal;

import android.app.ActivityManager;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import me.itangqi.waveloadingview.WaveLoadingView;

import static android.content.Context.ACTIVITY_SERVICE;


public class MainLayoutFragment extends Fragment {
WaveLoadingView waveLoadingView;
    TextView memorytitle,usedspace,leftspace;
    SharedPreferences sharedPreferences;
    //public static final String MEMORY_CHOIC="MEMORY_CHOICE";
    public static final String WAVE_COLOR="WAVE_COLOR";
    public static final String BORDER_COLOR="BORDER_COLOR";
    public static final String BACKGROUND_COLOR="BACKGROUND_COLOR";
    public  static final String BORDER_WIDTH="BORDER_WIDTH";
    public  static final String PROGRESS_SHAPE="PROGRESS_SHAPE";
    public static final String WAVE_AMPLITUDE="WAVE_AMPLITUDE";
    int progressval,wavecolor,bordercolor,backgroundcolor;
    float borderwidth;
    String progshape;

    SharedPreferences.Editor editor;
    public static final String MEMORY_CHOICE = "MEMORY_CHOICE";
    public MainLayoutFragment() {

        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        int ch=0;
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getActivity());

        progshape=sharedPreferences.getString(ProgressLoader.PROGRESS_SHAPE,"Circle");
        bordercolor=sharedPreferences.getInt(ProgressLoader.BORDER_COLOR,3);
        wavecolor=sharedPreferences.getInt(ProgressLoader.WAVE_COLOR,0);
        borderwidth=sharedPreferences.getFloat(ProgressLoader.BORDER_WIDTH,2);
        backgroundcolor=sharedPreferences.getInt(ProgressLoader.BACKGROUND_COLOR,0);

       // Toast.makeText(getContext(),String.valueOf(bordercolor) + " "+ String.valueOf(wavecolor)+ " " +progshape,Toast.LENGTH_LONG).show();
        View v=inflater.inflate(R.layout.layoutmain2, container, false);
        memorytitle=(TextView)v.findViewById(R.id.memorytitle);
        usedspace=(TextView)v.findViewById(R.id.usedspace);
        leftspace=(TextView)v.findViewById(R.id.leftspace);
        waveLoadingView=(WaveLoadingView)v.findViewById(R.id.waveLoadingView);
        ch=sharedPreferences.getInt(MainLayoutFragment.MEMORY_CHOICE,0);

        setProgressLayout();
        setMemoryLayout(ch);

        return v;

    }

    public void setProgressLayout()
    {
        progshape=sharedPreferences.getString(ProgressLoader.PROGRESS_SHAPE,"Circle");
        waveLoadingView.setBorderWidth(borderwidth);
        if(wavecolor!=0)
        waveLoadingView.setWaveColor(wavecolor);
        if(bordercolor!=0)
        waveLoadingView.setBorderColor(bordercolor);
        if(backgroundcolor!=0)
            waveLoadingView.setBackgroundColor(backgroundcolor);

        Toast.makeText(getContext(),"In Progress Layout" + progshape,Toast.LENGTH_LONG).show();

        switch(progshape.trim())
        {
            case "Circle":
            {
                waveLoadingView.setShapeType(WaveLoadingView.ShapeType.CIRCLE);
                break;
            }
            case "Square":
            {
                waveLoadingView.setShapeType(WaveLoadingView.ShapeType.SQUARE);
                break;
            }
            case "Rectangle":
            {
                waveLoadingView.setShapeType(WaveLoadingView.ShapeType.RECTANGLE);
                break;
            }
            case "Triangle":
            {
                waveLoadingView.setShapeType(WaveLoadingView.ShapeType.TRIANGLE);
                break;
            }
            default:
            {
                waveLoadingView.setShapeType(WaveLoadingView.ShapeType.CIRCLE);
                break;
            }

        }
    }
    public static long getAvailableInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSizeLong();
        long availableBlocks = stat.getAvailableBlocksLong();
        return availableBlocks * blockSize;
    }

    public static long getTotalInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSizeLong();
        long totalBlocks = stat.getBlockCountLong();
        return totalBlocks * blockSize;
    }
    public static boolean externalMemoryAvailable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }
    public static long getAvailableExternalMemorySize() {
        if (externalMemoryAvailable()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSizeLong();
            long availableBlocks = stat.getAvailableBlocksLong();
            return availableBlocks * blockSize;
        } else {
            return 0;
        }
    }

    public static long getTotalExternalMemorySize() {
        if (externalMemoryAvailable()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSizeLong();
            long totalBlocks = stat.getBlockCountLong();
            return totalBlocks * blockSize;
        } else {
            return 0;
        }
    }

    private long freeRamMemorySize() {
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        ActivityManager activityManager = (ActivityManager) getActivity().getSystemService(ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(mi);
        long availableMegs = mi.availMem / 1048576L;

        return availableMegs;
    }

    private long totalRamMemorySize() {
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        ActivityManager activityManager = (ActivityManager)getActivity().getSystemService(ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(mi);
        long availableMegs = mi.totalMem / 1048576L;
        return availableMegs;
    }

    public void setMemoryLayout(int ch)
    {
            SharedPreferences.Editor editor=sharedPreferences.edit();

        switch(ch)
        {
            case 0:
            {
                memorytitle.setText("Internal Storage");
                long totalInternalValue = getTotalInternalMemorySize();
                long freeInternalValue = getAvailableInternalMemorySize();
                long usedInternalValue = totalInternalValue - freeInternalValue;
                usedspace.setText(usedspace.getText().toString()+" " +formatSize(usedInternalValue));
                leftspace.setText(leftspace.getText().toString()+" " +formatSize(freeInternalValue));
                long percentage=(usedInternalValue*100)/totalInternalValue;
                //waveLoadingView.setShapeType(WaveLoadingView.ShapeType.CIRCLE);
                waveLoadingView.setProgressValue((int)percentage);
                editor.putInt(MEMORY_CHOICE,0);
                return;
            }
            case 1:
            {
                memorytitle.setText("External Storage");
                long totalExternalValue = getTotalExternalMemorySize();
                long freeExternalValue = getAvailableExternalMemorySize();
                long usedExternalValue = totalExternalValue - freeExternalValue;
                usedspace.setText(usedspace.getText().toString()+" " +formatSize(usedExternalValue));
                leftspace.setText(leftspace.getText().toString()+" " +formatSize(freeExternalValue));
                long percentage=(usedExternalValue*100)/totalExternalValue;
                waveLoadingView.setProgressValue((int)percentage);
                editor.putInt(MEMORY_CHOICE,1);
                return;
            }
            case 2:
            {
                memorytitle.setText("RAM Information");
                long totalRamValue = totalRamMemorySize();
                long freeRamValue = freeRamMemorySize();
                long usedRamValue = totalRamValue - freeRamValue;
                usedspace.setText(usedspace.getText().toString()+" " +formatSize(usedRamValue));
                leftspace.setText(leftspace.getText().toString()+" " +formatSize(freeRamValue));
                editor.putInt(MEMORY_CHOICE,2);
                return;
            }

        }
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




}




