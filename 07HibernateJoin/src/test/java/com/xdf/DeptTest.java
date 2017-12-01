package com.xdf;

import com.xdf.bean.Dept;
import com.xdf.bean.Emp;
import com.xdf.util.SessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class DeptTest {
    Session session=null;
    Transaction transaction=null;

    @Before
    public  void  before(){
        session= SessionFactoryUtil.getCurrentSession();
        transaction= session.beginTransaction();
    }



    @Test
    public   void addTest(){
        /**
         * 新增部门的同时 新增员工
         * 因为我们设置了cascade=all
         */
        Dept dept=new Dept();
        dept.setDeptId(1);
        dept.setName("财务部");
        dept.setLocation("1楼");
        //创建部门对应的员工
        Emp emp1=new Emp(1,"员工1","财务猿1",10000.0,dept);
        Emp emp2=new Emp(2,"员工2","财务猿2",5000.0,dept);
        Emp emp3=new Emp(3,"员工3","财务猿3",6000.0,dept);
        //将员工放进集合中
        dept.getEmps().add(emp1);
        dept.getEmps().add(emp2);
        dept.getEmps().add(emp3);
        session.save(dept);
        transaction.commit(); //提交事务

    }
    @Test
    public   void addTest2(){
        /**
         * 新增部门的同时 新增员工
         * 因为我们设置了cascade=all
         */
        Dept dept=new Dept();
        dept.setDeptId(2);
        dept.setName("研发部");
        dept.setLocation("2楼");
        //创建部门对应的员工
        Emp emp1=new Emp(4,"员工4","程序猿1",100000.0,dept);
        Emp emp2=new Emp(5,"员工5","程序猿2",50000.0,dept);
        Emp emp3=new Emp(6,"员工6","程序猿3",60000.0,dept);
        //将员工放进集合中
        dept.getEmps().add(emp1);
        dept.getEmps().add(emp2);
        dept.getEmps().add(emp3);
        session.save(dept);
        transaction.commit(); //提交事务

    }

    /**
     * 使用普通内连接查询  部门和员工的信息
     *
     连接查询
     1.内连接 ：查询两张表中的，相同的数据
     01.隐式内连接
     select  studentName，gradeName from  student,grade
     where  student.gradeId=grade.gradeId
     02.显式内连接
     select  studentName，gradeName from  student
     inner join grade  on  student.gradeId=grade.gradeId
     */
    @Test
    public  void  innerJoin1(){
        String hql="from Emp e inner join e.dept";  //e.dept就是Emp类中的域属性
       List<Object[]> list= session.createQuery(hql).list();
       for (Object[]  o:list){
           System.out.println(o[0]);  //Emp对象
           System.out.println(o[1]);  //Dept对象
       }
    }

    /**
     * 迫切内连接
     */
    @Test
    public  void  innerJoinFetch(){
        String hql="from Emp e inner join fetch e.dept";  //e.dept就是Emp类中的域属性
       List<Emp> list= session.createQuery(hql).list();
       for (Emp  o:list){
           System.out.println(o);  //Emp对象
       }
    }


    /**
     * 使用左外连接
     */
    @Test
    public  void  leftJoin1(){
        String hql="from Emp e left join  e.dept";  //e.dept就是Emp类中的域属性
       List<Object[]> list= session.createQuery(hql).list();
       for (Object[]  o:list){
           System.out.println(o[0]);//Emp对象
           System.out.println(o[1]);//Dept对象
       }
    }
    /**
     * 使用迫切左外连接
     */
    @Test
    public  void  leftJoinFetch(){
        String hql="from Emp e left join fetch e.dept";  //e.dept就是Emp类中的域属性
       List<Emp> list= session.createQuery(hql).list();
       for (Emp  o:list){
           System.out.println(o);//Emp对象
       }
    }


    /**
     * 所有的迫切连接返回的都是一个对象！
     * 非迫切连接返回的是一个Object[]！
     *
     *
     *
     * 为什么 没有 迫切右外连接？
     *
     * 01.from Emp e left join fetch   e.dept  返回的是一个Emp对象
     *    Emp对象 绝对不可能为空！
     *
     * 02.from Emp e right join fetch  e.dept  以右表为准
     *     有可能部门下面没有员工，返回null!
     *
     * 03.form  Dept d  left join fetch  ???? d.emps????
     *   emps??集合？？
     */















}
