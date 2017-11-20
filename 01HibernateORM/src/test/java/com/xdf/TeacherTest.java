package com.xdf;

import com.xdf.bean.Teacher;

import java.sql.*;

public class TeacherTest {

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
               int id=  rs.getInt("id");
               String name=  rs.getString("name");
                Teacher  t=new Teacher(id,name);
                System.out.println(t);
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
}
