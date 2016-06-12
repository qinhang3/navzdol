package com.github.navzdol.repository;

import com.github.navzdol.dataobject.Record;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.SqlResultSetMapping;

/**
 * Created by hang on 16/6/11.
 */
public interface RecordRepo extends CrudRepository<Record,Long>{
    @Query("select * from t_user u where u.username=?1")
    void getData(int type);
}
