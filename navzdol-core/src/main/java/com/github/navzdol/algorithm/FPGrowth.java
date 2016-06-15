package com.github.navzdol.algorithm;

import com.github.navzdol.algorithm.domain.FPData;
import com.github.navzdol.algorithm.domain.FPNode;
import com.github.navzdol.algorithm.domain.FPResult;

import java.io.InputStream;
import java.util.*;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by hang on 16/6/10.
 */
public class FPGrowth {

    private final int minSup;

    public FPGrowth(int minSup) {
        this.minSup = minSup;
    }

    public List<FPResult> getResult() {
        return result;
    }

    private List<FPResult> result = new ArrayList<>();

    private List<FPResult> work(FPResult data) {

        if (data.getExtData().isEmpty()){
            result.add(data);
            return new ArrayList<>();
        }

        Map<String, Integer> counter = new HashMap<>();
        //count
        for (FPData fpData : data.getExtData()) {
            for (String s : fpData.getData()) {
                if (counter.containsKey(s)) {
                    counter.put(s, counter.get(s) + fpData.getCnt());
                } else {
                    counter.put(s, fpData.getCnt());
                }
            }
        }

        for (FPData fpData : data.getExtData()) {
            fpData.deleteAndSort(counter,minSup);
        }

        //initTree
        FPNode root = new FPNode(null);
        Map<String,FPNode> fpNodes = new HashMap<>();

        //buildTree
        for (FPData fpData : data.getExtData()) {
            FPNode now = root;
            for (String s : fpData.getData()) {
                if (!now.getChildren().containsKey(s)){
                    FPNode newNode = new FPNode(s);
                    newNode.setParent(now);
                    newNode.setNext(fpNodes.get(s));
                    fpNodes.put(s,newNode);
                    now.getChildren().put(s,newNode);
                }
                now = now.getChildren().get(s);
                now.inc(fpData.getCnt());
            }
        }

        List<FPResult> fpResults = new ArrayList<>();

        //find subTree
        for (Map.Entry<String, FPNode> entry : fpNodes.entrySet()) {
            FPNode now = entry.getValue();
            List<String> prefix = new ArrayList<>();
            prefix.addAll(data.getData());
            prefix.add(entry.getKey());
            int cnt = 0;

            List<FPData> fpDatas = new ArrayList<>();

            while (now != null){
                cnt += now.getCnt();

                List<String> s = new ArrayList<>();

                FPNode p = now.getParent();
                while(p.getValue() != null){
                    s.add(p.getValue());
                    p = p.getParent();
                }

                if (!s.isEmpty()) {
                    fpDatas.add(new FPData(s, now.getCnt()));
                }
                now = now.getNext();
            }

            fpResults.add(new FPResult(prefix,fpDatas,cnt));
        }

        return fpResults;
    }

    public void run(List<String> datas){
        List<FPData> fpDatas = new ArrayList<>();
        for (String data : datas) {
            List<String> ss = new ArrayList<>();
            for (String s1 : data.split(",")) {
                ss.add(s1);
            }
            fpDatas.add(new FPData(ss,1));
        }
        Queue<FPResult> queue = new LinkedBlockingDeque<>();
        queue.add(new FPResult(new ArrayList<>(),fpDatas,1));
        while(!queue.isEmpty()){
            List<FPResult> fpResults = work(queue.poll());
            queue.addAll(fpResults);
        }
    }

    public static void main(String[] args){
        InputStream is = FPGrowth.class.getResourceAsStream("/input.txt");
        Scanner scanner = new Scanner(is);
        List<String> datas = new ArrayList<>();

        while(scanner.hasNext()){
            String s = scanner.nextLine();
            datas.add(s);
        }

        FPGrowth fpGrowth = new FPGrowth(5);
        fpGrowth.run(datas);
        System.out.println(fpGrowth.getResult());
    }
}
