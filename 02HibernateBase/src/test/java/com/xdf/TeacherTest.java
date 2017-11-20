package com.xdf;

import com.xdf.bean.Teacher;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

public class TeacherTest {


    /**
     * 新增教师信息
     *
     * Hibernate中核心的一个类和五个接口
     *
     * 一个类：
     * Configuration ： 读取核心配置文件
     *
     * 五个接口:
     * 01.SessionFactory : 负责初始化Hibernate.cfg.xml文件中所有的配置信息
     *                      在程序中有一个就够啦！ 使用单例模式！
     * 02.Session  ： 是hibernate中用来创建事务以及对对象的增删改查操作！
     * 03.Transaction ：事务的管理
     * 04.Query  ：   sql ,hql
     * 05.Criteria  ：  完全面向对象！
     *
     */
    @Test
    public  void addTeacher(){
        //01.读取核心配置文件 configure()底层就是加载了/hibernate.cfg.xml
        Configuration configuration=new Configuration().configure();
        //02.创建会话工厂 sessionFactory
        SessionFactory factory= configuration.buildSessionFactory();
        //03.创建会话  session
        Session session=factory.openSession();
        //04.开启事务
        Transaction transaction = session.beginTransaction();
        //05.创建新增的对象
        Teacher teacher=new Teacher();
        teacher.setId(4);
        teacher.setName("小白");
        //06.持久化操作
        session.save(teacher);
        System.out.println("******************************");
        //07.提交事务
        transaction.commit();  //  产生sql语句
        //08.关闭会话
        session.close();
    }


}
