package com.portal.basedao;

import java.io.Serializable;

/**
 * Base Entity.
 * 
 * 
 */
public class BaseEntity implements Serializable {

    /**
     * generated id.
     */
    private static final long serialVersionUID = 1L;

    private int primaryKey;

    private String primaryKeyStr;

    /**
     * Default Constructor.
     */
    public BaseEntity() {
        super();
    }

    public int getPrimaryKey() {
        return this.primaryKey;
    }

    public String getPrimaryKeyStr() {
        return this.primaryKeyStr;
    }

    public void setPrimaryKey(final int primaryKey) {
        this.primaryKey = primaryKey;
    }

    public void setPrimaryKeyStr(final String primaryKeyStr) {
        this.primaryKeyStr = primaryKeyStr;
    }

}
