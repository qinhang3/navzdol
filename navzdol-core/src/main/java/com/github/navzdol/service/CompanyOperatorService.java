package com.github.navzdol.service;

import com.github.navzdol.dataobject.Company;
import com.github.navzdol.dataobject.CompanyOperator;
import com.github.navzdol.dataobject.Record;
import com.github.navzdol.repository.CompanyOperatorRepo;
import com.github.navzdol.repository.CompanyRepo;
import com.github.navzdol.repository.RecordRepo;
import com.github.navzdol.util.GsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by hang on 16/6/11.
 */
@Service
public class CompanyOperatorService {
    @Autowired
    private CompanyRepo companyRepo;
    @Autowired
    private RecordRepo recordRepo;
    @Autowired
    private CompanyOperatorRepo companyOperatorRepo;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Transactional
    public void save(Record record, Map<String,List<Company>> companies){
        record = recordRepo.save(record);
        List<CompanyOperator> companyOperators = new ArrayList<>();
        for (Map.Entry<String, List<Company>> entry : companies.entrySet()) {
            for (Company company : entry.getValue()) {
                Company oCompany = companyRepo.findByFullName(company.getFullName());
                if (oCompany != null){
                    company = oCompany;
                } else {
                    company = companyRepo.save(company);
                }

                CompanyOperator companyOperator = new CompanyOperator();
                companyOperator.setRecordId(record.getId());
                companyOperator.setCompanyId(company.getId());
                companyOperator.setType(CompanyOperator.CompanyOperatorType.valueOf(entry.getKey()).getCode());

                companyOperators.add(companyOperator);
            }
        }
        companyOperatorRepo.save(companyOperators);
    }
}
