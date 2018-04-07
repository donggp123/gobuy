package com.cndinuo.domain;

import java.io.Serializable;
import java.util.Date;

public class MberInfo implements Serializable {

    private Integer mberId;

    private String nickName;

    private String headIcon;

    private Byte gender;

    private Date birthday;

    private Date registerTime;

    private Date lastLogTime;

    private Integer logCount;

    private Byte status;

    public Integer getMberId() {
        return mberId;
    }

    public void setMberId(Integer mberId) {
        this.mberId = mberId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    public String getHeadIcon() {
        return headIcon;
    }

    public void setHeadIcon(String headIcon) {
        this.headIcon = headIcon == null ? null : headIcon.trim();
    }

    public Byte getGender() {
        return gender;
    }

    public void setGender(Byte gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public Date getLastLogTime() {
        return lastLogTime;
    }

    public void setLastLogTime(Date lastLogTime) {
        this.lastLogTime = lastLogTime;
    }

    public Integer getLogCount() {
        return logCount;
    }

    public void setLogCount(Integer logCount) {
        this.logCount = logCount;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }
}