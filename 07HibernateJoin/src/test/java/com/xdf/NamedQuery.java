package com.xdf;

import com.xdf.bean.Emp;
import com.xdf.util.SessionFactoryUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class NamedQuery {

    Session session=null;
    Transaction transaction=null;

    @Before
    public  void  before(){
        session= SessionFactoryUtil.getCurrentSession();
        transaction= session.beginTransaction();
    }

    /**
     * 命名查询：
     *     hibernate中 允许我们在xml映射文件以及实体类中（使用注解）定义查询语句！
     *
     * 01.Query             hql语句
     * 02.sql-Query         sql语句
     */

    @Test
    public  void  test(){
         List<Emp> list=session.getNamedQuery("getAllEmps").list();
         for (Emp  emp:list){
             System.out.println(emp);
         }
    }

    /**
     * 使用sql-query来命名查询:
     * 返回的是一个Object数组！
     */
    @Test
    public  void  test2(){
         List<Object[]> list=session.getNamedQuery("getAllEmpsBySql").list();
         for (Object[]  emp:list){
             System.out.println(emp[0]); //id
             System.out.println(emp[1]);//name
             System.out.println(emp[2]);//sal
         }
    }

    /**
     * 根据指定的id查询出指定对象
     */
    @Test
    public  void  test3(){
         List<Object[]> list=session.getNamedQuery("getEmpById").setParameter("id",1).list();
         for (Object[]  emp:list){
             System.out.println(emp[0]); //id
             System.out.println(emp[1]);//name
             System.out.println(emp[2]);//sal
         }
    }
    /**
     * 根据指定的id查询出指定的对象
     */
    @Test
    public  void  test4(){
        Query query =session.getNamedQuery("getEmpObject").setParameter("id",1);
        //把查询出来的结果集转换成指定的对象
        query.setResultTransformer(Transformers.aliasToBean(Emp.class));
        Emp emp=(Emp)  query.uniqueResult();
        System.out.println(emp);

    }
}
