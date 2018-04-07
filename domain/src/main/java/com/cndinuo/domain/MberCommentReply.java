package com.cndinuo.domain;

import java.io.Serializable;
import java.util.Date;

public class MberCommentReply implements Serializable {
    private Integer id;

    private Integer comId;

    private Integer mrhtId;

    private String content;

    private Date replyTime;

    private String nickName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getComId() {
        return comId;
    }

    public void setComId(Integer comId) {
        this.comId = comId;
    }

    public Integer getMrhtId() {
        return mrhtId;
    }

    public void setMrhtId(Integer mrhtId) {
        this.mrhtId = mrhtId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Date getReplyTime() {
        return replyTime;
    }

    public void setReplyTime(Date replyTime) {
        this.replyTime = replyTime;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}