package com.github.navzdol.service;

import com.github.navzdol.algorithm.FPGrowth;
import com.github.navzdol.algorithm.domain.FPResult;
import com.github.navzdol.repository.CompanyOperatorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by hang on 16/6/15.
 */
@Service
public class AlgorighmService {
    @Autowired
    private CompanyOperatorRepo companyOperatorRepo;

    public List<FPResult> fpGrowth(Date startDate, int minSup){
        Iterable<String> strings = companyOperatorRepo.queryGroupByTime(startDate, new Date(), 1);
        FPGrowth fpGrowth = new FPGrowth(minSup);
        fpGrowth.run((List<String>) strings);
        return fpGrowth.getResult();
    }
}
