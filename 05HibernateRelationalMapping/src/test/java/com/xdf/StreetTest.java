package com.xdf;

import com.sun.javafx.scene.control.skin.VirtualFlow;
import com.xdf.bean.District;
import com.xdf.bean.Street;
import com.xdf.util.SessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class StreetTest {

    Session  session=null;
    Transaction transaction=null;

    @Before
    public void before(){
        session= SessionFactoryUtil.getCurrentSession();
        transaction=session.beginTransaction();
    }

    /**
     * 测试单向的多对一关联
     *  创建出 模拟数据
     */
   @Test
    public  void  addDistrict(){
       District district=new District(2,"朝阳区");
       session.save(district);
       transaction.commit();
   }
    /**
     * 新增街道的同时 给 街道对应的区县赋值
     */
    @Test
    public  void  testAddStreet(){
        //创建一个街道
        Street street=new Street();
        street.setId(2);
        street.setName("海淀2街道");
        //先从数据库中获取一个区县对象
        District district= (District) session.get(District.class,1);
        street.setDistrict(district);
        //新增街道
        session.save(street);
        transaction.commit();
    }

    /**
     * 修改街道对应的区县
     */
    @Test
    public  void  testUpdateStreet(){
        //从数据库中获取一个需要修改的街道
        Street street= (Street) session.get(Street.class,2);
        District district= (District) session.get(District.class,2);
        //修改对应的区县
        street.setDistrict(district);
        transaction.commit();
    }
    /**
     * 修改街道对应的区县为null
     */
    @Test
    public  void  testDeleteStreet(){
        //从数据库中获取一个需要的街道
        Street street= (Street) session.get(Street.class,2);
        //修改对应的区县
        street.setDistrict(null);
        transaction.commit();
    }

    /**
     * 新增区县的同时 新增 对应的街道
     *  在一方中的hbm.xml文件中增加cascade=save-update
     * cascade: 只能设置在 一的一方
     *     1.none默认值：当我们操作对象时，忽略其关联属性
     *     2.save-update：在调用save,saveOrUpdate和update方法的时候，会级联的修改或保存当前对象
     *          以及所关联的对象！
     *     3.delete: 在调用delete的时候会级联的删除关联对象
     *     4.all： 包含了save-update和delete
     */
    @Test
    public   void  testAddDistirct(){
        //创建区县
        District district=new District(3,"大兴区");
        //创建街道
        Street street1=new Street(3,"大兴3街道");
        Street street2=new Street(4,"大兴4街道");
        //把街道放进区县的街道集合中
        district.getStreets().add(street1);
        district.getStreets().add(street2);
        //新增区县
        session.save(district);
        transaction.commit();
        /**
         *   产生了5条sql语句！  发现 后面两条update 是无用功！！！！
         Hibernate: insert into District (name, id) values (?, ?)
         Hibernate: insert into Street (name, districtId, id) values (?, ?, ?)
         Hibernate: insert into Street (name, districtId, id) values (?, ?, ?)
         Hibernate: update Street set districtId=? where id=?
         Hibernate: update Street set districtId=? where id=?

         3条insert是 District产生的！
         为什么会产生两条update？？ Street！


         hibernate中 规定：
           01.多的一方  many-to-one,必须去维护双方的关系！
            因为many-to-one压根就没有inverse这个属性！
           02.inverse默认为false!  不反转！ 我来维护！
           03.必须在一的一方  设置 inverse="true" 放弃维护的权力！

          维护===》是否与数据库产生交互！

         */
    }


    /**
     * 验证级联删除
     */
    @Test
    public  void  deleteDistrict(){
          District district= (District) session.get(District.class,3);
          session.delete(district); //删除区县
          transaction.commit();
    }
}
