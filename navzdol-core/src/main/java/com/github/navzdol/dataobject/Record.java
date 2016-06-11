package com.github.navzdol.dataobject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by hang on 16/6/11.
 */
@Entity
public class Record {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String code;
    @Column
    private String name;
    @Column
    private Date date;
    @Column
    private Integer type;

    public enum RecodeType{
        ALL(1),SH(2),SZ(3),ZXB(4),CYB(5);

        private int code;

        RecodeType(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
