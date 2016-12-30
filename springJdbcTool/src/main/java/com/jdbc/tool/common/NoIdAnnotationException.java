package com.jdbc.tool.common;

public class NoIdAnnotationException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	private String msg = "no id annotation found!";
	private String code;
	
	public NoIdAnnotationException(@SuppressWarnings("rawtypes") Class clazz){
		super(clazz + " doesn't have an id field, please make sure " + clazz + " has a column with an @id annotation.");
	}
	
	public NoIdAnnotationException(String msg){
		this.msg = msg;
	}
	
	public NoIdAnnotationException(String code, String msg){
		this.code = code;
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "NoIdAnnotationException [msg=" + msg + ", code=" + code + "]";
	}
	
}
