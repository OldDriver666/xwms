package com.fise.model.entity;

import java.io.Serializable;

/**
 * @author 
 */
public class AppInformation implements Serializable {
    private Integer id;

    /**
     * APP唯一标示
     */
    private String appIndex;

    /**
     * 应用名称
     */
    private String appName;

    /**
     * 开发者ID
     */
    private Integer devId;

    /**
     * 开发者名称
     */
    private String devName;

    private String topCategory;

    private String category;

    /**
     * 0-待审核 1-发布 2-拒绝 3-下架
     */
    private Integer status;

    /**
     * 应用简介
     */
    private String desc;

    private String version;

    /**
     * 应用图标
     */
    private String icon;

    private String images;

    /**
     * 下载地址
     */
    private String download;

    private String size;

    private Integer updated;

    private Integer created;

    private static final long serialVersionUID = 1L;

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

    public Integer getCreated() {
        return created;
    }

    public void setCreated(Integer created) {
        this.created = created;
    }
}