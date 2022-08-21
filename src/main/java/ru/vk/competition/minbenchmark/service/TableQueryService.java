package ru.vk.competition.minbenchmark.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ru.vk.competition.minbenchmark.entity.TableQuery;
import ru.vk.competition.minbenchmark.repository.TableQueryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TableQueryService {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    TableQueryRepository tableQueryRepository;
    @Autowired
    CreateTableQueryService createTableQueryService;

    public  ResponseEntity<Void> addNewQueryToTable(TableQuery tableQuery) {
        if (createTableQueryService.showTableStructure(tableQuery.getTableName()) == null) {
            System.out.println("addNewQueryToTable ERROR, no table");
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        try {
            tableQueryRepository.save(tableQuery);
        }
        catch (Exception e) {
            System.out.println("addNewQueryToTable hard error");
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        System.out.println("addNewQueryToTable OK");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public ResponseEntity<Void> updateQueryWithQueryId(TableQuery tableQuery) {
        if (createTableQueryService.showTableStructure(tableQuery.getTableName()) == null) {
            System.out.println("updateQueryWithQueryId ERROR, no table");
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        if (tableQueryRepository.findByQueryId(tableQuery.getQueryId()).isEmpty()) {
            System.out.println("updateQueryWithQueryId ERROR, don't find any tableQuery");
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        try {
            tableQueryRepository.save(tableQuery);
        }
        catch (Exception e) {
            System.out.println("updateQueryWithQueryId hard error");
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        System.out.println("updateQueryWithQueryId OK");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteQueryById(Integer id) {
        try {
            if(tableQueryRepository.findByQueryId(id).map(TableQuery::getQueryId).isEmpty()) {
                System.out.println("deleteQueryById ERROR, don't find any tableQuery");
                return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
            } else {
                tableQueryRepository.deleteByQueryId(id);
                System.out.println("deleteQueryById OK");
                return new ResponseEntity<>(HttpStatus.ACCEPTED);
            }
        } catch (Exception e) {
            System.out.println("deleteQueryById hard error");
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    public ResponseEntity<Void> executeQueryById(Integer id) {
        Optional<TableQuery> query = tableQueryRepository.findByQueryId(id);
        if (query.isEmpty()) {
            System.out.println("executeQueryById ERROR, don't find any tableQuery");
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        try {
            jdbcTemplate.execute(query.get().getQuery());
        }
        catch (Exception e) {
            System.out.println("executeQueryById hard error");
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        System.out.println("executeQueryById OK");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public List<TableQuery> getAllTableQueriesByName(String name) {
        System.out.println("getAllTableQueriesByName start");
        if (createTableQueryService.showTableStructure(name) == null) {
            System.out.println("getAllTableQueriesByName ERROR, no table");
            return null;
        }
        return tableQueryRepository.findByTableName(name);
    }

    public void deleteQueryByTableName(String name) {
        try {
            tableQueryRepository.deleteByTableName(name);
            System.out.println("deleteQueryByTableName OK");
        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println("deleteQueryByTableName hard error");
        }
    }

    public ResponseEntity<TableQuery> getQueryById(Integer id) {
        Optional<TableQuery> tableQuery = tableQueryRepository.findByQueryId(id);
        if (tableQuery.isEmpty()) {
            System.out.println("getQueryById ERROR, don't find tableQuery by id");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        System.out.println("getQueryById OK");
        return new ResponseEntity<>(tableQuery.get(), HttpStatus.OK);
    }

    public List<TableQuery> getAllTableQueries() {
        List<TableQuery> tableQueryList = new ArrayList<>();
        tableQueryRepository.findAll().forEach(tableQueryList::add);
        System.out.println("getAllTableQueries OK");
        return tableQueryList;
    }
}
