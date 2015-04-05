package com.aquilibra.xavier.msbm;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


import java.io.File;
import java.io.UnsupportedEncodingException;


public class MainActivity extends Activity {

    HTML5WebView mWebView;
    String fileName;
    private LinearLayout mContentView;
    private FrameLayout mCustomViewContainer;
    private WebChromeClient.CustomViewCallback mCustomViewCallback;
    FrameLayout.LayoutParams COVER_SCREEN_GRAVITY_CENTER = new FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mWebView = new HTML5WebView(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFEB3B")));
        //mWebView = (HTML5WebView) findViewById(R.id.activity_main_webview);
        //WebSettings webSettings = mWebView.getSettings();
        //mWebView.getSettings().setLoadWithOverviewMode(true);
        //mWebView.getSettings().setUseWideViewPort(true);
        mWebView.setBackgroundColor(Color.YELLOW);
        mWebView.setBackgroundResource(R.drawable.nsplash);
        mWebView.getSettings().setAppCacheEnabled(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        mWebView.setScrollbarFadingEnabled(false);
        mWebView.setWebViewClient(new HelloWebViewClient());
        DowlonadListen dlist = new DowlonadListen(this);
        mWebView.setDownloadListener(dlist);
        registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

        mWebView.loadUrl("http://kurogo.artuvic.com:8010/home/");
        setContentView(mWebView.getLayout());
        /* Adds Progrss bar Support
        this.getWindow().requestFeature(Window.FEATURE_PROGRESS);
        setContentView(R.layout.activity_main);
        // Makes Progress bar Visible
        getWindow().setFeatureInt( Window.FEATURE_PROGRESS, Window.PROGRESS_VISIBILITY_ON);

        // Sets the Chrome Client, and defines the onProgressChanged
        // This makes the Progress bar be updated.
        final Activity MyActivity = this;
        mWebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress)
            {
                //Make the bar disappear after URL is loaded, and changes string to Loading...
                MyActivity.setTitle("Loading...");
                MyActivity.setProgress(progress * 100); //Make the bar disappear after URL is loaded

                // Return the app name after finish loading
                if(progress == 100)
                    MyActivity.setTitle(R.string.app_name);
            }
        });*/

    }

    private class DowlonadListen implements DownloadListener {
        Context context;

        public DowlonadListen(Context context) {
            this.context = context;

        }

        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
            //for downloading directly through download manager
                /*DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                fileName = url.substring(url.lastIndexOf('/')+1);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "filedownload.pdf");
                DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                dm.enqueue(request);*/
            String result = null;
            try {
                result = java.net.URLDecoder.decode(url, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            fileName = result.substring(result.lastIndexOf('/') + 1, result.indexOf('?'));
            FileDownloader fdown = new FileDownloader(context);
            File file = new File(Environment.getExternalStoragePublicDirectory("/MSBM")
                    + "/files/" + fileName);
            //Toast.makeText(this.context,result,Toast.LENGTH_LONG).show();
            if (file.exists()) {
                FileOpener.openFile(file, this.context);
            } else {
                fdown.download(url, "/files/", fileName);
                //Toast.makeText(this.context,fileName,Toast.LENGTH_LONG).show();
            }
        }


    }

    private class HelloWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView webview, String url) {
            //mWebView.setWebChromeClient(new WebChromeClient() {

        /*private View mCustomView;

                @Override
                public void onShowCustomView(View view, WebChromeClient.CustomViewCallback callback) {
                    // if a view already exists then immediately terminate the new one
                    if (mCustomView != null) {
                        callback.onCustomViewHidden();
                        return;
                    }

                    // Add the custom view to its container.
                    mCustomViewContainer.addView(view, COVER_SCREEN_GRAVITY_CENTER);
                    mCustomView = view;
                    mCustomViewCallback = callback;

                    // hide main browser view
                    mContentView.setVisibility(View.GONE);

                    // Finally show the custom view container.
                    mCustomViewContainer.setVisibility(View.VISIBLE);
                    mCustomViewContainer.bringToFront();
                }*/


            if (url.startsWith("tel:")) {

                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(url));

                startActivity(intent);
            } else if (url.startsWith("http:") || url.startsWith("https:")) {
                mWebView.loadUrl(url);
            }
            return true;
        }



        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            mWebView.loadUrl("file:///android_asset/index.html");
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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


    //New Back Method
    /*@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Check if the key event was the Back button and if there's history
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Toast.makeText(this,mWebView.getUrl(),Toast.LENGTH_LONG).show();
            if (mWebView.canGoBack()) {
                if (mWebView.getUrl().contains("kurogo.artuvic.com:8010/home")) {
                    new AlertDialog.Builder(this)
                            .setTitle("Exit!")
                            .setMessage("Are you sure you want to close?")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }

                            })
                            .setNegativeButton("No", null)
                            .show();
                    return true;
                } else {
                    mWebView.goBack();
                    return true;
                }
            } else {
                new AlertDialog.Builder(this)
                        .setTitle("Exit!")
                        .setMessage("Are you sure you want to close?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }

                        })
                        .setNegativeButton("No", null)
                        .show();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }*/

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            if (mWebView.getUrl().contains("kurogo.artuvic.com:8010/home")) {
                new AlertDialog.Builder(this)
                        .setTitle("Exit!")
                        .setMessage("Are you sure you want to close?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }

                        })
                        .setNegativeButton("No", null)
                        .show();
            } else {
                mWebView.goBack();
            }
        }else{
            new AlertDialog.Builder(this)
                    .setTitle("Exit!")
                    .setMessage("Are you sure you want to close?")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }

                    })
                    .setNegativeButton("No", null)
                    .show();
        }
    }


        BroadcastReceiver onComplete = new BroadcastReceiver() {
            public void onReceive(Context ctxt, Intent intent) {
                Toast.makeText(ctxt, "Download Complete", Toast.LENGTH_LONG).show();
                File file = new File(Environment.getExternalStoragePublicDirectory("/MSBM")
                        + "/files/" + fileName);
                FileOpener.openFile(file, ctxt);
           /* Uri uri = Uri.fromFile(file);
            try {
                Intent intentUrl = new Intent(Intent.ACTION_VIEW);
                intentUrl.setDataAndType(uri,"application/pdf");
                intentUrl.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                ctxt.startActivity(intentUrl);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(ctxt, "Cant Open File", Toast.LENGTH_LONG).show();
            }
        }*/
            }
        };

}


