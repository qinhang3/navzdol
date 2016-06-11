package com.github.navzdol.algorithm.domain;

import javax.validation.constraints.NotNull;
import java.util.*;

/**
 * Created by hang on 16/6/10.
 */
public class FPData {
    private List<String> data;
    private int cnt;

    public FPData(@NotNull List<String> data, int cnt) {
        this.data = data;
        this.cnt = cnt;
    }

    public void deleteAndSort(Map<String,Integer> counter,int minSup){
        List<String> newData = new ArrayList<>();
        for (String s : data) {
            if (counter.containsKey(s) && counter.get(s) >= minSup){
                newData.add(s);
            }
        }
        Collections.sort(newData, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                if (counter.get(o1).equals(counter.get(o2))){
                    return o1.compareTo(o2);
                } else {
                    return counter.get(o1) - counter.get(o2);
                }
            }
        });
        Collections.reverse(newData);

        this.data = newData;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }
}
