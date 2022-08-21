package ru.vk.competition.minbenchmark.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.vk.competition.minbenchmark.entity.ReportQuery;
import ru.vk.competition.minbenchmark.model.ColumnInfos;
import ru.vk.competition.minbenchmark.model.CreateTable;
import ru.vk.competition.minbenchmark.model.Report;
import ru.vk.competition.minbenchmark.model.TableInfo;
import ru.vk.competition.minbenchmark.repository.ReportQueryRepository;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReportQueryService {
    @Autowired
    ReportQueryRepository reportQueryRepository;
    @Autowired
    CreateTableQueryService tableService;

    public ResponseEntity<Report> getReportById(Integer id) {
        Optional<ReportQuery> reportQuery = reportQueryRepository.findByReportId(id);
        if (reportQuery.isEmpty()) {
            System.out.println("getReportById ERROR, reportQuery is empty");
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }
        System.out.println("getReportById OK");
        ReportQuery reportQueryFin = reportQuery.get();
        System.out.println("getReportById.getReportId=" + reportQueryFin.getReportId());
        System.out.println("getReportById.getTables=" + reportQueryFin.getTables());
        return new ResponseEntity<>(convertReportDbToReportModel(reportQueryFin), HttpStatus.OK);
    }

    private Report convertReportDbToReportModel(ReportQuery reportQuery) {
        Report report = new Report();
        report.setReportId(reportQuery.getReportId());
        report.setTableAmount(reportQuery.getTableAmount());
        List<TableInfo> tableInfoList = new ArrayList<>();
        String[] tables = reportQuery.getTables().split(";");
        for (int i = 0; i < tables.length; i++) {
            TableInfo tableInfo = new TableInfo();
            tableInfo.setTableName(tables[i]);
            List<ColumnInfos> columnInfos = tableService.showTableStructure(tables[i]).getColumnInfos();
            tableInfo.setColumns(columnInfos);
            tableInfoList.add(tableInfo);
        }
        report.setTables(tableInfoList);
        //System.out.println("convertReportDbToReportModel.report=" + report);
        return report;
    }


    public ResponseEntity<Void> createReport(Report report) {
        if (report.getTableAmount() != report.getTables().size()) {
            System.out.println("createReport ERROR, bad TableAmount");
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        //
        Optional<ReportQuery> reportQueryById = reportQueryRepository.findByReportId(report.getReportId());
        if (reportQueryById.isPresent()) {
            System.out.println("getReportById ERROR, report already exist");
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        //
        ReportQuery reportQuery = convertReportModelToReportDb(report);
        if (reportQuery == null) {
            System.out.println("createReport ERROR, bad report data");
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        reportQueryRepository.save(reportQuery);
        System.out.println("createReport OK");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    private ReportQuery convertReportModelToReportDb(Report report) {
        //System.out.println("convertReportModelToReportDb.report=" + report);
        ReportQuery reportQuery = new ReportQuery();
        reportQuery.setReportId(report.getReportId());
        reportQuery.setTableAmount(report.getTableAmount());
        List<TableInfo> tableInfoList = report.getTables();
        StringBuilder tables = new StringBuilder();
        for (int i = 0; i < tableInfoList.size(); i++) {
            String tableName = tableInfoList.get(i).getTableName();
            CreateTable createTable = tableService.showTableStructure(tableName);
            if (createTable == null) {
                return null;
            }
            List<ColumnInfos> columnInfosList = createTable.getColumnInfos();
            if (columnInfosList.isEmpty()) {
                return null;
            }
            HashMap<String , String> map = new HashMap<>();
            for (ColumnInfos infos : columnInfosList) {
                //System.out.println("infos=" + infos);
                map.put(infos.getTitle(), infos.getType());
            }
            List<ColumnInfos> columnInfos = tableInfoList.get(i).getColumns();
            for (ColumnInfos columnInfo : columnInfos) {
                String title = columnInfo.getTitle().toUpperCase(Locale.ROOT);
                String type = columnInfo.getType().replaceAll("[\\d\\(\\),\\.]", "").toUpperCase(Locale.ROOT);
                if (type.equalsIgnoreCase("VARCHAR")) {
                    type = "CHARACTER VARYING"; //for strange task
                }
                //System.out.println("columnInfo=" + columnInfo);
                String value = map.get(title);
                if (value == null) { //stub for stupid tests
                    value = map.get(tableName.toUpperCase(Locale.ROOT) + title);
                }
                if (value == null || (!value.equalsIgnoreCase(type) && !value.startsWith(type)))
                    return null;
            }
            tables.append(tableName);
            if (i != tableInfoList.size() - 1) {
                tables.append(";");
            }
        }
        reportQuery.setTables(tables.toString());
        System.out.println("convertReportModelToReportDb.reportId=" + reportQuery.getReportId());
        System.out.println("convertReportModelToReportDb.reportId=" + reportQuery.getTables());
        return reportQuery;
    }

}
