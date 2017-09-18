package com.fise.model.result;

import java.util.List;

public class UserLocationInfo {
	private Integer total_cnt;
	private Integer online_cnt;
	//private List<UserLocation> user_list;
	private List<UserDetail> user_list;

    public Integer getTotal_cnt() {
        return total_cnt;
    }
    public void setTotal_cnt(Integer total_cnt) {
        this.total_cnt = total_cnt;
    }
    public Integer getOnline_cnt() {
        return online_cnt;
    }
    public void setOnline_cnt(Integer online_cnt) {
        this.online_cnt = online_cnt;
    }
/*    public List<UserLocation> getUser_list() {
        return user_list;
    }
    public void setUser_list(List<UserLocation> user_list) {
        this.user_list = user_list;
    }
    */
    public List<UserDetail> getUser_list() {
        return user_list;
    }
    public void setUser_list(List<UserDetail> user_list) {
        this.user_list = user_list;
    }
}
