package com.example.performanceanalizerapplication.hibernate;

import com.example.performanceanalizerapplication.utils.DatetimeUtils;
import org.apache.calcite.config.Lex;
import org.apache.calcite.sql.*;
import org.apache.calcite.sql.dialect.MysqlSqlDialect;
import org.apache.calcite.sql.parser.SqlParseException;
import org.apache.calcite.sql.parser.SqlParser;
import org.jetbrains.annotations.NotNull;

public class SqlParserUtils {
  public static String parseSql(String sql) {
      SqlParser.Config config = SqlParser.config().withLex(Lex.MYSQL);
        SqlParser sqlParser = SqlParser.create(sql, config);
      try {
          SqlNode sqlNode = sqlParser.parseStmt();
          if (sqlNode.getKind() == SqlKind.SELECT) {
              SqlSelect sqlSelect = getSqlSelect((SqlSelect) sqlNode);
              if(sqlSelect.getWhere().getKind() == SqlKind.IN) {
                    SqlBasicCall basicCall = (SqlBasicCall) sqlSelect.getWhere();
                    SqlNode sqlNode1 = basicCall.getOperands()[1];
                    if(sqlNode1.getKind() == SqlKind.SELECT) {
                       getSqlSelect((SqlSelect) sqlNode1);
                    }
              }
          }
          return sqlNode.toSqlString(MysqlSqlDialect.DEFAULT).getSql();
      } catch (SqlParseException e) {
          throw new RuntimeException(e);
      }
  }

    private static @NotNull SqlSelect getSqlSelect(SqlSelect sqlNode) {
        SqlSelect sqlSelect = sqlNode;
        if (sqlSelect.getFrom().getKind() == SqlKind.AS) {
            SqlBasicCall basicCall = (SqlBasicCall) sqlSelect.getFrom();
            SqlIdentifier tableName = (SqlIdentifier) basicCall.getOperands()[0];
            String s = tableName.names.get(0);
            String newTableName = String.format("%s_%s",s, DatetimeUtils.currentDay.get());
            SqlIdentifier newTable = tableName.setName(0, newTableName);
            basicCall.setOperand(0, newTable);
        }
        return sqlSelect;
    }

}
