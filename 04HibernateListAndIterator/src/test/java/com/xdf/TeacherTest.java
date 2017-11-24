package com.xdf;

import com.xdf.bean.Teacher;
import com.xdf.util.SessionFactoryUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;

public class TeacherTest {

    Session session = null;
    Transaction transaction = null;

    @Before
    public void before() {
        session = SessionFactoryUtil.getCurrentSession();
        transaction = session.beginTransaction();
    }

    /**
     * 测试代码
     */
    @Test
    public void test01() {
        System.out.println(session.get(Teacher.class, 1));
    }

    /**
     * sql
     * hibernate    hql  hibernate查询语言
     * Query接口
     * <p>
     * 使用hql的步骤：
     * 01.获取session对象
     * 02.书写hql语句   完全面向对象的！  不存在表名和字段  只有类名和属性
     * select * from student;  student是表名
     * from  Student;        Student是类
     * 03.创建query对象
     * 04.query对象执行hql
     */
    @Test
    public void testHql() {
        String hql = "from  Teacher";  // 底层会转换成 select * from teacher;
        //创建query对象
        Query query = session.createQuery(hql);
        //执行查询语句
        System.out.println("******************");
        List<Teacher> list = query.list();  //会把查询的数据放进缓存中
        for (Teacher t : list) {
            System.out.println(t);
        }
        //清空缓存 session.clear();
        list = query.list();  //再次查询
        for (Teacher t : list) {
            System.out.println(t);
        }
    }

    @Test
    public void testHql2() {
        String hql = "from  Teacher";  // 底层会转换成 select * from teacher;
        //创建query对象
        Query query = session.createQuery(hql);
        //执行查询语句
        System.out.println("******************");
        List<Teacher> list = query.list();  //会把查询的数据放进缓存中
        for (Teacher t : list) {
            System.out.println(t);
        }
        System.out.println(session.get(Teacher.class,1));  // 证明list获取的数据确实放进缓存中

    }

    /**
     * list和iterator的区别
     *
     * 相同点:
     *     01.都是从数据库中获取数据的集合!
     *     02.都会把数据放进缓存中
     *
     * list:
     *    01.无论缓存中是否有数据，都会从数据库中查询（不走缓存）
     *    02.执行list一次，生成一次select语句
     *
     *
     *  iterator：
     *     01.如果数据库中有N条数据，那么会产生N+1条select语句
     *     02.第一个select语句是从数据库中查询所有的数据的id
     *       id要作为OID  缓存的依据
     *     03.无论如何都需要执行一条sql语句就是查询所有的id
     *        query.iterate()
     *     04.iterator.next()产生的sql就是数据库中数据的数量
     *
     *
     * iterator能够利用懒加载和缓存的机制来提高查询效率！
     * 第一条语句就是从数据库中获取所有的id，保存在缓存中，
     * 便于下次查询！ 之有缓存中不存在数据时，才回去查询数据库！
     * iterator使用于开启二级缓存的情况！
     */
    @Test
    public  void  testIterator(){
        String  hql="from  Teacher";  //书写hql语句
        Query query=  session.createQuery(hql);  //创建query对象
        Iterator<Teacher> iterator= query.iterate();  //通过query对象执行iterate查询
        System.out.println("*******************************");
        while (iterator.hasNext()){  //遍历结果集
            System.out.println("===================================");
         Teacher teacher=   iterator.next();
            System.out.println(teacher);
        }
        //清理缓存
        iterator= query.iterate();  //通过query对象执行iterate查询
        while (iterator.hasNext()){  //遍历结果集
            Teacher teacher=   iterator.next();
            System.out.println(teacher);
        }

    }


    /**
     * 验证
     * 如果缓存中没有数据 iterator回去查询数据库
     */
    @Test
    public  void  testIterator2(){
        //先从数据库中获取两条数据
    Teacher  teacher1= (Teacher) session.get(Teacher.class,1); //1
    Teacher  teacher2= (Teacher) session.get(Teacher.class,2); //1
    //现在为止  缓存中有两条数据  oid  分别是 1  2
       Query query= session.createQuery("from  Teacher");
       Iterator<Teacher> it=  query.iterate(); //1
       while (it.hasNext()){
         Teacher teacher=  it.next();
           System.out.println(teacher); //3
       }

    }

}