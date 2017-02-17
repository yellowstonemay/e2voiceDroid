package com.example.zhoupeng.e2voicedroid;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.zhoupeng.e2voicedroid.lex.E2InteractiveVoiceViewAdapter;

/**
 * Created by yanlixi on 2017/2/15.
 */

public class VideoCommandProcesser implements Runnable {

    private String url;
    private Context context;

    private Dialog videoDialog;
    private VideoView mVideoView;
    private ProgressDialog progressDialog;
    private MediaController mediaControls;
    private E2InteractiveVoiceViewAdapter interactiveVoiceViewAdapter;

    public void init(String url, Context context, E2InteractiveVoiceViewAdapter interactiveVoiceViewAdapter) {
        this.url = url;
        this.context = context;
        this.interactiveVoiceViewAdapter = interactiveVoiceViewAdapter;
    }

    @Override
    public void run() {
        openVideoPopup(url);
    }

    public void startPoup() {
        openVideoPopup(url);
    }

    private void openVideoPopup(String path)
    {
        interactiveVoiceViewAdapter.doLateCancelConversation();

        videoDialog = new Dialog(context,android.R.style.Theme_Translucent_NoTitleBar);
        videoDialog.setContentView(R.layout.layout_video_popup);

        mVideoView = (VideoView)videoDialog.findViewById(R.id.videoView);

        ImageButton btncancel = (ImageButton) videoDialog.findViewById(R.id.videoView_close_button);
        btncancel.setOnClickListener(cancelVideoPopup);

        //String path ="http://daily3gp.com/vids/747.3gp";
//        mVideoView.setOnPreparedListener(this);
//        mVideoView.getHolder().setFixedSize(300, 400);
        videoDialog.show();
        videoDialog.setOnCancelListener(new dislogCancelListener());
        videoDialog.setOnDismissListener(new dislogDismissListener());

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        if (mediaControls == null) {
            mediaControls = new MediaController(context);
        }

        mVideoView.setMediaController(mediaControls);

        mVideoView.setVideoPath(path);
        mVideoView.requestFocus();

        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                progressDialog.dismiss();
                mVideoView.start();
            }
        });
        mVideoView.setOnErrorListener(new MediaPlayer.OnErrorListener(){

            @Override
            public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                progressDialog.dismiss();

                TextView tx = (TextView)videoDialog.findViewById(R.id.video_error_load);
                if(tx != null)
                {
                    tx.setText("Failed to load video because of network issue or content is not available.");
                }
                return false;
            }
        });
        mVideoView.start();
    }

    private View.OnClickListener cancelVideoPopup = new View.OnClickListener() {
        public void onClick(View v) {
            if(videoDialog!=null)
                videoDialog.dismiss();
        }
    };

    private class dislogCancelListener implements DialogInterface.OnCancelListener {
        @Override
        public void onCancel(DialogInterface dialogInterface) {
            if(videoDialog != null) {
                videoDialog = null;
                interactiveVoiceViewAdapter.autoStartNewConversation();
            }
        }
    }
    private class dislogDismissListener implements DialogInterface.OnDismissListener {

        @Override
        public void onDismiss(DialogInterface dialogInterface) {
            if(videoDialog != null) {
                videoDialog = null;
                interactiveVoiceViewAdapter.autoStartNewConversation();
            }
        }
    }


}
