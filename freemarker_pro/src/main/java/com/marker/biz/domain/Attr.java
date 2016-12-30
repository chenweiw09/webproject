package com.marker.biz.domain;

import java.io.Serializable;

/**
 * Created by chenwei23 on 2016/12/14.
 */
public class Attr{

    // 属性名称
    public String field;

    // Java类型
    public String javaType;

    //属性的注释
    public String marker;

    //属性的mybatis数据库类型
    public String ibatisJdbcType;

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
}
