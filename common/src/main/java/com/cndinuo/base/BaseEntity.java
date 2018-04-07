package com.cndinuo.base;

import com.cndinuo.annotation.Name;

import java.io.Serializable;
import java.util.Date;

/**
 * 公用父类
 * @author dgb
 *
 */
public class BaseEntity implements Serializable{

	@Name("ID")
	private Integer id;

	@Name("创建人")
	private Integer createBy;

	@Name("更新人")
	private Integer updateBy;

	@Name("创建时间")
	private Date createTime;

	@Name("更新时间")
	private Date updateTime;

	@Name("删除标志")
	private Byte deleted;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCreateBy() {
		return createBy;
	}

	public void setCreateBy(Integer createBy) {
		this.createBy = createBy;
	}

	public Integer getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(int updateBy) {
		this.updateBy = updateBy;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Byte getDeleted() {
		return deleted;
	}

	public void setDeleted(Byte deleted) {
		this.deleted = deleted;
	}

}
