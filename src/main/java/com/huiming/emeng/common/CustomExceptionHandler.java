package com.huiming.emeng.common;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * 异常处理器
 * 
 * @author Jack
 * @date 2017年5月15日
 */
@ControllerAdvice
public class CustomExceptionHandler {

	/**
	 * 异常处理方法
	 * @param exception 抛出的运行时异常
	 * @param resp Http响应对象
	 * @return 错误页面
	 */
	@ExceptionHandler(RuntimeException.class)
	public ModelAndView exceptionHandle(RuntimeException exception,HttpServletResponse resp) {

		ModelAndView modelView;
		//如果是自定义异常则返回自定义异常的页面
		if(exception.getClass().equals(BaseException.class) || exception.getClass().getSuperclass().equals(BaseException.class)) {
			BaseException e = (BaseException)exception;
			modelView = new ModelAndView(e.getRespPage());
			resp.setStatus(e.getRespCode());
			modelView.addObject("msg",e.getMsg());
		} else {
			//若非自定义异常则返回默认的错误页面并将异常的信息栈打印出来
			modelView = new ModelAndView("error");
			exception.printStackTrace();
		}
		
		return modelView;
	}
}
