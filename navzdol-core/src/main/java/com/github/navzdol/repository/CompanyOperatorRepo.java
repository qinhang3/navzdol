package com.github.navzdol.repository;

import com.github.navzdol.dataobject.CompanyOperator;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.Map;

/**
 * Created by hang on 16/6/11.
 */
public interface CompanyOperatorRepo extends CrudRepository<CompanyOperator, Long> {
    @Query(value = "select group_concat(company_id) from company_operator a,record b " +
            "where a.record_id = b.id " +
            "and b.date > :startDate " +
            "and b.date < :endDate " +
            "and a.company_id <> 30 " +
            "and a.type= :operatorType group by record_id", nativeQuery = true)
    Iterable<String> queryGroupByTime(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param(value = "operatorType") int operatorType);
}
