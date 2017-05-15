package config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;


/**
 * spring-config
 * 
 * @author Jack
 * @date 2017年5月15日
 */
@Configuration
@ComponentScan(basePackages={"com.huiming.emeng"})
@Import(DataSourceConfig.class)
@EnableScheduling
public class RootConfig {
	
}
