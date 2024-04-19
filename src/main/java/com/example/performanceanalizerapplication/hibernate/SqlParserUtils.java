package com.example.performanceanalizerapplication.hibernate;

import com.example.performanceanalizerapplication.utils.DatetimeUtils;
import org.apache.calcite.config.Lex;
import org.apache.calcite.sql.*;
import org.apache.calcite.sql.dialect.MysqlSqlDialect;
import org.apache.calcite.sql.parser.SqlParseException;
import org.apache.calcite.sql.parser.SqlParser;

public class SqlParserUtils {
  public static String parseSql(String sql) {
      SqlParser.Config config = SqlParser.config().withLex(Lex.MYSQL);
        SqlParser sqlParser = SqlParser.create(sql, config);
      try {
          SqlNode sqlNode = sqlParser.parseStmt();
          if (sqlNode.getKind() == SqlKind.SELECT) {
              SqlSelect sqlSelect = (SqlSelect) sqlNode;
              if (sqlSelect.getFrom().getKind() == SqlKind.AS) {
                  SqlBasicCall basicCall = (SqlBasicCall) sqlSelect.getFrom();
                  SqlIdentifier tableName = (SqlIdentifier) basicCall.getOperands()[0];
                  String s = tableName.names.get(0);
                  String newTableName = String.format("%s_%s",s, DatetimeUtils.currentDay.get());
                  SqlIdentifier newTable = tableName.setName(0, newTableName);
                  basicCall.setOperand(0, newTable);
              }
          }
          return sqlNode.toSqlString(MysqlSqlDialect.DEFAULT).getSql();
      } catch (SqlParseException e) {
          throw new RuntimeException(e);
      }
  }

}
