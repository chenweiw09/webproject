package com.marker.biz.domain;

/**
 * Created by chenwei23 on 2016/12/14.
 */
public class Attr{

    // 属性名称
    private String field;

    // Java类型
    private String javaType;

    //属性的注释
    private String marker;

    //属性的mybatis数据库类型
    private String ibatisJdbcType;

    //是否是主键
    private boolean primary;

    public Attr(){

    }

    public Attr(String field,  String javaType){
        this.field = field;
        this.javaType = javaType;
    }

    public Attr(String field,  String javaType, String marker, String ibatisJdbcType){
        this.field = field;
        this.javaType = javaType;
        this.marker = marker;
        this.ibatisJdbcType = ibatisJdbcType;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getJavaType() {
        return javaType;
    }

    public void setJavaType(String javaType) {
        this.javaType = javaType;
    }

    public String getMarker() {
        return marker;
    }

    public void setMarker(String marker) {
        this.marker = marker;
    }

    public String getIbatisJdbcType() {
        return ibatisJdbcType;
    }

    public void setIbatisJdbcType(String ibatisJdbcType) {
        this.ibatisJdbcType = ibatisJdbcType;
    }

    public boolean isPrimary() {
        return primary;
    }

    public void setPrimary(boolean primary) {
        this.primary = primary;
    }
}
