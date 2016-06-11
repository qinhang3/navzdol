package com.github.navzdol.util;

import com.google.gson.Gson;

/**
 * Created by hang on 16/6/10.
 */
public class GsonUtil {
    private static final Gson gson = new Gson();
    public static String toJson(Object o){
        return gson.toJson(o);
    }
}
