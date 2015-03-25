package com.aquilibra.xavier.msbm;

/**
 * Created by Xavier on 3/13/2015.
 */
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

public  class FileOpener {


    public static void openFile(File file,Context context){

        MimeTypeMap mMime = MimeTypeMap.getSingleton();
        //String ext = MimeTypeMap.getFileExtensionFromUrl(file.toString());
        //String ext=file.toString().
        int pos_dot;
        String file_name = file.toString();

        List<Integer> pointslist = getpoints(file_name,".");
        pos_dot = file_name.indexOf(".");
        if(pointslist.size()>1){
            pos_dot = pointslist.get(pointslist.size()-1);
        }
        String ext = file_name.substring(pos_dot+1, file_name.length());
        //int posdot = file.toString().indexOf(".");
        //String ext=file.toString().substring(posdot+1, file.toString().length());
        String mtype = mMime.getMimeTypeFromExtension(ext);
        Intent intent   = new Intent(Intent.ACTION_VIEW);
        //intent.setDataAndType(Uri.fromFile(file), mtype);
        //intent.setDataAndType(Uri.fromFile(file),"application/*");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if(ext.equalsIgnoreCase("") || mtype == null){
            //intent.setDataAndType(Uri.fromFile(file),"application/v*" );
            switch(ext){
                case ".doc":
                    mtype="application/msword";
                    break;

                case ".docx":
                    mtype="application/msword";
                    break;

                case ".xls":
                    mtype = "application/vnd.ms-excel";
                    break;
                case "xlsx":
                    mtype = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
                    break;
                case ".ppt":
                    mtype="application/vnd.ms-powerpoint";
                    break;
                case ".pptx":
                    mtype="application/vnd.ms-powerpoint";
                    break;
                default:
                    mtype="application/*";
                    break;
            }

        }
        intent.setDataAndType(Uri.fromFile(file), mtype);
        try{
            context.startActivity(intent);
            //context.start
        }catch(android.content.ActivityNotFoundException e){
            Toast.makeText(context, "Could not open file", Toast.LENGTH_SHORT).show();
        }
    }

    public static List<Integer> getpoints(String string,String substr){
        int lastIndex = 0;
        int count =0;
        List<Integer> pointslist = new ArrayList<Integer>();
        while(lastIndex != -1){
            lastIndex = string.indexOf(substr,lastIndex);


            if( lastIndex != -1){
                pointslist.add(lastIndex);
                count ++;
                lastIndex+=substr.length();
            }
        }
        return pointslist;

    }



}
