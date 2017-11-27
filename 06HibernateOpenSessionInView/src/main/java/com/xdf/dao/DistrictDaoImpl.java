package com.xdf.dao;

import com.xdf.bean.District;
import com.xdf.util.SessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.Serializable;

public class DistrictDaoImpl  implements DistrictDao {

    public District getDistrictById(Serializable id) {
      Session session= SessionFactoryUtil.getCurrentSession();
      Transaction transaction= session.beginTransaction();
      District district= (District) session.load(District.class,id);
        System.out.println("daoImpl中的=======》"+session.hashCode());
        return district;
    }
}
