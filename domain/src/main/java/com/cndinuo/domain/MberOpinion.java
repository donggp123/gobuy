package com.cndinuo.domain;

import java.io.Serializable;
import java.util.Date;

public class MberOpinion implements Serializable {
    private Integer id;

    private Integer mberId;

    private String content;

    private Date opTime;

    private String nickName;

    private String headIcon;

    private String mobile;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMberId() {
        return mberId;
    }

    public void setMberId(Integer mberId) {
        this.mberId = mberId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Date getOpTime() {
        return opTime;
    }

    public void setOpTime(Date opTime) {
        this.opTime = opTime;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getHeadIcon() {
        return headIcon;
    }

    public void setHeadIcon(String headIcon) {
        this.headIcon = headIcon;
    }

}