package com.aquilibra.xavier.msbm;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.net.HttpURLConnection;
import java.net.URL;


public class ImageDialog extends Activity {

    String url;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_dialog);
        //Declare Stuff
        final AlertDialog.Builder alert = new AlertDialog.Builder(this,AlertDialog.THEME_HOLO_LIGHT);
        final WebView adView = new WebView(this);

        url = "http://www.mona.uwi.edu/msbm/sites/default/files/msbm/images/msbmobilead.png";
        //url = "http://www.multyshades.com/wp-content/uploads/2010/07/Pepsi_Commercial_by_mindfuckx.jpg";

        final String data = "<head><style type='text/css'>body{margin:auto auto;text-align:center;} img{width:100%25;} </style>" +
                "</head><body><img src='" + url + "' /></body>";

        adView.loadData(data, "text/html", null);
        // adView.loadUrl("http://138studentliving.com/wp-content/uploads/2015/03/site_logo1.png");
        adView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        //Implement the dialogs features
        alert.setView(adView);
        alert.setNegativeButton("Close Ad.", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                ImageDialog.this.finish();
            }
        });
        alert.setCancelable(false);

        alert.show();



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_image_dialog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}