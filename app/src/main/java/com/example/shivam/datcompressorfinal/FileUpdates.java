package com.example.shivam.datcompressorfinal;

import java.util.Date;

/**
 * Created by Shivam on 3/4/2017.
 */

public class FileUpdates {

    String filename,message;
    Date  updatedate;
    String updatetimhis;
    public void setFilename(String filename)
    {
        this.filename=filename;
    }
    public  String getFilename()
    {
        return  filename;
    }

    public String getMessage()
    {
        return  message;
    }
    public void setMessage(String message)
    {
        this.message=message;
    }
    public String returndatetime()
    {
        String str=String.valueOf(updatedate)+ " "+updatetimhis;
        return str;
    }
    public void setUpdatedate(Date date)
    {
        this.updatedate=date;

    }
}
