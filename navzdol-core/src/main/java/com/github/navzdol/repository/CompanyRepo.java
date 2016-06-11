package com.github.navzdol.repository;

import com.github.navzdol.dataobject.Company;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by hang on 16/6/11.
 */
public interface CompanyRepo extends CrudRepository<Company,Long>{
    Company findByFullName(String fullName);
}
