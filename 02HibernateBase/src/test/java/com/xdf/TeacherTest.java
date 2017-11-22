package com.xdf;

import com.xdf.bean.Teacher;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TeacherTest {


    Transaction transaction=null;
    Session session=null;
    @Before
    public  void before(){
        //01.读取核心配置文件 configure()底层就是加载了/hibernate.cfg.xml
        Configuration configuration=new Configuration().configure();
        //02.创建会话工厂 sessionFactory
        SessionFactory factory= configuration.buildSessionFactory();
        //03.创建会话  session
         session=factory.openSession();
        //04.开启事务
        transaction = session.beginTransaction();
    }


    @After
    public  void after(){
        //07.提交事务
        transaction.commit();  //  assigned 产生sql语句
        //08.关闭会话
        session.close();
    }

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
     */
    @Test
    public  void addTeacher(){

        //05.创建新增的对象
        Teacher teacher=new Teacher();
        teacher.setId(2);
        teacher.setName("小白2");
        //06.持久化操作
        System.out.println("******************************");
        session.save(teacher);   //identity产生sql语句
        System.out.println("******************************");
       // factory.close();
    }


    /**
     *  数据库有对应的数据产生2条sql
     *
     *  01.先根据对象的id 去数据库中查询 看有没有数据
     *  02.如果存在根据id删除指定的信息
     *     如果不存在 只做查询操作
     */
    @Test
    public void  deleteTeacher(){
          //创建需要删除的对象
        Teacher teacher=new Teacher();
        teacher.setId(2);
        System.out.println("***********************");
        //删除
        session.delete(teacher);
        System.out.println("***********************");
    }


    /**
     * 修改
     *  只生成一条update语句，不会进行查询操作
     */
    @Test
    public void  updateTeacher(){
        //创建需要修改的对象
        Teacher teacher=new Teacher();
        teacher.setId(1);
        teacher.setName("小黑111");
        System.out.println("***********************");
        //修改
        session.update(teacher);
        System.out.println("***********************");
    }

    /**
     *
     * 从数据库中查询指定id的信息
     *   get和load的区别
     *
     *   get：
     *     01.在get()立即产生sql语句
     *     02.首先会去hibernate的1级缓存（session）中查询有没有对应的数据，
     *        如果用，直接返回,不去访问数据库，
     *        如果没有，而且我们配置了2级缓存，那么会去2级缓存中查询，
     *        如果2级缓存没有数据，就去访问数据库。
     *     03.如果数据库中有对应的数据 直接返回
     *        数据库中没有对应的数据，则返回null
     */

    @Test
    public void  getTeacher(){
        System.out.println("*************************");
        Teacher teacher= (Teacher) session.get(Teacher.class,3);
        System.out.println("*************************");
        System.out.println(teacher);
    }

    /**
     * 验证get会把查询到的数据放进session缓存中
     */
    @Test
    public void  getTeacher2(){
        System.out.println("*************************");
        Teacher teacher1= (Teacher) session.get(Teacher.class,3);
        Teacher teacher2= (Teacher) session.get(Teacher.class,3);
        System.out.println("*************************");
    }

    /**
     * evict  从session中清除指定的对象
     */
    @Test
    public void  evictTeacher(){
        Teacher teacher1= (Teacher) session.get(Teacher.class,1);
        Teacher teacher2= (Teacher) session.get(Teacher.class,2);
        //清除teacher1
        session.evict(teacher1);
        teacher1= (Teacher) session.get(Teacher.class,1); //再次查询
    }
    /**
     * clear  从session中清空所有对象
     */
    @Test
    public void  clearTeacher(){
        Teacher teacher1= (Teacher) session.get(Teacher.class,1);
        Teacher teacher2= (Teacher) session.get(Teacher.class,2);
        //清空所有对象
         session.clear();
        teacher1= (Teacher) session.get(Teacher.class,1); //再次查询
        teacher2= (Teacher) session.get(Teacher.class,2); //再次查询
    }

    /**
     * load： 懒加载
     *   01.不会在load的时候 产生sql语句，
     *      用户需要的时候产生
     *  02.首先会去hibernate的1级缓存（session）中查询有没有对应的数据，
     *        如果用，直接返回,不去访问数据库，
     *        如果没有，而且我们配置了2级缓存，那么会去2级缓存中查询，
     *        如果2级缓存没有数据，就去访问数据库。
     *  03.如果数据库中有对应的数据 直接返回
     *        数据库中没有对应的数据，则抛出ObjectNotFoundException
     */
    @Test
    public void  loadTeacher(){
        Teacher teacher= (Teacher) session.load(Teacher.class,4);
        System.out.println("***********************************");
        System.out.println(teacher);
    }


    /**
     * load也可以使用立即加载
     *  去对应类所在的hbm.xml文件中的class节点上 增加  lazy="false"  立即加载
     */
    @Test
    public void  loadTeacher2(){
        Teacher teacher= (Teacher) session.load(Teacher.class,1);
        System.out.println("***********************************");
        System.out.println(teacher);
    }



}
