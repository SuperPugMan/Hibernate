package com.xdf.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 区县对应的实体类
 */
public class District {

    private  Integer   id;  //区县id
    private  String   name; //区县名称
    // 一个区县对应多个街道
    List<Street> streets=new ArrayList<Street>();

    public List<Street> getStreets() {
        return streets;
    }

    public void setStreets(List<Street> streets) {
        this.streets = streets;
    }

    public District(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

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

    public District() {
    }

    public District(Integer id, String name, List<Street> streets) {
        this.id = id;
        this.name = name;
        this.streets = streets;
    }

    @Override
    public String toString() {
        return "District{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", streets=" + streets.size() +
                '}';
    }
}
