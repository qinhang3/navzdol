package com.github.navzdol.algorithm;

import com.github.navzdol.Application;
import com.github.navzdol.repository.RecordRepo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by hang on 16/6/12.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class RecordRepoTest {
    @Autowired
    private RecordRepo recordRepo;

    @Test
    public void test(){
//        System.out.println(recordRepo.getData(1));
    }
}
