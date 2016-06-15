package com.github.navzdol.algorithm;

import com.github.navzdol.TestBase;
import com.github.navzdol.repository.CompanyOperatorRepo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by hang on 16/6/15.
 */
public class CompanyOperatorRepoTest extends TestBase {
    @Autowired
    private CompanyOperatorRepo companyOperatorRepo;

    @Test
    public void test() throws ParseException {
        String start = "2016-01-01 00:00:00";
        String end = "2016-06-01 00:00:00";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Iterable<String> maps = companyOperatorRepo.queryGroupByTime(sdf.parse(start),sdf.parse(end),1);
        System.out.println(maps);
    }
}
