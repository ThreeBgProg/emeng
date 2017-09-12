package config;

import org.springframework.context.annotation.*;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

/**
 * SpringMVC：servletConfig
 * 
 * @author Jack
 * @date 2017年5月15日
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages={ "com.huiming.emeng.controller"},
		includeFilters={@Filter(type = FilterType.ANNOTATION, value = Controller.class)})
@PropertySource("classpath:application.properties")
@ImportResource("classpath:application.xml")
public class WebConfig extends WebMvcConfigurerAdapter {
	
	/**
	 * 配置JSP视图解析器
	 * 
	 * @return 视图解析器
	 */
	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/jsp/");
		resolver.setSuffix(".jsp");
		resolver.setViewClass(org.springframework.web.servlet.view.JstlView.class);
		resolver.setExposeContextBeansAsAttributes(true);
		return resolver;
	}

	/**
	 * 配置资源的静态访问
	 */
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	/**
	 * 配置Multipart解析器,用于from表格提交文件后再控制器中可使用Multipart获取
	 * 
	 * @return Multipart解析器
	 */
	@Bean
	public MultipartResolver multipartResolver() {
		return new StandardServletMultipartResolver();
	}

	/**
	 * 配置消息转换器 配置@ResponseBody注解返回的方式制定为json
	 */
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

		// 新建字符串消息的转换器，并配置编码为UTF-8
		StringHttpMessageConverter stringConverter = new StringHttpMessageConverter();
		// 为转换器设置所支持的媒体类型
		stringConverter.setSupportedMediaTypes(
				Arrays.asList(new MediaType[] { new MediaType("text", "html", Charset.forName("UTF-8")) }));
		// 添加字符串消息转换器
		converters.add(stringConverter);

		// 添加字节数组消息转换器
		converters.add(new ByteArrayHttpMessageConverter());

		// 添加表格消息转换器
		converters.add(new FormHttpMessageConverter());

		// 新建json转换器
		MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
		// 为转换器设置所支持的媒体类型
		jsonConverter.setSupportedMediaTypes(
				Arrays.asList(new MediaType[] { new MediaType("application", "json"), new MediaType("text", "json") }));
		// 添加json转换器
		converters.add(jsonConverter);
	}

}
