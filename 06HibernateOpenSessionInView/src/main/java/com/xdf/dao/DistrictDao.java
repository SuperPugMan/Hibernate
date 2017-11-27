package com.xdf.dao;

import com.xdf.bean.District;

import java.io.Serializable;

public interface DistrictDao {

     //根据id查询出指定的区县
    District  getDistrictById(Serializable id);
}
