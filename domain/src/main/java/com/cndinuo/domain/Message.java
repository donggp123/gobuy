package com.cndinuo.domain;

import com.cndinuo.annotation.Name;

import java.io.Serializable;
import java.util.Date;

/**
 * 消息类
 * @author dgb
 */
public class Message implements Serializable{

	@Name("ID")
	private Long id;

    @Name("消息标题")
	private String title;

    @Name("发送者")
	private Integer from;

    @Name("发送者名称")
	private String fromName;

    @Name("接收者")
	private Integer to;

    @Name("接收者名称")
	private String toName;

    @Name("信息内容")
	private String text;

    @Name("发送时间")
	private Date sendTime;

    @Name("状态")
	private Byte status; //0 未读，1 已读

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getToName() {
		return toName;
	}

	public void setToName(String toName) {
		this.toName = toName;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public Integer getFrom() {
		return from;
	}

	public void setFrom(Integer from) {
		this.from = from;
	}

	public Integer getTo() {
		return to;
	}

	public void setTo(Integer to) {
		this.to = to;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

}
