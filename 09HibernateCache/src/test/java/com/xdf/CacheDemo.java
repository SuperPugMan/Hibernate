package com.xdf;

import com.xdf.bean.Dept;
import com.xdf.util.SessionFactoryUtil;
import org.hibernate.CacheMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Before;
import org.junit.Test;

public class CacheDemo {



    Session session=null;
    Transaction transaction=null;

    @Before
    public  void  before(){
        session= SessionFactoryUtil.getCurrentSession();
        transaction= session.beginTransaction();
    }


    /**
     * 验证2级缓存是否配置成功
     */
    @Test
    public   void  test1(){
        Dept dept= (Dept) session.get(Dept.class,1); //获取指定的部门信息
        session.clear();//清空一级缓存   ，但是配置了 2级缓存
        Dept dept2= (Dept) session.get(Dept.class,1);
    }

    /**
     * 验证2级缓存策略  只读
     */
    @Test
    public   void  test2(){
        Dept dept= (Dept) session.get(Dept.class,1); //获取指定的部门信息
        dept.setName("xx部");  //持久化状态
        transaction.commit();
    }


    /**
     *  设置2级缓存的模式
     *
     *  NORMAL  : 和2级缓存关联，可读可写
     *  IGNORE  : 不和2级缓存关联
     *  GET     : 和2级缓存关联，只读
     *  PUT     : 和2级缓存关联，只写
     *  REFRESH :和2级缓存关联，只写
     *  REFRESH可以设置强行和数据库同步
     *  在核心配置文件中配置下面的属性即可
     *  hibernate.cache.use_minimal_puts
     */
    @Test
    public   void  test3(){
        Dept dept= (Dept) session.get(Dept.class,1); //获取指定的部门信息  1条sql
        session.clear();
        //设置  缓存模式
        session.setCacheMode(CacheMode.PUT);  // 只写 不能读
        dept= (Dept) session.get(Dept.class,1); //不会产生
    }



}
