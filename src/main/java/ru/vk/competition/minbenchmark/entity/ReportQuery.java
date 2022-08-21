package ru.vk.competition.minbenchmark.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Builder
@Entity
@Table(name = "report_query")
@NoArgsConstructor
@AllArgsConstructor
public class ReportQuery {
    @Id
    @Column(name = "reportId")
    private Integer reportId;

    @Column(name = "tableAmount")
    private Integer tableAmount;

    @Column(name = "tables")
    private String tables;

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

    public String getTables() {
        return tables;
    }

    public void setTables(String tables) {
        this.tables = tables;
    }
}
