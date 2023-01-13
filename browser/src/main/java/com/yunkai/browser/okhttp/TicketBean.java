package com.yunkai.browser.okhttp;

/**
 * 创建时间：2021/1/22
 *
 * @version 1.0
 * @auther ZhanHaoYuan
 */
public class TicketBean {

    String id;//单项票活动id
    String name;//单项票活动名

    public TicketBean(String id, String name) {
        this.id = id;
        this.name = name;

    }

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

    @Override
    public String toString() {
        return "TicketBean{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
