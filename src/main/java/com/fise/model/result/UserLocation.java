package com.fise.model.result;

public class UserLocation {
    private String uname;
    private Integer uid;
    private Integer online_status;
    private String location_x;
    private String location_y;
    public String getUname() {
        return uname;
    }
    public void setUname(String uname) {
        this.uname = uname;
    }
    public Integer getUid() {
        return uid;
    }
    public void setUid(Integer uid) {
        this.uid = uid;
    }
    public Integer getOnline_status() {
        return online_status;
    }
    public void setOnline_status(Integer online_status) {
        this.online_status = online_status;
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
}