package com.yunkai.browser.okhttp;

/**
 * 创建时间：2023/1/13
 *
 * @version 1.0
 * @auther ZhanHaoYuan
 */
public class IcCardPayErr {
    private int errcode;
    private String errmsg;

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

    @Override
    public String toString() {
        return "IcCardPayErr{" +
                "errcode=" + errcode +
                ", errmsg='" + errmsg + '\'' +
                '}';
    }
}
