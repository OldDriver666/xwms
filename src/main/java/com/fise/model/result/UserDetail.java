package com.fise.model.result;

import java.util.Random;

import com.fise.model.entity.IMUser;

public class UserDetail {
    private Integer uid;
    private Integer sex;
    private String account;
    private String avatar;
    private String nick;
    private String province;
    private String city;
    private String address;
    private String phone;
    private String sos_phone;
    private Integer height;
    private Integer weight;
    private Integer online_status;
    private Integer online_time;
    private String location_x;
    private String location_y;
    private Integer battery;
    private Integer sq;
    private Integer step_cnt;
    public Integer getUid() {
        return uid;
    }
    public void setUid(Integer uid) {
        this.uid = uid;
    }
    public Integer getSex() {
        return sex;
    }
    public void setSex(Integer sex) {
        this.sex = sex;
    }
    public String getAccount() {
        return account;
    }
    public void setAccount(String account) {
        this.account = account;
    }
    public String getNick() {
        return nick;
    }
    public void setNick(String nick) {
        this.nick = nick;
    }
    public String getProvince() {
        return province;
    }
    public void setProvince(String province) {
        this.province = province;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public Integer getHeight() {
        return height;
    }
    public void setHeight(Integer height) {
        this.height = height;
    }
    public Integer getWeight() {
        return weight;
    }
    public void setWeight(Integer weight) {
        this.weight = weight;
    }
    public Integer getOnline_status() {
        return online_status;
    }
    public void setOnline_status(Integer online_status) {
        this.online_status = online_status;
    }
    public Integer getOnline_time() {
        return online_time;
    }
    public void setOnline_time(Integer online_time) {
        this.online_time = online_time;
    }
    public String getLocation_x() {
        return location_x;
    }
    public void setLocation_x(String location_x) {
        this.location_x = location_x;
    }
    public String getLocation_y() {
        return location_y;
    }
    public void setLocation_y(String location_y) {
        this.location_y = location_y;
    }
    public Integer getBattery() {
        return battery;
    }
    public void setBattery(Integer battery) {
        this.battery = battery;
    }
    public Integer getSq() {
        return sq;
    }
    public void setSq(Integer sq) {
        this.sq = sq;
    }

    public void initUserDetail(IMUser u){
        this.uid = u.getId();
        this.sex = u.getSex();
        this.account = u.getDomain();
        this.nick = u.getNick();
        this.province = u.getProvince();
        this.city = u.getCity();
        this.avatar = u.getAvatar();
//        this.phone = u.getPhone();
        this.phone = "xxxxxxxxxxxxx";
        this.height = u.getHeight();
        this.weight = u.getWeight();
        this.online_status = u.getOnlineStatus();
        this.online_time = u.getLastOnlineTime();
        this.location_x = u.getLat();
        this.location_y = u.getLng();
        this.battery = u.getBattery();
        this.sq = u.getSq();
        this.address = u.getAddress();
        //TODO delete
        this.sos_phone = "15985648002";
        Random random = new Random();
        int s = random.nextInt(9999)%(300);
        this.step_cnt = s;
    }
    public Integer getStep_cnt() {
        return step_cnt;
    }
    public void setStep_cnt(Integer step_cnt) {
        this.step_cnt = step_cnt;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getAvatar() {
        return avatar;
    }
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    public String getSos_phone() {
        return sos_phone;
    }
    public void setSos_phone(String sos_phone) {
        this.sos_phone = sos_phone;
    }
}