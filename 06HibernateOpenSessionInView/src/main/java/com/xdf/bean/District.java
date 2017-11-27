package com.xdf.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 区县对应的实体类
 */
public class District {

    private  Integer   id;  //区县id
    private  String   name; //区县名称




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

    public District(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "District{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
