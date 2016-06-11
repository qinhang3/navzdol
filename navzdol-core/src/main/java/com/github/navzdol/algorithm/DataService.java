package com.github.navzdol.algorithm;

import com.github.navzdol.CompanyOperatorService;
import com.github.navzdol.dataobject.Company;
import com.github.navzdol.dataobject.Record;
import com.github.navzdol.repository.CompanyRepo;
import com.github.navzdol.repository.RecordRepo;
import com.github.navzdol.util.GsonUtil;
import com.github.navzdol.util.HtmlUtil;
import com.github.navzdol.util.HttpUtil;
import org.apache.http.HttpResponse;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by hang on 16/6/11.
 */
@Service
public class DataService {

    @Autowired
    private CompanyOperatorService companyOperatorService;

    /**
     * BOARD:
     * http://data.10jqka.com.cn/market/longhu/cate/ALL/ajax/1/date/2016-06-07/
     * http://data.10jqka.com.cn/market/longhu/cate/SH/ajax/1/date/2016-06-07/
     * http://data.10jqka.com.cn/market/longhu/cate/SZ/ajax/1/date/2016-06-07/
     * http://data.10jqka.com.cn/market/longhu/cate/ZXB/ajax/1/date/2016-06-07/
     * http://data.10jqka.com.cn/market/longhu/cate/CYB/ajax/1/date/2016-06-07/
     *
     * @param date
     */

    private final String[] dataTypes = new String[]{"ALL", "SH", "SZ", "ZXB", "CYB"};

    private Logger logger = LoggerFactory.getLogger(getClass());

    public void getData(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (String dataType : dataTypes) {
            String url = String.format("http://data.10jqka.com.cn/market/longhu/cate/%s/ajax/1/date/%s/", dataType, sdf.format(date));
            Document document;
            try {
                HttpResponse response = HttpUtil.executeGet(url, new HashMap<>(), false);
                document = HtmlUtil.getDocument(response,"GBK",url);
            } catch (IOException e) {
                logger.error("get data error.url = {}", url, e);
                continue;
            }

            Elements elements = document.select("#maintable tbody tr");
            for (Element element : elements) {
                Record record = buildRecord(element, date, dataType);
                Map<String, List<Company>> companies = getCompanies(record);
                companyOperatorService.save(record,companies);
            }
        }
    }

    /**
     * http://data.10jqka.com.cn/market/lhbcjmx/code/603959/date/2016-05-23/ajax/1
     * @param record
     */
    private final String[] companyOperatorTypes = new String[]{"BUY","SOLD"};
    private Map<String,List<Company>> getCompanies(Record record) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String url = String.format("http://data.10jqka.com.cn/market/lhbcjmx/code/%s/date/%s/ajax/1",record.getCode(),sdf.format(record.getDate()));
        Document document;
        try {
            HttpResponse response = HttpUtil.executeGet(url, new HashMap<>(), false);
            document = HtmlUtil.getDocument(response, "GBK", url);
        } catch (IOException e) {
            logger.error("get document error.url = {}",url,e);
            throw new RuntimeException(e);
        }

        Elements elements = document.select(".m_table tbody tr");
        int typeIndex = -1;
        Map<String,List<Company>> companies = new HashMap<>();

        for (Element element : elements) {
            if (element.attr("class").contains("lhb_td_desc")){
                typeIndex++;
                if (typeIndex >= companyOperatorTypes.length){
                    break;
                }
                companies.put(companyOperatorTypes[typeIndex],new ArrayList<>());
                continue;
            }
            companies.get(companyOperatorTypes[typeIndex]).add(buildCompany(element));
        }
        return companies;
    }

    private Company buildCompany(Element element) {
        Company company = new Company();
        String name = element.select(".jj_stockinfo_toggle a").html();
        company.buildName(name);
        return company;
    }

    private Record buildRecord(Element element, Date date, String type) {
        Elements elements = element.select("td");
        Record record = new Record();
        record.setCode(elements.get(1).select("a").html());
        record.setName(elements.get(2).select("a").html());
        record.setDate(date);
        record.setType(Record.RecodeType.valueOf(type).getCode());
        return record;
    }
}
