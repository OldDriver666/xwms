package com.fise.model.entity;

import java.io.Serializable;

/**
 * @author 
 */
public class Wiki implements Serializable {
    private Integer id;

    /**
     * 1-入门指南 2-常见问题 3-使用技巧 4-高级版相关 5-账号相关 6-关于我们
     */
    private Integer type;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 有帮助
     */
    private Integer helpful;

    /**
     * 没帮助
     */
    private Integer nohelp;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getHelpful() {
        return helpful;
    }

    public void setHelpful(Integer helpful) {
        this.helpful = helpful;
    }

    public Integer getNohelp() {
        return nohelp;
    }

    public void setNohelp(Integer nohelp) {
        this.nohelp = nohelp;
    }
}