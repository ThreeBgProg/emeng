package config;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

/**
 * 数据库配置
 * 
 * @author Jack
 * @date 2017年5月15日
 */
@Configuration
@PropertySource("classpath:application.properties")
public class DataSourceConfig implements EnvironmentAware{

	/**
	 * Environment变量，用于加载application.properties
	 */
	@Autowired
    private Environment env; 
	

	/**
	 * 实现EnvironmentAware接口的方法确保environment注入成功
	 */
	public void setEnvironment(Environment env) {
		this.env = env;
	}
	
	/**
	 * 加载持久化模板Bean
	 * @param sqlSessionFactory 数据库会话工厂
	 * @return 持久化模板
	 */
	@Bean
	public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory, ExecutorType.BATCH);
	}
	
	/**
	 * 加载自动扫描配置
	 * @return Mapper包的自动扫描配置
	 * @throws Exception 在扫描基础包失败时将抛出异常
	 */
	@Bean
	public MapperScannerConfigurer mapperScannerConfigurer() throws Exception{
		MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
		mapperScannerConfigurer.setBasePackage("com.huiming.emeng.mapper;");
		return mapperScannerConfigurer;
	}
	
	/**
	 * 加载数据库会话工厂
	 * @param dataSource 数据库对象
	 * @return 数据库会话工厂
	 * @throws Exception 当从会话对象工厂的Bean获取对象失败时
	 */
	@Bean
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) 
			throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);  
        return sqlSessionFactoryBean.getObject();
	}
	
	/**
	 * 加载JDBC事务管理
	 * @param dataSource
	 * @return 数据库数据管理器
	 */
	@Bean
	public DataSourceTransactionManager transactionManager(DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}
	
	/**
	 * 加载数据库对象
	 * @return 数据库对象
	 */
	@Bean
	public DataSource dataSource() {
		//使用数据连接池
		BasicDataSource dataSource = new BasicDataSource();
		//设置连接信息
		dataSource.setDriverClassName(env.getRequiredProperty("jdbc.driver"));
		dataSource.setUrl(env.getRequiredProperty("jdbc.url"));
		dataSource.setUsername(env.getRequiredProperty("jdbc.username"));
		dataSource.setPassword(env.getRequiredProperty("jdbc.password"));
		//设置连接池最大连接数
		dataSource.setMaxTotal(500);
		//设置最大空闲连接数量
		dataSource.setMaxIdle(200);
		//设置最小空闲连接数量
		dataSource.setMinIdle(50);
		//设置最大等待连接的时间
		dataSource.setMaxWaitMillis(3600);
		//设置连接池把空闲时间超过minEvictableIdleTimeMillis毫秒的连接断开, 直到连接池中的连接数到minIdle为止
		dataSource.setMinEvictableIdleTimeMillis(3600000);
		//设置自动回收连接池
		dataSource.setRemoveAbandonedOnBorrow(true);
		return dataSource;
	}


}
