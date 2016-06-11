package com.github.navzdol.util;

import org.apache.http.HttpResponse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Created by hang on 16/6/11.
 */
public class HtmlUtil {
    public static Document getDocument(HttpResponse httpResponse, String charset, String url) throws IOException {
        return Jsoup.parse(httpResponse.getEntity().getContent(),charset,url);
    }
}
