package com.huiming.emeng.common;


public class CustomException {
	
	/**
	 * 生成对应的异常
	 * @param genClass 需要生成的异常类
	 * @param msg 生成的异常消息
	 * @return 异常对象
	 */
	public static BaseException genException(Class<? extends BaseException> genClass,String msg) {
		try {
			BaseException result = genClass.newInstance();
			result.setMsg(msg);
			return result;
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return new BaseException(msg);
	}
	
	@CustomExp(respCode=401,msg="未获得权限",respPage="404")
	public static class UnauthorizedError extends BaseException{};
}
