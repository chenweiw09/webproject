package com.jdbc.tool.common;

import java.lang.reflect.Method;

public class NoColumnAnnotationException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	private String msg = "no column annotation found!";
	private String code;
	
	public NoColumnAnnotationException(String ClassName,Method getter){
		super(ClassName + "." + getter.getName() + "() should have an @Column annotation.");
	}
	
	public NoColumnAnnotationException(String msg){
		this.msg = msg;
	}
	
	public NoColumnAnnotationException(String code, String msg){
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
