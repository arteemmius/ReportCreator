package ru.vk.competition.minbenchmark.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vk.competition.minbenchmark.entity.TableQuery;
import ru.vk.competition.minbenchmark.service.TableQueryService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/table-query")
public class TableQueryController {
    @Autowired
    TableQueryService tableQueryService;

    @PostMapping("/add-new-query-to-table")
    public ResponseEntity<Void> addNewQueryToTable(@RequestBody TableQuery tableQuery)
    {
        System.out.println("addNewQueryToTable start");
        ResponseEntity<Void> status = tableQueryService.addNewQueryToTable(tableQuery);
        System.out.println("addNewQueryToTable.status=" + status);
        return status;
    }

    @PutMapping("/modify-query-in-table")
    public ResponseEntity<Void> updateQueryInTable(@RequestBody TableQuery tableQuery) {
        System.out.println("updateQueryInTable start");
        ResponseEntity<Void> status = tableQueryService.updateQueryWithQueryId(tableQuery);
        System.out.println("updateQueryInTable.status=" + status);
        return status;
    }

    @DeleteMapping("/delete-table-query-by-id/{id}")
    public ResponseEntity<Void> deleteTableQueryById(@PathVariable Integer id) {
        System.out.println("deleteTableQueryById.id=" + id);
        ResponseEntity<Void> status = tableQueryService.deleteQueryById(id);
        System.out.println("deleteTableQueryById.status=" + status);
        return status;
    }

    @GetMapping("/execute-table-query-by-id/{id}")
    public ResponseEntity<Void> modifyQueryInTable(@PathVariable Integer id) {
        System.out.println("modifyQueryInTable.id=" + id);
        ResponseEntity<Void> status = tableQueryService.executeQueryById(id);
        System.out.println("modifyQueryInTable.status=" + status);
        return status;
    }

    @GetMapping("/get-all-queries-by-table-name/{name}")
    public List<TableQuery> getAllTableQueriesByName(@PathVariable String name) {
        System.out.println("getAllTableQueriesByName.name=" + name);
        List<TableQuery> tableQueryList = tableQueryService.getAllTableQueriesByName(name);
        //System.out.println("getAllTableQueriesByName.tableQueryList=" + tableQueryList);
        return tableQueryList;
    }

    @GetMapping("/get-table-query-by-id/{id}")
    public ResponseEntity<TableQuery> getTableQueryById(@PathVariable Integer id) {
        System.out.println("getTableQueryById.id=" + id);
        ResponseEntity<TableQuery> res = tableQueryService.getQueryById(id);
        //System.out.println("getTableQueryById.res=" + res);
        return res;
    }

    @GetMapping("/get-all-table-queries")
    public List<TableQuery> getAllTableQueries() {
        System.out.println("getAllTableQueries start");
        List<TableQuery> tableQueryList = tableQueryService.getAllTableQueries();
        //System.out.println("getAllTableQueries.tableQueryList=" + tableQueryList);
        return tableQueryList;
    }
}
