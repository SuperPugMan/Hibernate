package com.xdf.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * SessionFactoryUtil的单例类！
 * 整个项目中只需要一个SessionFactory足够了！
 */
public class SessionFactoryUtil {

    private  static Configuration configuration;  //加载核心配置文件的类
    private  static SessionFactory factory;  //单例对象

    //在类加载的时候  指定静态代码块
    static{
        configuration=new Configuration().configure();  //默认加载项目根目录下的hibernate.cfg.xml
        factory=configuration.buildSessionFactory();
    }

    //通过sessionFactory 创建session
    public static Session  getCurrentSession(){
        return  factory.getCurrentSession();
        /**
         *  不能直接用  需要在核心配置文件中增加一个节点
         <property name="current_session_context_class">thread</property>
         */
    }

}
