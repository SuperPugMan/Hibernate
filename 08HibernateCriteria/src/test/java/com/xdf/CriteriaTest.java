package com.xdf;

import com.xdf.bean.Dept;
import com.xdf.bean.Emp;
import com.xdf.util.SessionFactoryUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class CriteriaTest {

    Session session=null;
    Transaction transaction=null;

    @Before
    public  void  before(){
        session= SessionFactoryUtil.getCurrentSession();
        transaction= session.beginTransaction();
    }


    /**
     *  Criteria查询： 完全面向对象的思想来 解决数据库数据的操作问题！
     *  没有SQL也没有HQL!
     */
    @Test
    public   void  test1(){  //查询所有的部门信息
      Criteria criteria= session.createCriteria(Dept.class); // from  Dept;  select * from dept;
      List<Dept> depts= criteria.list();
      for (Dept dept:depts){
          System.out.println(dept);
      }
    }

    /**
     * 查询 研发部的 信息
     *
     * criteria.add(Criterion类型)
     * Criterion 是一个接口  , 规范
     * Restrictions 是一个类， 约束，给我们的查询增加各种条件
     * Restrictions所有的方法返回值都是Criterion或者是其实现类，方法的修饰符都是static
     */
  @Test
    public void  test2(){
      Criteria criteria= session.createCriteria(Dept.class);
      //增加条件
      criteria.add(Restrictions.eq("name","研发部"));
      Dept dept= (Dept) criteria.uniqueResult();
      System.out.println(dept);
  }

    /**
     * 查询薪水大于10k的员工信息
     */
    @Test
    public void  test3(){
        Criteria criteria= session.createCriteria(Emp.class);
        //增加条件
        criteria.add(Restrictions.gt("sal",10000d));
        List<Emp> emps=criteria.list();
        for (Emp  emp:emps){
            System.out.println(emp);
        }
    }

    /**
     * 查询薪水在5K和10k之间的员工信息
     */
    @Test
    public void  test4(){
        Criteria criteria= session.createCriteria(Emp.class);
        //增加条件
        criteria.add(Restrictions.between("sal",5000d,10000d));
        List<Emp> emps=criteria.list();
        for (Emp  emp:emps){
            System.out.println(emp);
        }
    }


    /**
     * 查询没有部门的 员工信息
     */
    @Test
    public void  test5(){
        Criteria criteria= session.createCriteria(Emp.class);
        //增加条件
        criteria.add(Restrictions.isNull("dept"));
        List<Emp> emps=criteria.list();
        for (Emp  emp:emps){
            System.out.println(emp);
        }
    }


    /**
     * 查询没有员工的  部门信息
     */
    @Test
    public void  test6(){
        Criteria criteria= session.createCriteria(Dept.class);
        //增加条件
        criteria.add(Restrictions.isEmpty("emps"));
        List<Dept> depts=criteria.list();
        for (Dept  dept:depts){
            System.out.println(dept);
        }
    }

    /**
     * 查询职务是 程序猿1或者是财务猿1的  员工信息  使用or
     */
    @Test
    public void  test7(){
        Criteria criteria= session.createCriteria(Emp.class);
        //增加条件
        criteria.add(Restrictions.or(
                Restrictions.eq("job","程序猿1"),
                Restrictions.eq("job","财务猿1")
        ));
        List<Emp> emps=criteria.list();
        for (Emp  emp:emps){
            System.out.println(emp);
        }
    }
    /**
     * 查询职务是 程序猿1或者是财务猿1的  员工信息  使用 in
     */
    @Test
    public void  test8(){
        Criteria criteria= session.createCriteria(Emp.class);
        List<String>  jobs=new ArrayList<String>();
        jobs.add("程序猿1");
        jobs.add("财务猿1");

        //增加条件
        criteria.add(Restrictions.in("job",jobs));
        List<Emp> emps=criteria.list();
        for (Emp  emp:emps){
            System.out.println(emp);
        }
    }

    /**
     * 查询职务是 程序猿1或者是财务猿1的  员工信息  使用  disJunction
     *
     *
     * Restrictions.disjunction  返回值是一个 DisJunction 类
     * DisJunction 类 extends  Junction 类
     *  Junction 类有一个方法叫add（）===》criteria.add（）
     *
     *  public Junction add(Criterion criterion) {
         criteria.add(criterion);
         return this;
         }
     */
    @Test
    public void  test9(){
       Criteria criteria=  session.createCriteria(Emp.class);
       //增加条件
        criteria.add(Restrictions.disjunction()
                .add(Restrictions.eq("job","程序猿1"))
                .add(Restrictions.eq("job","财务猿1")));
        List<Emp> emps=criteria.list();
        for (Emp  emp:emps){
            System.out.println(emp);
        }
    }


    /**
     *  like 和 ilike的区别
     *
     *  like  模糊查询
     *  ilike 模糊并且忽略大小写查询
     *
     *  MatchMode:  我们的value值出现的位置
     *  anywhere: 前后
     *  start: 前
     *  end: 后
     */
    @Test
    public void  test10(){  //查询员工姓名中包含A
        Criteria criteria=  session.createCriteria(Emp.class);
        criteria.add(Restrictions.like("name","b", MatchMode.END));
        //增加条件
        List<Emp> emps=criteria.list();
        for (Emp  emp:emps){
            System.out.println(emp);
        }
    }


    /**
     *   聚合函数
     *   setProjection 需要我们传递一个Projection
     *   Projections类中的所有方法返回值都是Projection或者其实现类
     *   如果设值之后，没有清空，那么之前的参数会被带入下次的查询！
     */
    @Test
    public void  test11(){  //查询员工最高，最低，平均和总薪水
        Criteria criteria=  session.createCriteria(Emp.class);
        criteria.setProjection(Projections.projectionList()
        .add(Projections.max("sal"))
        .add(Projections.min("sal"))
        .add(Projections.avg("sal"))
        .add(Projections.sum("sal"))
        );
     List<Object[]> list=  criteria.list();
     for (Object[] o:list){
         System.out.println("最高薪水："+o[0]);
         System.out.println("最低薪水："+o[1]);
         System.out.println("平均薪水："+o[2]);
         System.out.println("总薪水："+o[3]);
     }
    }

    /**
     *  查询姓名中包含 b的员工  并且按照薪水降序排列
     */

    @Test
    public void  test12() {
        int count = ((Long) session.createCriteria(Emp.class).add(Restrictions.ilike("name", "b", MatchMode.ANYWHERE))
                .setProjection(Projections.count("name")).uniqueResult()).intValue();
        System.out.println(count);
        //设置当前页和页大小
        int pageIndex = 2;
        int pageSize = 2;
        //计算总页数
        int totalPage = (count % pageSize == 0) ? (count / pageSize) : (count / pageSize + 1);
        //根据薪水进行降序排列
        Criteria criteria = session.createCriteria(Emp.class).add(Restrictions.ilike("name", "b", MatchMode.ANYWHERE))
                .addOrder(Order.desc("sal"));
        //设置 起始页和页大小
        List<Emp> emps = criteria.setFirstResult((pageIndex - 1) * pageSize).setMaxResults(pageSize).list();
        for (Emp emp : emps) {
            System.out.println(emp);
        }
    }

        /**
         * DetachedCriteria和 Criteria的区别
         * 相同点：都能用来 做查询操作
         * 不同点：
         *    01.DetachedCriteria在创建的时候 不需要session！
         *    02.真正执行查询的时候getExecutableCriteria(session)才使用session
         *    03.DetachedCriteria自身可以作为一个参数
         *
         *  薪水  大于    平均值的员工信息
         */
        @Test
        public  void   test13(){
            //得到DetachedCriteria对象
            DetachedCriteria criteria= DetachedCriteria.forClass(Emp.class)
                    .setProjection(Projections.avg("sal"));
            //执行查询
            double  avg=(Double) criteria.getExecutableCriteria(session).uniqueResult();
            System.out.println("薪水的平均值是："+avg);

            //薪水 大于    平均值的员工信息
            List<Emp> list = session.createCriteria(Emp.class).add(
                    Property.forName("sal").gt(criteria)).list();
            for (Emp emp : list) {
                System.out.println(emp);
            }
        }

    }







