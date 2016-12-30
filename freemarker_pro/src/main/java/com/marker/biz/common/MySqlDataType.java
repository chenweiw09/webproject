package com.marker.biz.common;

/**
 * Created by chenwei23 on 2016/12/15.
 * 数据格式转换枚举值
 */
public enum MySqlDataType {
    VARCHAR("varchar", "String", "VARCHAR"),
    CHAR( "char", "String", "CHAR"),
    BLOB( "blob", "byte[]", "BLOB"),
    TEXT("text", "String", "LONGVARCHAR"),
    INT( "int", "Long", "INTEGER"),
    TINYINT("tinyint", "Integer", "TINYINT"),
    SMALLINT("smallint", "Short", "SMALLINT"),
    MEDIUMINT("mediumint", "Integer", "MEDIUMINT"),
    BIT("bit", "Boolean", "BIT"),
    BIGINT("bigint", "Long", "BIGINT"),
    FLOAT( "float", "Float", "FLOAT"),
    DOUBLE("double", "Double", "DOUBLE"),
    DECIMAL("decimal", "BigDecimal", "DECIMAL"),
    BINARY("binary", "byte[]", "BINARY"),
    DATE("date", "Date", "DATE"),
    DATETIME("datetime", "Date", "TIMESTAMP"),
    TIME("time", "Date", "TIME"),
    TIMESTAMP("timestamp", "Date", "TIMESTAMP"),
    UNSIGNED_INT("int unsigned", "Long", "INTEGER"),
    UNSIGNED_BIGINT("bigint unsigned", "Long", "BIGINT"),
    UNSIGNED_TINYINT("tinyint unsigned", "Long", "TINYINT"),
    LONGTEXT("longtext", "String", "LONGTEXT"),
    ;


    private String mySqlDataType;
    private String javaDataType;
    private String ibatisJdbcType;

    private MySqlDataType(String mySqlDataType, String javaDataType, String ibatisJdbcType) {
        this.javaDataType = javaDataType;
        this.mySqlDataType = mySqlDataType;
        this.ibatisJdbcType = ibatisJdbcType;
    }

    public String getMySqlDataType() {
        return mySqlDataType;
    }

    public String getJavaDataType() {
        return javaDataType;
    }

    public String getIbatisJdbcType() {
        return ibatisJdbcType;
    }

    public void setMySqlDataType(String mySqlDataType) {
        this.mySqlDataType = mySqlDataType;
    }

    public void setJavaDataType(String javaDataType) {
        this.javaDataType = javaDataType;
    }

    public void setIbatisJdbcType(String ibatisJdbcType) {
        this.ibatisJdbcType = ibatisJdbcType;
    }
}
