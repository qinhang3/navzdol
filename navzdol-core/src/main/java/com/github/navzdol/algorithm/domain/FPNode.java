package com.github.navzdol.algorithm.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hang on 16/6/10.
 */
public class FPNode {

    private String value;
    private int cnt = 0;
    private Map<String, FPNode> children = new HashMap<>();
    private FPNode next;
    private FPNode parent;

    public FPNode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public Map<String, FPNode> getChildren() {
        return children;
    }

    public void setChildren(Map<String, FPNode> children) {
        this.children = children;
    }

    public FPNode getNext() {
        return next;
    }

    public void setNext(FPNode next) {
        this.next = next;
    }

    public FPNode getParent() {
        return parent;
    }

    public void setParent(FPNode parent) {
        this.parent = parent;
    }

    public void inc(int c){
        cnt += c;
    }
}
