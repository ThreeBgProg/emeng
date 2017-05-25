package com.huiming.emeng.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;

/**
 * 自定义注解，仅能搭配@RequestMapping注解使用，
 * 对@RequestMapping中的url进行功能描述
 * 只能应用在方法上
 * @author Achan
 *
 */

@Retention(RUNTIME)
@Target(METHOD)
@Documented
@Component
public @interface MappingDescription {

	public String value() default "未设置Mapping描述";
}
