package com.cndinuo.domain;

import com.cndinuo.annotation.Name;

import java.io.Serializable;

public class SysDict implements Serializable {
    @Name("ID")
    private Integer id;

    @Name("表名")
    private String table;

    @Name("字段名")
    private String field;

    @Name("类型")
    private String type;

    @Name("名称")
    private String name;

    @Name("值")
    private String value;

    @Name("排序")
    private Integer sort;

    @Name("状态")
    private Byte status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table == null ? null : table.trim();
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field == null ? null : field.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }
}