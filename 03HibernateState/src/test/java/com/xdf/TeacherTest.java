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

        //08.关闭会话
        session.close();
    }


    /**
     *   Hibernate对象的三种状态
     *   1.瞬时态（临时态，自由态）
     *     我们通过new关键字创建出一个类的实例对象， 这个对象和hibernate没有任何关系！
     *   2.持久态
     *     对象被session管理。就会产生一个OID（主键标识符）！这个对象和hibernate有关系！
     *   3.游离态（托管态）
     *     曾经被session管理过！和瞬时态的区别在于，是否存在OID!
     *
     *
     * commit（）和flush()的区别
     *
     * flush()：是缓存清理，把缓存中的数据同步到数据库！
     * commit():在执行的时候，会默认执行flush（），
     *       flush()在执行的时候会进行缓存清理，在缓存清理的时候，会进行脏检查！
     *
     *  什么是脏检查？
     *       在一个对象被session管理的时候，会创建这个对象的快照，
     *       我们之后commit的时候，会拿当前的对象信息和之前对象的快照进行对比，
     *       如果当前对象的属性发生改变，那么现在的对象就是脏对象！
     *       脏对象会被同步到数据库中！
     *
     *
     *
     *
     */
    @Test
    public   void  addTeacher(){
         Teacher  teacher=new Teacher(4,"老师4"); //瞬时态
         session.save(teacher);  //持久态
         session.evict(teacher);  //游离态
         Teacher teacher1= (Teacher) session.get(Teacher.class,4);
         System.out.println(teacher1);
    }



    @Test
    public   void  addTeacher2(){
        Teacher  teacher=new Teacher(4,"老师4"); //瞬时态
        teacher.setName("老师5");//瞬时态
        session.save(teacher);  //持久态开始 才被session管理
        System.out.println("*****************************");
        transaction.commit();
    }

    @Test
    public   void  addTeacher3(){
        Teacher  teacher=new Teacher(4,"老师4"); //瞬时态
        session.save(teacher);  //持久态
        teacher.setName("老师5");
        System.out.println("*****************************");
        transaction.commit();
        /**
         *  01.产生insert
         *  02.产生update
         */
    }
    @Test
    public   void  addTeacher4(){
        Teacher  teacher=new Teacher(4,"老师4"); //瞬时态
        session.save(teacher);  //持久态
        teacher.setName("老师5");
        teacher.setName("老师6");
        teacher.setName("老师7");
        System.out.println("*****************************");
        transaction.commit();
        /**
         *  01.产生insert
         *  02.产生update
         */
    }


    /**
     * 118行从数据库中获取指定数据， teacher是持久化状态   产生一条sql语句
     * 119行改变了对象的属性，teacher变成了脏对象，就得同步到数据库，产生update
     */
    @Test
    public   void  addTeacher5(){
        Teacher  teacher= (Teacher) session.get(Teacher.class,1); //持久化状态
        teacher.setName("哈哈哈哈");
        transaction.commit();
    }





}
