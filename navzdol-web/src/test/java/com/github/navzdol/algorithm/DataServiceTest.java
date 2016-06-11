package com.github.navzdol.algorithm;

import com.github.navzdol.Application;
import com.github.navzdol.algorithm.DataService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by hang on 16/6/11.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)

public class DataServiceTest {
    @Autowired
    private DataService dataService;

    @Test
    public void test() throws ParseException {
        String date = "2016-06-07";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        dataService.getData(sdf.parse(date));
    }
}
