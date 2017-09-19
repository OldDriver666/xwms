package com.fise.model.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fise.model.entity.AppInformation;
import com.fise.utils.JsonUtil;

public class AppDetailResult {

    @JsonProperty("app_id")
    private Integer id;

    @JsonProperty("app_index")
    private String appIndex;

    @JsonProperty("app_name")
    private String appName;

    @JsonProperty("dev_id")
    private Integer devId;

    @JsonProperty("dev_name")
    private String devName;

    @JsonProperty("top_category")
    private String topCategory;

    private String category;

    private Integer status;

    private String desc;

    private String version;

    private String icon;

    private String images;

    private String download;

    private String size;

    private Integer updated;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppIndex() {
        return appIndex;
    }

    public void setAppIndex(String appIndex) {
        this.appIndex = appIndex;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Integer getDevId() {
        return devId;
    }

    public void setDevId(Integer devId) {
        this.devId = devId;
    }

    public String getDevName() {
        return devName;
    }

    public void setDevName(String devName) {
        this.devName = devName;
    }

    public String getTopCategory() {
        return topCategory;
    }

    public void setTopCategory(String topCategory) {
        this.topCategory = topCategory;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getDownload() {
        return download;
    }

    public void setDownload(String download) {
        this.download = download;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Integer getUpdated() {
        return updated;
    }

    public void setUpdated(Integer updated) {
        this.updated = updated;
    }

    public void init(AppInformation data){
        this.id = data.getId();
        this.appIndex = data.getAppIndex();
        this.appName = data.getAppName();
        this.devId = data.getDevId();
        this.devName = data.getDevName();
        this.topCategory = data.getTopCategory();
        this.category = data.getCategory();
        this.status = data.getStatus();
        this.desc = data.getDesc();
        this.version = data.getVersion();
        this.icon = data.getIcon();
        this.images = data.getImages();
        this.download = data.getDownload();
        this.size = data.getSize();
        this.updated = data.getUpdated();
    }
    
    @Override
    public String toString() {
        return JsonUtil.toJson(this);
    }
}
