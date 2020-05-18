package com.sam.pattern.template;

import com.sam.pattern.template.dao.MemberDao;

/**
 * 模板模式
 *      通常又叫模板方法模式 Template Method
 *      生产饮料：加原料、加水、烧水、加工、混合
 *
 *      JdbcTemplate：利用模板模式，自己写一个JdbcTemplate
 *
 */
public class TemplateTest {

    public static void main(String[] args) {

        MemberDao dao = new MemberDao(null);
        dao.query();


    }
}
