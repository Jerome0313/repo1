package com.example.demo.access;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Access {
    /**
     * Access数据库Connection
     */
    private Connection connection;

    static {
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");//加载ucanaccess驱动
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Connection getAccessConnection(String path, String user, String pwd) {
        try {
            //获取Access数据库连接(Connection)
            this.connection = DriverManager.getConnection("jdbc:ucanaccess://" + path, user, pwd);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return this.connection;
    }

    public static void main(String[] args) throws Exception {
        Access access=new Access();
        while (true) {
            Connection connection = access.getAccessConnection("D:\\AccessFiles\\student.mdb", "", "");
            access.select(connection);
        }
    }

    /**
     * Access插入(使用了预编译)
     *
     * @param connection 连接
     * @return 受影响的行数
     * @throws Exception 异常
     * @date: 2019年1月24日 12:47:12
     */
    public int insert(Connection connection) throws Exception {
        // ? 是 JDBC 预编译的占位符
        PreparedStatement statement=connection.prepareStatement("insert into student(name,address,age) values(?,?,?)");
//        statement.setInt(0, 1);//学生编号
        statement.setString(1, "赵六");//学生姓名
        statement.setString(2, "湖南省、衡阳市、珠晖区1");//学生住址
        statement.setInt(3, 20);//学生年龄
        int result = statement.executeUpdate();
        statement.close();
        connection.close();
        return result;
    }

    /**
     * Access删除
     *
     * @param connection 连接
     * @return 受影响的行数
     * @throws Exception 异常
     * @date: 2019年1月24日 12:47:12
     */
    public int delete(Connection connection) throws Exception {
        Statement statement = connection.createStatement();
        int result = statement.executeUpdate("delete from student where id=3");
        statement.close();
        connection.close();
        return result;
    }

    /**
     * Access更新
     *
     * @param connection 连接
     * @return 受影响的行数
     * @throws Exception 异常
     * @date: 2019年1月24日 12:47:12
     */
    public int update(Connection connection) throws Exception {
        Statement statement = connection.createStatement();
        int result = statement.executeUpdate("update student set address='湖南省、衡阳市、珠晖区' where id=1");
        statement.close();
        connection.close();
        return result;
    }

    /**
     * Access查询
     *
     * @param connection 连接
     * @throws Exception 异常
     * @date: 2019年1月24日 12:47:12
     */
    public void select(Connection connection) throws Exception {
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("select * from student");
        while (result.next()) {
            System.out.print(result.getString("id") + "\t");
            System.out.print(result.getString("name") + "\t");
            System.out.print(result.getString("address") + "\t");
            System.out.print(result.getString("age") + "\t");
            System.out.print(result.getString("birthday") + "\t");
            System.out.println();
        }
        statement.close();
        connection.close();
    }
}
