package ru.vk.competition.minbenchmark.model;

import java.util.List;

public class TableInfo {
    private String tableName;
    private List<ColumnInfos> columns;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<ColumnInfos> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnInfos> columns) {
        this.columns = columns;
    }

    @Override
    public String toString() {
        return "TableInfo{" +
                "tableName='" + tableName + '\'' +
                ", columns=" + columns +
                '}';
    }
}
