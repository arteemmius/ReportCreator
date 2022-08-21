package ru.vk.competition.minbenchmark.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ru.vk.competition.minbenchmark.entity.TableStructure;
import ru.vk.competition.minbenchmark.model.ColumnInfos;
import ru.vk.competition.minbenchmark.model.CreateTable;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@Slf4j
@RequiredArgsConstructor
public class CreateTableQueryService {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    //@Lazy
    //@Autowired
    //TableQueryService tableQueryService;

    public ResponseEntity<Void> createTable(String tableName, int columnsAmount, String primaryKey, List<ColumnInfos> columnInfos) {
        if (columnsAmount != columnInfos.size()) {
            System.out.println("createTable Error");
            return new ResponseEntity<Void>(HttpStatus.NOT_ACCEPTABLE);
        }
        StringBuilder createTableSQL = new StringBuilder();
        createTableSQL.append("create table ");
        createTableSQL.append(tableName);
        createTableSQL.append(" (");
        boolean checkPrimaryKey = false;
        for (int i = 0; i < columnInfos.size(); i++) {
            String title_i = columnInfos.get(i).getTitle();
            createTableSQL.append(title_i);
            createTableSQL.append(" ");
            createTableSQL.append(columnInfos.get(i).getType());
            if (title_i.equals(primaryKey)) {
                createTableSQL.append(" ");
                createTableSQL.append("primary key");
                checkPrimaryKey = true;
            }
            if (i != columnInfos.size() - 1) {
                createTableSQL.append(",");
            }
        }
        if (primaryKey != null && !checkPrimaryKey) {
            System.out.println("createTable Error in primaryKey=" + primaryKey);
            return new ResponseEntity<Void>(HttpStatus.NOT_ACCEPTABLE);
        }
        createTableSQL.append(");");
        System.out.println("createTableSQL=" + createTableSQL);
        try {
            jdbcTemplate.execute(createTableSQL.toString());
        }
        catch (Exception e) {
            //e.printStackTrace();
            System.out.println("createTable hard error");
            return new ResponseEntity<Void>(HttpStatus.NOT_ACCEPTABLE);
        }
        System.out.println("createTable OK");
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    public CreateTable showTableStructure(String name) {
        String sql = "show columns from " + name + ";";
        System.out.println("showTableStructure.sql=" + sql);
        try {
            List<TableStructure> tableStructures = jdbcTemplate.query(sql, (ResultSet rs, int rowNum) -> {
                TableStructure tableStructure = new TableStructure();
                tableStructure.setField(rs.getString("field"));
                tableStructure.setType(rs.getString("type").replaceAll("\\(\\d+\\)", ""));
                tableStructure.setKey(rs.getString("key"));
                tableStructure.setDefaultV(rs.getString("default"));
                return tableStructure;
            });
            if (tableStructures.isEmpty()) {
                return null;
            }
            List<ColumnInfos> columnInfos = new ArrayList<>();
            CreateTable createTableQuery = new CreateTable(name, 0, null, columnInfos);
            for (int i = 0; i < tableStructures.size(); i++) {
                String type = tableStructures.get(i).getType();
                ColumnInfos columnInfos_element = new ColumnInfos(tableStructures.get(i).getField(), type);
                columnInfos.add(columnInfos_element);
                if (tableStructures.get(i).getKey().equals("PRI")) {
                    createTableQuery.setPrimaryKey(tableStructures.get(i).getField().toLowerCase(Locale.ROOT));
                }
            }
            createTableQuery.setColumnsAmount(columnInfos.size());
            //System.out.println("showTableStructure.createTableQuery=" + createTableQuery);
            System.out.println("showTableStructure OK");
            return createTableQuery;
        }
        catch (Exception e) {
            //e.printStackTrace();
            System.out.println("showTableStructure hard error");
            return null;
        }
    }

    public ResponseEntity<Void> deleteTable(String name) {
        String dropTableSQL = "DROP TABLE " + name + " RESTRICT";
        System.out.println("deleteTable.dropTableSQL=" + dropTableSQL);
        try {
            jdbcTemplate.execute(dropTableSQL);
            System.out.println("deleteTable OK");
            //tableQueryService.deleteQueryByTableName(name);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        catch (Exception e) {
            //e.printStackTrace();
            System.out.println("deleteTable ERROR");
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @Deprecated
    private boolean checkField(String value) {
        int size = value.length();
        return value.replaceAll("[а-яА-Я]", "").length() == size;
    }
}
