package com.example.shivam.datcompressorfinal;

import android.widget.ImageView;

import java.io.File;

/**
 * Created by Shivam on 3/12/2017.
 */

public class FileArrayAdapter {

    String filename;
    long size;
    ImageView imageView;
String filepath,fileext;
    String filetype;
    File file;
    long timer;
    String technique="Lossless";
    int width,height,percentage;
    public void setFilename(String filename)
    {
        this.filename=filename;
    }
    public void setSize(long size)
    {
        this.size=size;
    }
    public  void setFilepath(String filepath)
    {
        this.filepath=filepath;
    }
    public void setFiletype(String filetype)
    {
        this.filetype=filetype;
    }
    public void setFile1(File file)
    {
        this.file=file;
    }
    public void setImageView()
    {
        imageView.setImageResource(R.drawable.file);
    }
    public String getFilepath()
    {
        return  filepath;
    }
    public  String getFilename()
    {
        return  filename;
    }
    public void setTechnique(String  technique)
    {
       this.technique=technique;
    }
    public String getTechnique()
    {
        return  this.technique;
    }
    public String getFiletype()
    {
        return filetype;
    }
    public void setFileext(String fileext)
    {
        this.fileext=fileext;
    }
    public String getFileext(){return this.fileext;}
    public void setTimer(long timer)
    {
        this.timer=timer;
    }
    public String getTimer()
    {
        return Long.toString(this.timer);
    }
    public void setImageWidth(int width)
    {
        if(width>=0)
            this.width=width;
        else
            this.width=0;
    }
    public void setImageHeight(int height)
    {
        if(height>=0)
            this.height=height;
        else
            this.height=0;
    }

    public void setQualityPercentage(int percentage)
    {
        if(percentage>=0)
            this.percentage=percentage;
        else
            this.percentage=0;
    }
    public int getQualityPercentage(){return this.percentage;}
    public int getImageHeight(){return this.height;}
    public int getImageWidth(){return this.width;}
    public long getFileSize()
    {
        return this.size;
    }


}
