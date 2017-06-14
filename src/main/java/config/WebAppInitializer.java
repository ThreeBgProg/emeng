package config;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import org.springframework.web.util.Log4jConfigListener;

public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	/**
	 * 配置根配置类
	 */
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[]{RootConfig.class};
	}

	/**
	 * 配置Servlet配置
	 */
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[]{WebConfig.class};
	}

	/**
	 * 将DispatchServlety映射到"/"
	 */
	@Override
	protected String[] getServletMappings() {
		return new String[]{"/"};
	}
	
	/**
	 * 配置拦截过滤器
	 */
	@Override
	protected Filter[] getServletFilters() {
		
		//创建编码过滤器
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		//设置编码为UTF-8
		characterEncodingFilter.setEncoding("UTF-8");
		//设置请求和响应均强制编码
		characterEncodingFilter.setForceEncoding(true);
		
		//添加Spring的编码过滤器
		return new Filter[]{characterEncodingFilter};
	}
	
	@Override
	protected void registerContextLoaderListener(ServletContext servletContext) {
		servletContext.addListener(new Log4jConfigListener());
		super.registerContextLoaderListener(servletContext);
	}
	
	@Override
	protected void customizeRegistration(Dynamic registration) {
		
		//配置MutipartResolver文件上传解析器的参数
		//临时目录：D:/temp/
		//单文件最大容量：5242880 Byte == 500 MB
		//请求文件最大容量：20971520 Byte == 2000 MB
		registration.setMultipartConfig(new MultipartConfigElement(null, 524288000, 2097152000, 0));
	}
}
