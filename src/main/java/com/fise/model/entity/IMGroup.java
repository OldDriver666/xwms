package com.fise.model.entity;

import java.io.Serializable;

/**
 * @author 
 */
public class IMGroup implements Serializable {
    private Integer id;

    /**
     * 群名称
     */
    private String name;

    /**
     * 群头像
     */
    private String avatar;

    /**
     * 创建者用户id
     */
    private Integer creator;

    /**
     * 群组类型，1-固定;2-临时群
     */
    private Byte type;

    /**
     * 成员人数
     */
    private Integer usercnt;

    /**
     * 是否删除,0-正常，1-删除
     */
    private Byte status;

    /**
     * 群版本号
     */
    private Integer version;

    /**
     * 群公告
     */
    private String board;

    /**
     * 群公告时间
     */
    private Integer boardTime;

    /**
     * 最后聊天时间
     */
    private Integer lastchated;

    /**
     * 更新时间
     */
    private Integer updated;

    /**
     * 创建时间
     */
    private Integer created;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Integer getUsercnt() {
        return usercnt;
    }

    public void setUsercnt(Integer usercnt) {
        this.usercnt = usercnt;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public Integer getBoardTime() {
        return boardTime;
    }

    public void setBoardTime(Integer boardTime) {
        this.boardTime = boardTime;
    }

    public Integer getLastchated() {
        return lastchated;
    }

    public void setLastchated(Integer lastchated) {
        this.lastchated = lastchated;
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