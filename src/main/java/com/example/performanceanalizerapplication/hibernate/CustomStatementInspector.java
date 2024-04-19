package com.example.performanceanalizerapplication.hibernate;

import org.hibernate.resource.jdbc.spi.StatementInspector;

import static com.example.performanceanalizerapplication.hibernate.SqlParserUtils.parseSql;

public class CustomStatementInspector implements StatementInspector {

    @Override
    public String inspect(String sql) {
        String s = parseSql(sql);
        System.out.println(s);
        return s;
    }
}
