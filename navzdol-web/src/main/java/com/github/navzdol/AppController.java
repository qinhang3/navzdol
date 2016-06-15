package com.github.navzdol;

import com.github.navzdol.algorithm.domain.FPResult;
import com.github.navzdol.service.AlgorighmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.websocket.server.PathParam;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by hang on 16/6/15.
 */
@Controller
public class AppController {

    @Autowired
    private AlgorighmService algorighmService;

    @RequestMapping("getResult")
    @ResponseBody
    public List<FPResult> getResult(@PathParam("start")String start, @PathParam("end")String end, @PathParam("minSup")Integer minSup) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = sdf.parse(start);
        Date endDate = sdf.parse(end);
        List<FPResult> fpResults = algorighmService.fpGrowth(startDate, minSup);

        Collections.sort(fpResults, (o1, o2) -> o1.getCnt() - o2.getCnt());

        return fpResults;
    }
}
