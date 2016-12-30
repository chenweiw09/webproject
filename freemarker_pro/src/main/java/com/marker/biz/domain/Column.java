package com.marker.biz.domain;

/**
 * Created by chenwei23 on 2016/12/15.
 */
public class Column {

    //列属性名
    public String columnName;

    //指定列的数据类型名
    public String typeName;

    //指定列的数据类型
    public int sqlDataType;

    //是否允许为null
    public boolean sqlNotNull;

    //默认值
    public String defaultValue;

    //是否是只读
    public boolean sqlReadOnly;

    //注释，用于实体注释
    public String remark;

    public boolean equals(Object o) {
        boolean rv = false;
        if (o != null && o instanceof Column) {
            rv = (columnName.equals(((Column)o).columnName));
        }
        return rv;
    }
    public int hashCode() {
        return (columnName != null) ? columnName.hashCode() : 0;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getSqlDataType() {
        return sqlDataType;
    }

    public void setSqlDataType(int sqlDataType) {
        this.sqlDataType = sqlDataType;
    }

    public boolean isSqlNotNull() {
        return sqlNotNull;
    }

    public void setSqlNotNull(boolean sqlNotNull) {
        this.sqlNotNull = sqlNotNull;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public boolean isSqlReadOnly() {
        return sqlReadOnly;
    }

    public void setSqlReadOnly(boolean sqlReadOnly) {
        this.sqlReadOnly = sqlReadOnly;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}