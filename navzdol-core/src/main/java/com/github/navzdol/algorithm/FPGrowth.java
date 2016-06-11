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

    private List<FPResult> work(FPResult data) {

        if (data.getExtData().isEmpty()){
            System.out.println(String.format("found {%s} , cnt = %d",data.getData(),data.getCnt()));
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

    public static void main(String[] args){
        List<FPData> fpDatas = new ArrayList<>();
        InputStream is = FPGrowth.class.getResourceAsStream("/input.txt");
        Scanner scanner = new Scanner(is);
        while(scanner.hasNext()){
            String s = scanner.nextLine();
            List<String> ss = new ArrayList<>();
            for (String s1 : s.split(",")) {
                ss.add(s1);
            }
            fpDatas.add(new FPData(ss,1));
        }

        FPResult fpResult = new FPResult(new ArrayList<>(),fpDatas,0);
        FPGrowth fpGrowth = new FPGrowth(5);

        Queue<FPResult> queue = new LinkedBlockingDeque<>();
        queue.add(fpResult);

        while(!queue.isEmpty()){
            List<FPResult> fpResults = fpGrowth.work(queue.poll());
            queue.addAll(fpResults);
            System.out.println("size = " + queue.size());
        }
    }
}
