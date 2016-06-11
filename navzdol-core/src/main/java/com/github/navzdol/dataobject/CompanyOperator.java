package com.github.navzdol.dataobject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by hang on 16/6/11.
 */
@Entity
public class CompanyOperator {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private Long recordId;

    @Column
    private Long companyId;

    private Integer type;

    public enum CompanyOperatorType {
        BUY(1),SOLD(2);

        private int code;

        CompanyOperatorType(int code) {
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

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
