package com.aquilibra.xavier.msbm;

/**
 * Created by Xavier on 3/13/2015.
 */
import java.io.File;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.widget.Toast;

public class FileDownloader{
    File file;
    Context context;
    public FileDownloader(Context context){
        this.context = context;
        // getBaseContext().registerReceiver(onComplete,
        // new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public long download(String file_url,String file_path,String filename){

        //registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        file = new File(Environment.getExternalStoragePublicDirectory("/MSBM")+file_path);

        if(!file.exists()){
            file.mkdirs();
        }

        DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Request req = new Request(Uri.parse(file_url));
        try{
            req.setDestinationInExternalPublicDir("/MSBM",file_path+filename);
        }catch(Exception exec){
            Toast.makeText(context, "No Storage Found", Toast.LENGTH_SHORT).show();
            return 0;
        }

        if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
            req.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        }
        req.setTitle(filename);
        req.setDescription("File Download");


        return manager.enqueue(req);

    }

	/*BroadcastReceiver onComplete = new BroadcastReceiver(){


	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		//FileOpener.openFile(file, context);
	}
	};*/
}
