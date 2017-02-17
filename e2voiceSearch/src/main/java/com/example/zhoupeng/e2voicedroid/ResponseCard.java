package com.example.zhoupeng.e2voicedroid;

import java.util.ArrayList;

/**
 * Created by yanlixi on 2017/2/12.
 */

public class ResponseCard {

    private String videoUrl;
    private String additionMsg;
    private String WebUrl;
    private ArrayList options;

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getAdditionMsg() {
        return additionMsg;
    }

    public void setAdditionMsg(String additionMsg) {
        this.additionMsg = additionMsg;
    }

    public String getWebUrl() {
        return WebUrl;
    }

    public void setWebUrl(String webUrl) {
        WebUrl = webUrl;
    }

    public ArrayList getOptions() {
        return options;
    }

    public void setOptions(ArrayList options) {
        this.options = options;
    }
}
