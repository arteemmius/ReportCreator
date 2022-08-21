package ru.vk.competition.minbenchmark.model;

import java.util.List;

public class Report {
    private Integer reportId;
    private Integer tableAmount;
    private List<TableInfo> tables;

    public Integer getReportId() {
        return reportId;
    }

    public void setReportId(Integer reportId) {
        this.reportId = reportId;
    }

    public Integer getTableAmount() {
        return tableAmount;
    }

    public void setTableAmount(Integer tableAmount) {
        this.tableAmount = tableAmount;
    }

    public List<TableInfo> getTables() {
        return tables;
    }

    public void setTables(List<TableInfo> tables) {
        this.tables = tables;
    }

    @Override
    public String toString() {
        return "Report{" +
                "reportId=" + reportId +
                ", tableAmount=" + tableAmount +
                ", tables=" + tables +
                '}';
    }
}
