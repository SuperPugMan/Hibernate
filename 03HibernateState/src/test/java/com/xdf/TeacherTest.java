package com.xdf;

import com.xdf.bean.Teacher;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.sound.midi.Soundbank;

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
        /**
         * 因为teacher已经是持久态  所以不需要save或者update
         */
        transaction.commit();
    }


    /**
     * saveOrUpdate（）
     * 验证一：
     *    数据在数据库不存在，
     *    saveOrUpdate（） 01.先产生select
     *                     02.insert
     */
    @Test
    public  void test06(){
        Teacher  teacher=new Teacher(4,"老师4"); //瞬时态
        session.saveOrUpdate(teacher); //持久态
        System.out.println("=============================================");
        transaction.commit();
  }

    /**
     * saveOrUpdate（）
     * 验证二：
     *    数据在数据库存在，
     *    saveOrUpdate（） 01.先产生select语句根据id查询
     *                   如果修改了对象的属性
     *                   02.产生update语句
     */
    @Test
    public  void test07(){
        Teacher  teacher=new Teacher(4,"老师哈哈哈"); //瞬时态
        session.saveOrUpdate(teacher); //持久态
        System.out.println("=============================================");
        transaction.commit();
    }


    /**
     *   saveOrUpdate（）：
     *     数据库中没有对应的数据，
     *     然后更改了数据的属性！
     *     01.select
     *     02.insert
     *     03.update
     */
    @Test
    public  void test08(){
        Teacher  teacher=new Teacher(4,"老师4"); //瞬时态
        session.saveOrUpdate(teacher); //持久态
        teacher.setName("老师5");
        System.out.println("=============================================");
        transaction.commit();
    }
    /**
     *   merge
     *   验证一：
     *   数据库没有对应的数据
     *     01.select语句
     *     02.insert
     */
    @Test
    public  void test09(){
        Teacher  teacher=new Teacher(4,"老师4"); //瞬时态
        session.merge(teacher);
        transaction.commit();
    }

    /**
     *   merge
     *   验证二：
     *   数据库有对应的数据
     *       只有一条select语句
     */
    @Test
    public  void test10(){
        Teacher  teacher=new Teacher(4,"老师4"); //瞬时态
        session.merge(teacher);
        transaction.commit();
    }

    /**
     *   merge
     *   验证三：
     *   数据库有对应的数据，但是对数据进行修改
     *       只有一条select语句
     *       01.select
     *       02.update
     */
    @Test
    public  void test11(){
        Teacher  teacher=new Teacher(4,"老师5"); //瞬时态
        session.merge(teacher);
        transaction.commit();
    }
    /**
     *   merge
     *   验证四：
     *   数据库没有对应的数据，创建对象之后 对数据进行修改
     *       01.select
     *       02.insert
     */
    @Test
    public  void test12(){
        Teacher  teacher=new Teacher(4,"老师4"); //瞬时态
        session.merge(teacher);
        teacher.setName("老师5");
        transaction.commit();
    }
    /**
     *   merge
     *   验证五：
     *     不会改变对象的状态
     */
    @Test
    public  void test13(){
        Teacher  teacher=new Teacher(4,"老师4"); //瞬时态
        session.merge(teacher);  //不会改变对象的状态
        teacher.setName("老师5");  //瞬时态
        session.update(teacher); //报错
        teacher.setName("老");
        transaction.commit();
    }

    /**
     *
     * 瞬时态和游离态的区别就是是否拥有OID！
     * OID怎么来的？只要曾经被session管理过的对象都有OID！
     *
     *  save():   把瞬时态转换成持久态
     *  update(): 把游离态转换成持久态
     *  saveOrUpdate():
     *         会根据对象是否有OID来判断执行save还是update
     *           如果有oid  执行update
     *           如果没有oid  执行save
     *  merge()： 产生的sql语句和saveOrUpdate有点类似，
     *            但是！！！！！
     *            01.merge不会改变对象的状态
     *            02.当我们的对象处于瞬时状态时，会将对象复制一份到session的缓存中，
     *              然后执行save方法，执行insert
     *
     *
     *
     *
     *
     *
     *
     */







}
