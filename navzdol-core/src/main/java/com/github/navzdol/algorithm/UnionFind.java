package com.github.navzdol.algorithm;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hang on 16/6/17.
 */
public class UnionFind {
    private Map<String,String> f = new HashMap<>();

    public void addRel(String a,String b){
        String fa = findFather(a);
        String fb = findFather(b);
        if (fa.compareTo(fb) > 0){
            f.put(fa,fb);
        } else if (fa.compareTo(fb) < 0){
            f.put(fb,fa);
        }
    }

    public String findFather(String a){
        String father = f.get(a);
        if (father == null){
            father = a;
        }
        if (father.equals(a)){
            return father;
        } else {
            father = findFather(father);
            f.put(a,father);
        }
        return father;
    }

    public static void main(String[] args){
        UnionFind unionFind = new UnionFind();
        unionFind.addRel("7","8");
        unionFind.addRel("7","9");
        unionFind.addRel("2","3");
        unionFind.addRel("1","2");
        unionFind.addRel("1","3");
        unionFind.addRel("1","7");
        unionFind.addRel("5","6");
//        System.out.println(unionFind.f);
        System.out.println(unionFind.findFather("1"));
        System.out.println(unionFind.findFather("2"));
        System.out.println(unionFind.findFather("3"));
        System.out.println(unionFind.findFather("5"));
        System.out.println(unionFind.findFather("6"));
        System.out.println(unionFind.findFather("7"));
        System.out.println(unionFind.findFather("8"));
        System.out.println(unionFind.findFather("9"));
    }
}
