package com.huiming.emeng.common;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 基础异常类
 * 
 * @author Jack
 * @date 2017年5月15日
 */
public class BaseException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5272118220235896687L;
	/**
	 * 异常对应返回的Http状态码，默认为500
	 */
	private int respCode = 500;
	/**
	 * 异常对应返回的异常消息
	 */
	private String msg = "页面出错了！";

	/**
	 * 异常对应返回的jsp页面
	 */
	private String respPage;

	
	/**
	 * 默认构造器，自动获取异常注解的值并注入对象属性
	 */
	public BaseException() {
		CustomExp annotation = this.getClass().getAnnotation(CustomExp.class);
		if(annotation != null ) {
			this.respCode = annotation.respCode();
			this.msg = annotation.msg();
			this.respPage = annotation.respPage();
		}
	}
	
	/**
	 * 带消息的异常构造器
	 * @param msg 异常消息
	 */
	public BaseException(String msg) {
		this();
		this.msg = msg;
	}
	
	public int getRespCode() {
		return respCode;
	}
	public void setRespCode(int respCode) {
		this.respCode = respCode;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getRespPage() {
		return respPage;
	}
	public void setRespPage(String respPage) {
		this.respPage = respPage;
	}
	
}

/**
 * 用于自定义异常的注解
 * 其中respCode规定了该异常的http响应返回的状态码；
 * msg对应该异常返回的http响应消息；
 * respPage对应异常返回的页面。
 * 
 * @author Jack
 * @date 2017年5月15日
 */
@Documented
@Inherited 
@Retention(RetentionPolicy.RUNTIME)  
@Target({ElementType.TYPE})  
@interface CustomExp {
	int respCode() default 500;
	String msg() default "出现错误了！";
	String respPage() default "error";
}