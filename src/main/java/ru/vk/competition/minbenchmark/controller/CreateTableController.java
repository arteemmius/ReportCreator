package ru.vk.competition.minbenchmark.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vk.competition.minbenchmark.model.CreateTable;
import ru.vk.competition.minbenchmark.service.CreateTableQueryService;
import ru.vk.competition.minbenchmark.service.TableQueryService;

@RestController
@RequestMapping("/api/table")
public class CreateTableController {
    @Autowired
    CreateTableQueryService tableService;

    @PostMapping("/create-table")
    @ResponseBody
    public ResponseEntity<Void> createTable(@RequestBody CreateTable createTableQuery) {
        //System.out.println("createTable start");
        ResponseEntity<Void> status = tableService.createTable(createTableQuery.getTableName(), createTableQuery.getColumnsAmount(),
                createTableQuery.getPrimaryKey(), createTableQuery.getColumnInfos());
        System.out.println("createTable.status=" + status);
        return status;
    }

    @GetMapping(value = "/get-table-by-name/{name}")
    @ResponseBody
    public CreateTable showTableStructure(@PathVariable(name = "name") String name) {
        System.out.println("showTableStructure.name=" + name);
        CreateTable result = tableService.showTableStructure(name);
        //System.out.println("showTableStructure.result=" + result);
        return result;
    }

    @DeleteMapping(value = "/drop-table/{name}")
    @ResponseBody
    public ResponseEntity<Void> deleteTable(@PathVariable(name = "name") String name) {
        System.out.println("deleteTable.name=" + name);
        ResponseEntity<Void> status = tableService.deleteTable(name);
        System.out.println("deleteTable.status=" + status);
        return status;
    }
}
