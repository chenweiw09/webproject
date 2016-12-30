package ${packageName};

import java.io.Serializable;

/**
* Created by ${author} on ${date}.
*/
public class CommResult<E> implements Serializable {
    private static final long serialVersionUID = 1L;
    private String code;
    private String msg;
    private E data;

    public CommResult(){}
    public CommResult(String code, String msg){
    this(code, msg, null);
    }

    public CommResult(String code, String msg, E data){
    this.code = code;
    this.msg = msg;
    this.data =data;
    }

    public String getCode() {
    return code;
    }

    public void setCode(String code) {
    this.code = code;
    }

    public String getMsg() {
    return msg;
    }

    public void setMsg(String msg) {
    this.msg = msg;
    }

    public E getData() {
    return data;
    }

    public void setData(E data) {
    this.data = data;
    }
    }