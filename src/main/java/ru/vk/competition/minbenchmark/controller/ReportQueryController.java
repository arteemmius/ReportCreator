package ru.vk.competition.minbenchmark.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vk.competition.minbenchmark.entity.TableQuery;
import ru.vk.competition.minbenchmark.model.Report;
import ru.vk.competition.minbenchmark.service.ReportQueryService;

@RestController
@RequestMapping("/api/report")
public class ReportQueryController {
    @Autowired
    ReportQueryService reportQueryService;

    @PostMapping("/create-report")
    public ResponseEntity<Void> createReport(@RequestBody Report report)
    {
        //System.out.println("createReport.report=" + report);
        ResponseEntity<Void> status = reportQueryService.createReport(report);
        System.out.println("createReport.status=" + status);
        return status;
    }

    @GetMapping("/get-report-by-id/{id}")
    public ResponseEntity<Report> getReportById(@PathVariable Integer id) {
        System.out.println("getReportById.id=" + id);
        ResponseEntity<Report> report = reportQueryService.getReportById(id);
        //System.out.println("getReportById.report=" + report);
        return report;
    }
}
