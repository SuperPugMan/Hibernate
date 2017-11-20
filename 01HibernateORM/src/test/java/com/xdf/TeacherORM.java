package com.xdf;

import com.xdf.bean.Teacher;

import java.sql.*;

public class TeacherORM {

    public static void main(String[] args) {
        /**
         * 定义数据库连接的四要素
         */
        String driver="com.mysql.jdbc.Driver";
        String url="jdbc:mysql://localhost:3306/t17";
        String userName="root";
        String password="";

        /**
         * 创建JDBC需要的API
         */
        Connection connection=null;
        PreparedStatement  ps=null;
        ResultSet  rs=null;

        //创建需要的实体类对象 用于动态的赋值
        Object  object=null;
        try {
         object= Class.forName("com.xdf.bean.Teacher").newInstance();
        } catch (Exception e) {
           e.printStackTrace();
        }

        try {
            //01.加载驱动
            Class.forName(driver);
            connection= DriverManager.getConnection(url,userName,password);
            //02.书写sql语句
            String sql="select id,name from  teacher where id=?";
            ps=connection.prepareStatement(sql);
            //03.给参数赋值
            ps.setInt(1,2);
            rs= ps.executeQuery();
            //04.遍历结果集
            while (rs.next()){
                ResultSetMetaData data = rs.getMetaData();
                int count=  data.getColumnCount();  // 获取结果集中的列数
                for (int  i=1;i<=count;i++){
                  String  name=   data.getColumnName(i);  //获取字段名称
                  String method= changeName(name); //根据字段名称  获取执行的方法
                   String  type=data.getColumnTypeName(i);  //获取数据库中字段的类型
                  if (type.equalsIgnoreCase("int")){
                        object.getClass().getMethod(method,Integer.class).invoke(object,rs.getInt(name));
                  }else  if (type.equalsIgnoreCase("varchar")){
                        object.getClass().getMethod(method,String.class).invoke(object,rs.getString(name));
                  }
                }

                System.out.print((Teacher)object);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            //05.释放资源
            try {
                rs.close();
                ps.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    }

    /**
     *  给我们传递一个name   id
     *  我们返回setName   setId
     */
    private static String changeName(String name) {
        return  "set"+name.substring(0,1).toUpperCase()+name.substring(1);
    }
}
