package ru.vk.competition.minbenchmark.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@Entity
@Table(name = "table_query")
@NoArgsConstructor
@AllArgsConstructor
public class TableQuery {
    @Id
    @Column(name = "queryId")
    private Integer queryId;

    @Column(name = "tableName")
    private String tableName;

    @Lob
    @Column(name = "query")
    private String query;

    public Integer getQueryId() {
        return queryId;
    }

    public void setQueryId(Integer queryId) {
        this.queryId = queryId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
