package com.yunkai.browser.okhttp;

/**
 * 创建时间：2021/1/21
 *
 * @version 1.0
 * @auther ZhanHaoYuan
 */
public class UserBean {

    String clerkid;//登录员工id

    String storeid;//所属门店id

    String subid;//所属门店分店id

    String name;//员工名称

    String mobile;//员工手机号码

    String store_name;//所属门店名称

    String sub_store_name;//所属门店分店名称

    String userid;//员工用户id

    public String getClerkid() {
        return clerkid;
    }

    public void setClerkid(String clerkid) {
        this.clerkid = clerkid;
    }

    public String getStoreid() {
        return storeid;
    }

    public void setStoreid(String storeid) {
        this.storeid = storeid;
    }

    public String getSubid() {
        return subid;
    }

    public void setSubid(String subid) {
        this.subid = subid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public String getSub_store_name() {
        return sub_store_name;
    }

    public void setSub_store_name(String sub_store_name) {
        this.sub_store_name = sub_store_name;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }


    @Override
    public String toString() {
        return "UserBean{" +
                "clerkid='" + clerkid + '\'' +
                ", storeid='" + storeid + '\'' +
                ", subid='" + subid + '\'' +
                ", name='" + name + '\'' +
                ", mobile='" + mobile + '\'' +
                ", store_name='" + store_name + '\'' +
                ", sub_store_name='" + sub_store_name + '\'' +
                ", userid='" + userid + '\'' +
                '}';
    }
}
