package ru.vk.competition.minbenchmark.model;

import java.util.List;

public class CreateTable {
    private String tableName;
    private Integer columnsAmount;
    private String primaryKey;
    private List<ColumnInfos> columnInfos;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Integer getColumnsAmount() {
        return columnsAmount;
    }

    public void setColumnsAmount(Integer columnsAmount) {
        this.columnsAmount = columnsAmount;
    }

    public String getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }

    public List<ColumnInfos> getColumnInfos() {
        return columnInfos;
    }

    public void setColumnInfos(List<ColumnInfos> columnInfos) {
        this.columnInfos = columnInfos;
    }

    public CreateTable(String tableName, Integer columnsAmount, String primaryKey, List<ColumnInfos> columnInfos) {
        this.tableName = tableName;
        this.columnsAmount = columnsAmount;
        this.primaryKey = primaryKey;
        this.columnInfos = columnInfos;
    }

    @Override
    public String toString() {
        return "CreateTable{" +
                "tableName='" + tableName + '\'' +
                ", columnsAmount=" + columnsAmount +
                ", primaryKey='" + primaryKey + '\'' +
                ", columnInfos=" + columnInfos +
                '}';
    }
}
