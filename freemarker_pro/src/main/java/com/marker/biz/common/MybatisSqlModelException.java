package com.marker.biz.common;

/**
 * Created by chenwei23 on 2016/12/19.
 */
public class MybatisSqlModelException extends RuntimeException{
    private static final long serialVersionUID = 1L;


    /**
     * 创建异常
     * @param errorMessage 错误描述
     */
    public MybatisSqlModelException(String errorMessage) {
        this(errorMessage, null);
    }

    /**
     * 创建异常
     * @param errorMessage 错误描述
     * @param cause 错误栈信息
     */
    public MybatisSqlModelException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }


    public String getErrorMessage() {
        return this.getMessage();
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }


    @Override
    public String toString() {
        String s = getClass().getName();
        String message = mergeMesssage();
        return (message != null) ? (s + ": " + message) : s;
    }

    private String mergeMesssage() {
        String msg;
        if (getMessage()== null || "".equals(getMessage())) {
            msg = getMessage() + "(" + getLocalizedMessage() + ")";
        } else {
            msg = getLocalizedMessage();
        }
        return msg;
    }

}
