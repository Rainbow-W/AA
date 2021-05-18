package com.yunkai.browser.utils;

import java.net.URL;

/**
 * Created by Administrator on 2017/10/31.
 */

public class NetApplication {

    private URL iconUrl ;//图片链接
    private String appName;
    private String url ;//点击跳转的链接


    public NetApplication(URL iconUrl, String appName,String url ){
        setAppName(appName);
        setIconUrl(iconUrl);
        setUrl(url);
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public URL getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(URL iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
