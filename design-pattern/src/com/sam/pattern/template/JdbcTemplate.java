package com.sam.pattern.template;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class JdbcTemplate {

    private DataSource dataSource;

    public JdbcTemplate(DataSource dataSource){
        this.dataSource = dataSource;
    }

    private Connection getConnection() throws Exception{
        return this.dataSource.getConnection();
    }

    private PreparedStatement createPreparedStatement(Connection conn, String sql) throws Exception{
        return conn.prepareStatement(sql);
    }

    private ResultSet executeQuery(PreparedStatement pstmt, Object[] values) throws Exception{

        for (int i = 0; i< values.length; i++){
            pstmt.setObject(i, values[i]);
        }
        return pstmt.executeQuery();
    }

    private void closeStatement(Statement stmt) throws Exception{
        stmt.close();
    }

    private void closeResult(ResultSet rs) throws Exception{
        rs.close();
    }

    private void closeConnection(Connection conn) throws Exception{
        //通常把它放到连接池中回收
    }

    public List<?> executeQuery(String sql, Object[] values){

        try{
            //1.获取连接
            Connection conn = this.getConnection();
            //2.创建语句集
            PreparedStatement pstmt = this.createPreparedStatement(conn, sql);

            //3.执行语句集，并且获得结果集
            ResultSet rs = this.executeQuery(pstmt, values);
            //4.解析语句集
            List<Object> result = new ArrayList<>();
            while(rs.next()){
                Object obj = processResult(rs);
                result.add(obj);
            }

            //5.关闭结果集,其实close应该在finally块里面
            this.closeResult(rs);
            //6.关闭语句集
            this.closeStatement(pstmt);
            //7.关闭连接
            this.closeConnection(conn);
            return result;
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    public abstract Object processResult(ResultSet rs) throws Exception;


}
