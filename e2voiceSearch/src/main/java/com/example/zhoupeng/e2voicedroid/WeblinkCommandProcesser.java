package com.example.zhoupeng.e2voicedroid;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;

import com.example.zhoupeng.e2voicedroid.lex.E2InteractiveVoiceViewAdapter;

/**
 * Created by yanlixi on 2017/2/15.
 */

public class WeblinkCommandProcesser implements Runnable {

    private String url;
    private Context context;
    private E2InteractiveVoiceViewAdapter interactiveVoiceViewAdapter;


    private Dialog webDialog;
    private WebView mWebView;

    public void init(String url, Context context, E2InteractiveVoiceViewAdapter interactiveVoiceViewAdapter) {
        this.url = url;
        this.context = context;
        this.interactiveVoiceViewAdapter = interactiveVoiceViewAdapter;
    }

    @Override
    public void run() {
        openWebUrl(url);
    }

    public void startPoup() {
        openWebUrl(url);
    }

    private void openInternalVebView(String url)
    {
        interactiveVoiceViewAdapter.doLateCancelConversation();
        webDialog = new Dialog(context,android.R.style.Theme_Translucent_NoTitleBar);
        webDialog.setContentView(R.layout.layout_webview_popup);

        mWebView = (WebView)webDialog.findViewById(R.id.webView);

        ImageButton btncancel = (ImageButton) webDialog.findViewById(R.id.webView_close_button);
        btncancel.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                if(webDialog!=null)
                    webDialog.dismiss();
            }
        });

        mWebView.loadUrl(url);

        webDialog.show();
        webDialog.setOnCancelListener(new DialogInterface.OnCancelListener(){

            @Override
            public void onCancel(DialogInterface dialogInterface) {
                if(webDialog != null) {
                    webDialog = null;
                    interactiveVoiceViewAdapter.autoStartNewConversation();
                }
            }
        });
        webDialog.setOnDismissListener(new DialogInterface.OnDismissListener (){

            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                if(webDialog != null) {
                    webDialog = null;
                    interactiveVoiceViewAdapter.autoStartNewConversation();
                }
            }
        });
    }

    private void openWebUrl(String url)
    {
        interactiveVoiceViewAdapter.doLateCancelConversation();

        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        context.startActivity(i);
    }
}
