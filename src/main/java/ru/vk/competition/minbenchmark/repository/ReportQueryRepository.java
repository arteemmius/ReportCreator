package ru.vk.competition.minbenchmark.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.vk.competition.minbenchmark.entity.ReportQuery;

import java.util.Optional;

@Repository
public interface ReportQueryRepository extends CrudRepository<ReportQuery, String> {
    Optional<ReportQuery> findByReportId(Integer id);
}
