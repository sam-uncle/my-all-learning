package com.sam.pattern.template.dao;

import com.sam.pattern.template.JdbcTemplate;
import com.sam.pattern.template.entity.Member;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.util.List;

public class MemberDao extends JdbcTemplate {

    public MemberDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public Object processResult(ResultSet rs) throws Exception {

        Member member = new Member();
        member.setUserName(rs.getString("userName"));
        member.setPassword(rs.getString("password"));
        member.setAge(rs.getInt("age"));
        member.setAddr(rs.getString("addr"));
        return member;
    }

    public List<?> query(){
        String sql = "select * from t_member";
        return super.executeQuery(sql, null);
    }
}
