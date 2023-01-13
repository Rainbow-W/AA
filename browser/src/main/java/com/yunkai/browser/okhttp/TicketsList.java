package com.yunkai.browser.okhttp;

import java.util.List;

/**
 * 创建时间：2021/1/26
 *
 * @version 1.0
 * @auther ZhanHaoYuan
 */
public class TicketsList {

    int errcode;
    String errmsg;
    List<Tickets> data;

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

    public List<Tickets> getData() {
        return data;
    }

    public void setData(List<Tickets> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "TicketsList{" +
                "errcode='" + errcode + '\'' +
                ", errmsg='" + errmsg + '\'' +
                ", data=" + data +
                '}';
    }

    public class Tickets {
        String id;
        String name;
        String fee;
        String shareimg;
        int  stock;
        Bindinfo bindinfo;


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getFee() {
            return fee;
        }

        public void setFee(String fee) {
            this.fee = fee;
        }

        public String getShareimg() {
            return shareimg;
        }

        public void setShareimg(String shareimg) {
            this.shareimg = shareimg;
        }

        public Bindinfo getBindinfo() {
            return bindinfo;
        }

        public void setBindinfo(Bindinfo bindinfo) {
            this.bindinfo = bindinfo;
        }

        public int getStock() {
            return stock;
        }

        public void setStock(int stock) {
            this.stock = stock;
        }

        @Override
        public String toString() {
            return "Tickets{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", fee='" + fee + '\'' +
                    ", shareimg='" + shareimg + '\'' +
                    ", stock=" + stock +
                    ", bindinfo=" + bindinfo +
                    '}';
        }
    }

    public class Bindinfo {
        int name;       //0不显示，1必填，2选填
        int mobile;     //0不显示，1必填，2选填
        int idcard;     //0不显示，1必填，2选填

        public int getName() {
            return name;
        }

        public void setName(int name) {
            this.name = name;
        }

        public int getMobile() {
            return mobile;
        }

        public void setMobile(int mobile) {
            this.mobile = mobile;
        }

        public int getIdcard() {
            return idcard;
        }

        public void setIdcard(int idcard) {
            this.idcard = idcard;
        }


        @Override
        public String toString() {
            return "Bindinfo{" +
                    "name=" + name +
                    ", mobile=" + mobile +
                    ", idcard=" + idcard +
                    '}';
        }
    }


}
