package com.yunkai.browser.okhttp;

/**
 * 创建时间：2021/1/21
 *
 * @version 1.0
 * @auther ZhanHaoYuan
 */
public class LoginErrBean {
    private int errcode;
    private String errmsg;
    private UserBean data;

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public UserBean getData() {
        return data;
    }

    public void setData(UserBean data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "LoginErrBean{" +
                "errcode='" + errcode + '\'' +
                ", errmsg='" + errmsg + '\'' +
                ", data=" + data +
                '}';
    }
}
