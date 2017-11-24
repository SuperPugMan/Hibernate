package com.xdf;

import com.xdf.bean.Teacher;
import com.xdf.util.SessionFactoryUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * 参数的绑定
 */
public class ParamterTest02 {


    Session session = null;
    Transaction transaction = null;

    @Before
    public void before() {
        session = SessionFactoryUtil.getCurrentSession();
        transaction = session.beginTransaction();
    }

    /**
     * 01.根据参数的位置进行绑定
     * 查询 id=2的老师信息
     */
      @Test
    public  void  test01(){
          String  hql="from  Teacher where id=?"; //Teacher 类名    id属性名
         Query query= session.createQuery(hql);
         //给参数赋值
          query.setParameter(0,2);  //给第一个参数赋值为2
          //得到唯一的结果
           Teacher teacher= (Teacher) query.uniqueResult();
          System.out.println(teacher);

   }
    /**
     * 02.根据参数的名称进行绑定
     * 查询 id=2的老师信息
     */
      @Test
    public  void  test02(){
          String  hql="from  Teacher where id=:tID"; //Teacher 类名    id属性名   :tID参数的名称
         Query query= session.createQuery(hql);
         //给参数赋值
          query.setParameter("tID",2);
          //得到唯一的结果
           Teacher teacher= (Teacher) query.uniqueResult();
          System.out.println(teacher);
   }

    /**
     * 动态参数的绑定
     *   程序运行期间才能判断用户需要查询的内容
     *
     *   查询教师表中 薪水是xx  地址是xx
     *   我们不知道用户具体的根据哪个字段进行查询
     *
     */
    @Test
    public  void  test03(){
        StringBuffer buffer=new StringBuffer("from  Teacher where 1=1 ");

        //创建一个Teacher对象 模拟用户给对象赋值
        Teacher teacher=new Teacher();
        teacher.setAddress("ssasa");
        teacher.setSal(2);

        //因为不清楚用户具体输入的参数 所以进行非空判断
         if (teacher.getSal()>0){
             buffer.append(" and sal>:sals");
         }
         if (teacher.getAddress()!=null){
             buffer.append(" and address like :addr ");
         }

         //创建query对象
       Query query= session.createQuery(buffer.toString());
         query.setParameter("sals",3000.0);
         query.setParameter("addr","%区%");
       List<Teacher> teachers=  query.list();
       for (Teacher t:teachers){
           System.out.println(t);
       }




    }
    /**
     * 动态参数的绑定
     *   程序运行期间才能判断用户需要查询的内容
     *
     *   直接传递一个对象
     *   注意点
     *   参数名称 必须和 实体类中的属性名一致
     *
     */
    @Test
    public  void  test04(){
        StringBuffer buffer=new StringBuffer("from  Teacher where 1=1 ");
        //创建一个Teacher对象 模拟用户给对象赋值
        Teacher teacher=new Teacher();
        teacher.setAddress("%区%");
        teacher.setSal(3000);

        //因为不清楚用户具体输入的参数 所以进行非空判断
         if (teacher.getSal()>0){
             buffer.append(" and sal>:sal");
         }
         if (teacher.getAddress()!=null){
             buffer.append(" and address like :address ");
         }

         //创建query对象
       Query query= session.createQuery(buffer.toString());
         //传递对象  给参数赋值
        query.setProperties(teacher);

       List<Teacher> teachers=  query.list();
       for (Teacher t:teachers){
           System.out.println(t);
       }
    }

    /**
     * 投影查询
     * select * from  teacher; 查询所有
     * select name,address from  teacher;  查询指定的字段    ===》投影查询
     *
     * 01.将每条查询结果封装成Object对象
     */
    @Test
    public  void  test05(){
        String hql="select  name from  Teacher";
       List<Object> list= session.createQuery(hql).list();
       for (Object  t:list){
           System.out.println(t);
       }
    }

    /**
     * 02.将每条查询结果封装成Object数组
     */
    @Test
    public  void  test06(){
        String hql="select  name,sal from  Teacher";
       List<Object[]> list= session.createQuery(hql).list();
       for (Object[]  t:list){
           System.out.println(t[0]);
           System.out.println(t[1]);
       }
    }
    /**
     * 03.将每条查询结果封装成对象
     *    前提 必须有对应的构造方法
     */
    @Test
    public  void  test07(){
        String hql="select  new Teacher(name,sal) from  Teacher";
       List<Teacher> list= session.createQuery(hql).list();
       for (Teacher  t:list){
           System.out.println(t);
       }
    }


    /**
     * 分页
     *
     * 01.查询总记录数
     * 02.每页显示的数据
     * 03.总页数
     */
    @Test
    public  void  test08(){
        //查询总记录数
        String  hql="select  count(*) from  Teacher";  // 会返回Long
        int counts= ((Long)session.createQuery(hql).uniqueResult()).intValue();
        //页大小
        int pageSize=2;
        //总页数
        int totalPages=(counts%pageSize==0)?(counts/pageSize):(counts/pageSize+1);
        // 显示第2页的内容
        int  pageIndex=3;
        hql="from Teacher";
        Query query= session.createQuery(hql);
        //设置从那一条数据开始查询
        query.setFirstResult((pageIndex-1)*pageSize);
        //设置页大小
        query.setMaxResults(pageSize);
        List<Teacher> teachers=query.list();
        for (Teacher t:teachers){
            System.out.println(t);
        }
    }


}
