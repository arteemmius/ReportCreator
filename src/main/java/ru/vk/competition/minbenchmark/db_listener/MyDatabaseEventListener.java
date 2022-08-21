package ru.vk.competition.minbenchmark.db_listener;

import org.h2.api.DatabaseEventListener;
import ru.vk.competition.minbenchmark.entity.TableQuery;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class MyDatabaseEventListener implements DatabaseEventListener {

    @Override
    public void init(String s) {
    }

    @Override
    public void opened() {

    }

    @Override
    public void exceptionThrown(SQLException e, String s) {
    }

    @Override
    public void setProgress(int i, String s, int i1, int i2) {
        if (i == STATE_STATEMENT_END && s.toUpperCase(Locale.ROOT).contains("RENAME")) { //if (s.matches("(?i).*ALTER\\s+TABLE\\s+.+RENAME\\s+TO.*")) { //catch rename request
            try (Connection connection = DbConfig.getConnection(); Statement statement = connection.createStatement()) {
                System.out.println("setProgress_RENAME s=" + s);
                // example:  ALTER TABLE "TEST_SCHEMA"."TEST_TABLE" RENAME TO TESTTABLE
                String[] tableNames = s.toUpperCase(Locale.ROOT).replaceAll("\\s{2,}", " ").trim()
                        .replaceAll("ALTER\\sTABLE\\s|\\sRENAME\\sTO|\"", "").split(" ");
                int index = Math.max(0, tableNames[0].indexOf('.'));
                String oldName = tableNames[0].substring(index);
                String newName = tableNames[1];
                //System.out.println("oldName=" + oldName);
                //System.out.println("newName=" + newName);
                String sql = "SELECT * FROM table_query where locate (lower(TABLE_NAME), '" +
                        oldName.toLowerCase(Locale.ROOT) + "');";
                ResultSet resultSet = statement.executeQuery(sql);
                List<TableQuery> tableQueryList = new ArrayList<>();
                while (resultSet.next()) {
                    TableQuery tableQuery = new TableQuery();
                    tableQuery.setQueryId(resultSet.getInt("QUERY_ID"));
                    tableQuery.setQuery(resultSet.getString("QUERY"));
                    tableQuery.setTableName(resultSet.getString("TABLE_NAME"));
                    tableQueryList.add(tableQuery);
                }
                System.out.println("tableQueryList=" + tableQueryList);
                if (!tableQueryList.isEmpty()) {
                    for (TableQuery tableQuery : tableQueryList) {
                        sql = "UPDATE table_query SET TABLE_NAME = '" + newName + "', QUERY='" +
                                tableQuery.getQuery().replaceAll("(?i)" + oldName, newName) + "' where QUERY_ID=" +
                        tableQuery.getQueryId() + ";";
                        statement.executeUpdate(sql);
                    }
                }
            }
            catch (Exception e) {
                System.out.println("setProgress_RENAME: ERROR in s=" + s);
                //e.printStackTrace();
            }
        }
        //
        if (i == STATE_STATEMENT_END && s.toUpperCase(Locale.ROOT).contains("DROP")) {
            if (DbConfig.jdbcURL == null) return; //страховка от начальных удалений spring
            try (Connection connection = DbConfig.getConnection(); Statement statement = connection.createStatement()) {
                System.out.println("setProgress_DROP s=" + s);
                String[] tableNames = s.replaceAll("\\s|DROP|TABLE|RESTRICT|CASCADE", "").split(",");
                //System.out.println(Arrays.toString(tableNames));
                String sql;
                for (String tableName_i: tableNames) {
                    sql = "DELETE FROM table_query where locate (lower(TABLE_NAME), '" +
                            tableName_i.toLowerCase(Locale.ROOT) + "');";
                    statement.executeUpdate(sql);
                }
            }
            catch (Exception e) {
                System.out.println("setProgress_DROP: ERROR in s=" + s);
                //e.printStackTrace();
            }
        }
    }

    @Override
    public void closingDatabase() {

    }
}