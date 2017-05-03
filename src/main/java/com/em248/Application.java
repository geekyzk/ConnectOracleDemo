package com.em248;

import java.sql.*;

/**
 * Created by tian on 2017/5/3.
 */
public class Application {

    public static void main(String[] args) throws SQLException {
        OracleJdbcTest test = new OracleJdbcTest();
        try{
            test.query("drop table testjdbc");
        }catch(SQLException e){}
        test.query("create table testjdbc(id int,name nchar(20))");
        test.query("insert into testjdbc values(1,'anxpp')");
        test.query("insert into testjdbc values(2,'test')");
        test.query("SELECT * FROM testjdbc",true);
        test.close();
    }
}
class OracleJdbcTest{
    //数据库连接对象
    private static Connection conn = null;
    private static String driver = "oracle.jdbc.driver.OracleDriver";       //驱动
    private static String url = "jdbc:oracle:thin:@//127.0.0.1:1521/orcl";  //连接字符串
    private static String username = "system";      // 用户名
    private static String password = "oracle";   // 密码
    //获取连接对象
    private static synchronized Connection getConn(){
        if(conn==null){
            try {
                Class.forName(driver);
                conn = DriverManager.getConnection(url, username, password);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return conn;
    }
    //执行查询语句
    public void query(String sql,boolean isSelect) throws SQLException {
        PreparedStatement pstmt;
        try {
            pstmt = getConn().prepareStatement(sql);
            // 建立一个结果集,用来保存查询出来的结果
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                System.out.println(name + "?");
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void query(String sql) throws SQLException {
        PreparedStatement pstmt;
        pstmt = getConn().prepareStatement(sql);
        pstmt.execute();
        pstmt.close();
    }
    //关闭连接
    public void close(){
        try {
            getConn().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
