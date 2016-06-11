package com.github.navzdol.algorithm.domain;

import java.util.List;

/**
 * Created by hang on 16/6/10.
 */
public class FPResult {
    private List<String> data;
    private List<FPData> extData;
    private int cnt;

    public FPResult(List<String> data, List<FPData> extData, int cnt) {
        this.data = data;
        this.extData = extData;
        this.cnt = cnt;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    public List<FPData> getExtData() {
        return extData;
    }

    public void setExtData(List<FPData> extData) {
        this.extData = extData;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    @Override
    public String toString() {
        return "FPResult{" +
                "data=" + data +
                ", extData=" + extData +
                ", cnt=" + cnt +
                '}';
    }
}
