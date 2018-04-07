package com.cndinuo.domain;

import com.cndinuo.annotation.Name;

import java.io.Serializable;

public class MrhtGoodsClass implements Serializable {

    @Name("ID")
    private Integer id;

    @Name("类型编码")
    private String code;

    @Name("父类编码")
    private String parCode;

    @Name("分类名称")
    private String name;

    @Name("备注")
    private String remark;

    @Name("分类级别")
    private Byte grade;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getParCode() {
        return parCode;
    }

    public void setParCode(String parCode) {
        this.parCode = parCode == null ? null : parCode.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Byte getGrade() {
        return grade;
    }

    public void setGrade(Byte grade) {
        this.grade = grade;
    }
}