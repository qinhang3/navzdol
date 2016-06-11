package com.github.navzdol.dataobject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by hang on 16/6/11.
 */
@Entity
public class Company {
    @Id
    @GeneratedValue
    private Long id;
    @Column(length = 1024)
    private String fullName;
    @Column(length = 1024)
    private String priName;
    @Column(length = 1024)
    private String subName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPriName() {
        return priName;
    }

    public void setPriName(String priName) {
        this.priName = priName;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    private static final String split = "公司";
    public void buildName(String fullName){
        setFullName(fullName);
        int i = fullName.indexOf(split);
        if (i == -1) {
            return;
        }
        i += split.length();
        setPriName(fullName.substring(0,i));
        setSubName(fullName.substring(i));
    }
}
