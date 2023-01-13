package com.yunkai.browser.okhttp;

/**
 * 创建时间：2021/1/27
 *
 * @version 1.0
 * @auther ZhanHaoYuan
 */
public class MemberListDean {

    int errcode;
    String errmsg;
    MemberDean data;

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

    public MemberDean getData() {
        return data;
    }

    public void setData(MemberDean data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "MemberListDean{" +
                "errcode='" + errcode + '\'' +
                ", errmsg='" + errmsg + '\'' +
                ", data=" + data +
                '}';
    }

    public class MemberDean {
        String uid;                             //会员ID  ~
        String cardsn;                          //会员卡号 ~
        String cardnum;                         //会员IC卡号 ~
        String mobile;                          //手机  ~
        String credit1;                         //积分  ~
        String credit2;                         //余额 ~
        String nickname;                        //昵称
        String realname;                        //姓名 ~
        String openid;                          //粉丝openid
        String photo;                           //会员头像 ~
        String status;                          //状态
        String type_id;                         //会员卡类型ID
        String cardtype;                        //会员卡类型  ~
        String idcard;                          //身份证  ~

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getCardsn() {
            return cardsn;
        }

        public void setCardsn(String cardsn) {
            this.cardsn = cardsn;
        }

        public String getCardnum() {
            return cardnum;
        }

        public void setCardnum(String cardnum) {
            this.cardnum = cardnum;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getCredit1() {
            if (credit1 == null)
                return "0";
            return credit1;
        }

        public void setCredit1(String credit1) {
            this.credit1 = credit1;
        }

        public String getCredit2() {
            return credit2;
        }

        public void setCredit2(String credit2) {
            this.credit2 = credit2;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getOpenid() {
            return openid;
        }

        public void setOpenid(String openid) {
            this.openid = openid;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getType_id() {
            return type_id;
        }

        public void setType_id(String type_id) {
            this.type_id = type_id;
        }

        public String getCardtype() {
            return cardtype;
        }

        public void setCardtype(String cardtype) {
            this.cardtype = cardtype;
        }

        public String getIdcard() {
            return idcard;
        }

        public void setIdcard(String idcard) {
            this.idcard = idcard;
        }


        @Override
        public String toString() {
            return "MemberDean{" +
                    "uid='" + uid + '\'' +
                    ", cardsn='" + cardsn + '\'' +
                    ", cardnum='" + cardnum + '\'' +
                    ", mobile='" + mobile + '\'' +
                    ", credit1='" + credit1 + '\'' +
                    ", credit2='" + credit2 + '\'' +
                    ", nickname='" + nickname + '\'' +
                    ", realname='" + realname + '\'' +
                    ", openid='" + openid + '\'' +
                    ", photo='" + photo + '\'' +
                    ", status='" + status + '\'' +
                    ", type_id='" + type_id + '\'' +
                    ", cardtype='" + cardtype + '\'' +
                    ", idcard='" + idcard + '\'' +
                    '}';
        }
    }
}