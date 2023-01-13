package com.yunkai.browser.okhttp;

import java.util.List;

/**
 * 创建时间：2021/1/21
 *
 * @version 1.0
 * @auther ZhanHaoYuan
 */
public class ScanErrBean {
    int errcode;
    String errmsg;
    List<TicketBean> data;
    InfoBean info;
    TimeOutData infos;

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

    public List<TicketBean> getData() {
        return data;
    }

    public void setData(List<TicketBean> data) {
        this.data = data;
    }

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public TimeOutData getInfos() {
        return infos;
    }

    public void setInfos(TimeOutData infos) {
        this.infos = infos;
    }

    @Override
    public String toString() {
        return "ScanErrBean{" +
                "errcode='" + errcode + '\'' +
                ", errmsg='" + errmsg + '\'' +
                ", data=" + data +
                ", info=" + info +
                ", infos=" + infos +
                '}';
    }

    public class InfoBean {
        String title;                                   //票名
        String fee;                                     //票价格
        String id;                                      //票券ID
        String group_number;                            //票券人数
        String createtime;                              //核销时间
        String odcode;                                  //票券码
        String sub_business_name;                       //园区

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getFee() {
            return fee;
        }

        public void setFee(String fee) {
            this.fee = fee;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getGroup_number() {
            return group_number;
        }

        public void setGroup_number(String group_number) {
            this.group_number = group_number;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getOdcode() {
            return odcode;
        }

        public void setOdcode(String odcode) {
            this.odcode = odcode;
        }

        public String getSub_business_name() {
            return sub_business_name;
        }

        public void setSub_business_name(String sub_business_name) {
            this.sub_business_name = sub_business_name;
        }


        @Override
        public String toString() {
            return "InfoBean{" +
                    "title='" + title + '\'' +
                    ", fee='" + fee + '\'' +
                    ", id='" + id + '\'' +
                    ", group_number='" + group_number + '\'' +
                    ", createtime='" + createtime + '\'' +
                    ", odcode='" + odcode + '\'' +
                    ", sub_business_name='" + sub_business_name + '\'' +
                    '}';
        }
    }


    public class TimeOutData {
        String errcode;
        String orderid; //订单票券id
        String actid; //票种id
        String item_id; //核销项目id
        String logid;//入园检票记录id
        int overtime;//超出时长，单位分钟（0为未超时）
        String ticket_name; //票名
        String item_name; //核销项目名
        String over_price; //超时费用，单位元（0为不收款）
        String intime;//入园时间

        public String getErrcode() {
            return errcode;
        }

        public void setErrcode(String errcode) {
            this.errcode = errcode;
        }

        public String getOrderid() {
            return orderid;
        }

        public void setOrderid(String orderid) {
            this.orderid = orderid;
        }

        public String getActid() {
            return actid;
        }

        public void setActid(String actid) {
            this.actid = actid;
        }

        public String getItem_id() {
            return item_id;
        }

        public void setItem_id(String item_id) {
            this.item_id = item_id;
        }

        public String getLogid() {
            return logid;
        }

        public void setLogid(String logid) {
            this.logid = logid;
        }

        public int getOvertime() {
            return overtime;
        }

        public void setOvertime(int overtime) {
            this.overtime = overtime;
        }

        public String getTicket_name() {
            return ticket_name;
        }

        public void setTicket_name(String ticket_name) {
            this.ticket_name = ticket_name;
        }

        public String getItem_name() {
            return item_name;
        }

        public void setItem_name(String item_name) {
            this.item_name = item_name;
        }

        public String getOver_price() {
            return over_price;
        }

        public void setOver_price(String over_price) {
            this.over_price = over_price;
        }

        public String getIntime() {
            return intime;
        }

        public void setIntime(String intime) {
            this.intime = intime;
        }

        @Override
        public String toString() {
            return "TimeOutData{" +
                    "errcode='" + errcode + '\'' +
                    ", orderid='" + orderid + '\'' +
                    ", actid='" + actid + '\'' +
                    ", item_id='" + item_id + '\'' +
                    ", logid='" + logid + '\'' +
                    ", overtime='" + overtime + '\'' +
                    ", ticket_name='" + ticket_name + '\'' +
                    ", item_name='" + item_name + '\'' +
                    ", over_price='" + over_price + '\'' +
                    ", intime='" + intime + '\'' +
                    '}';
        }
    }

}
