package ru.vk.competition.minbenchmark.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.vk.competition.minbenchmark.entity.SingleQuery;
import ru.vk.competition.minbenchmark.service.SingleQueryService;

@Slf4j
@RestController
@RequestMapping("/api/single-query")
@RequiredArgsConstructor
public class SingleQueryController {

    private final SingleQueryService singleQueryService;

    @GetMapping("/get-all-single-queries")
    public Flux<SingleQuery> getAllTableQueries() {
        System.out.println("getAllTableQueries start");
        Flux<SingleQuery> res = singleQueryService.getAllQueries();
        //System.out.println("getAllTableQueries.res=" + res);
        return res;
    }

    @GetMapping("/get-single-query-by-id/{id}")
    public Mono<SingleQuery> getTableQueryById(@PathVariable Integer id) {
        System.out.println("getTableQueryById.id=" + id);
        Mono<SingleQuery> res = singleQueryService.getQueryById(id);
        //System.out.println("getTableQueryById.res=" + res);
        return res;
    }

    @DeleteMapping("/delete-single-query-by-id/{id}")
    public Mono<ResponseEntity<Void>> deleteTableQueryById(@PathVariable Integer id) {
        System.out.println("deleteTableQueryById.id=" + id);
        Mono<ResponseEntity<Void>> res = singleQueryService.deleteQueryById(id);
        //System.out.println("deleteTableQueryById.res=" + res);
        return res;
    }

    @PostMapping("/add-new-query")
    public Mono<ResponseEntity<Void>> addNewQueryToTable(@RequestBody SingleQuery singleQuery) {
        System.out.println("addNewQueryToTable start");
        Mono<ResponseEntity<Void>> res = singleQueryService.addQueryWithQueryId(singleQuery);
        //System.out.println("addNewQueryToTable.res=" + res);
        return res;
    }

    @PutMapping("/modify-query")
    public Mono<ResponseEntity<Void>> modifyQueryInTable(@RequestBody SingleQuery singleQuery) {
        System.out.println("modifyQueryInTable start");
        Mono<ResponseEntity<Void>> res = singleQueryService.updateQueryWithQueryId(singleQuery);
        //System.out.println("modifyQueryInTable.res=" + res);
        return res;
    }

    @PutMapping("/execute-single-query-by-id/{id}")
    public ResponseEntity<Void> executeQueryById(@PathVariable Integer id) {
        System.out.println("executeQueryById.id=" + id);
        ResponseEntity<Void> status = singleQueryService.executeQueryById(id);
        System.out.println("executeQueryById.status=" + status);
        return status;
    }
}