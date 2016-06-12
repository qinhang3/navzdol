package com.github.navzdol.repository;

import com.github.navzdol.dataobject.Record;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.SqlResultSetMapping;

/**
 * Created by hang on 16/6/11.
 */
public interface RecordRepo extends CrudRepository<Record,Long>{
//    @Query("select record_id,group_concat(company_id) from company_operator\n" +
//            "where type = 1\n" +
//            "group by record_id\n")
//    void getData(int type);
}
