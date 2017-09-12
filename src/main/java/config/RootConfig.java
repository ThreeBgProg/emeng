package config; 

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;


/**
 * spring-config
 * 
 * @author Jack
 * @date 2017年5月15日
 */
@Configuration
@ComponentScan(basePackages={"com.huiming.emeng"},
		excludeFilters={@Filter(type = FilterType.ANNOTATION, value = Controller.class)})
@Import(DataSourceConfig.class)
@EnableScheduling
public class RootConfig {

//	@Bean(initMethod="initMethod")
//	public StartupListener startupListener() {
//		return new StartupListener();
//	}
//
	
}