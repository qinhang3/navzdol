package com.github.navzdol.algorithm;

import com.github.navzdol.TestBase;
import com.github.navzdol.algorithm.domain.FPResult;
import com.github.navzdol.repository.CompanyOperatorRepo;
import com.github.navzdol.service.AlgorighmService;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by hang on 16/6/15.
 */
public class AlgorithmServiceTest extends TestBase{
    @Autowired
    private AlgorighmService algorighmService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    public void test() throws ParseException {
        String start = "2016-01-01 00:00:00";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        long startTime = System.currentTimeMillis();
        List<FPResult> fpResults = algorighmService.fpGrowth(sdf.parse(start), 10);
        logger.info("run time = {}",System.currentTimeMillis() - startTime);

        for (FPResult fpResult : fpResults) {
            logger.info("found {} cnt = {}",fpResult.getData(),fpResult.getCnt());
        }
    }
}
