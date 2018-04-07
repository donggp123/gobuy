package com.cndinuo.vo;

import java.io.Serializable;
import java.util.List;

public class GoodsClassVO implements Serializable{

    private String id;

    private String pId;

    private String name;

    private List<GoodsClassVO> son;

    public GoodsClassVO() {}

    public GoodsClassVO(GoodsClassVO vo) {
        this.id = vo.getId();
        this.pId = vo.getPId();
        this.name = vo.getName();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPId() {
        return pId;
    }

    public void setPId(String pId) {
        this.pId = pId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<GoodsClassVO> getSon() {
        return son;
    }

    public void setSon(List<GoodsClassVO> son) {
        this.son = son;
    }
}
