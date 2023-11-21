package com.api.ivr.config;

import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Properties;

import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan(basePackages = "com.api.ivr")
@EnableJpaRepositories(basePackages = "com.api.ivr.db.repository", entityManagerFactoryRef = "entityManagerFactory")
@EnableAutoConfiguration
@PropertySource("classpath:application.properties")
// @PropertySources({@PropertySource(value =
// "file:/Config/application.properties")})
@EnableScheduling
@EnableCaching
@PersistenceContext
public class AppConfig {
	private static final String PROPERTY_NAME_HIBERNATE_DIALECT = "hibernate.dialect";
	public static final int THREE_MINS = 60 * 3 * 1000;

	@Autowired
	private  Environment env;
	
	@Configuration
	@Profile("dev")
	@PropertySource("file:D:/Subbu/host/BH/BH_ConfigFilePath/Restapiapplication.properties")

	//@PropertySource("file:C:/Users/1665256/Kalaivanan/BH/BH_PROD/BH_ConfigFilePath/application-prod.properties")
	//@PropertySource("file:${catalina.base}/webapps/HostConfig/application-dev.properties")
	//@PropertySource("file:C:\\Users\\1665257\\OneDrive - Standard Chartered Bank\\Documents\\Raja\\BH\\Config\\BH_ConfigFilePath\\Config\\application-dev.properties")
	static class dev
	{
		
	}
	@Configuration
	@Profile("sit")
	@PropertySource("file:${catalina.base}/webapps/HostConfig/application-sit.properties")
	static class sit
	{
		
	}
	
	@Configuration
	@Profile("prod")
	@PropertySource("file:${catalina.base}/webapps/HostConfig/application-prod.properties")
	static class prod
	{
		
	}
	@Configuration
	@Profile("uat")
	@PropertySource("file:${catalina.base}/webapps/HostConfig/application-uat.properties")
	static class uat
	{
		
	}
	
	@Configuration
	@Profile("dr")
	@PropertySource("file:${catalina.base}/webapps/HostConfig/application-dr.properties")
	static class dr
	{
		
	}

	@Bean
	public CacheManager cacheManager() {
		List<ConcurrentMapCache> caches = Arrays.asList(new ConcurrentMapCache("configfile"));
		SimpleCacheManager cacheManager = new SimpleCacheManager();
		cacheManager.setCaches(caches);
		return cacheManager;
	}

	/*
	 * @Bean public PropertySourcesPlaceholderConfigurer
	 * propertySourcesPlaceholderConfigurer() {
	 * PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer
	 * = new PropertySourcesPlaceholderConfigurer();
	 * propertySourcesPlaceholderConfigurer.setLocations(new
	 * ClassPathResource("application.properties"));
	 * //propertySourcesPlaceholderConfigurer.setIgnoreUnresolvablePlaceholders(
	 * true);
	 * //propertySourcesPlaceholderConfigurer.setIgnoreResourceNotFound(true);
	 * return propertySourcesPlaceholderConfigurer; }
	 */

	@Bean
	public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	
	/*@Configuration
	@EnableSchedulerLock(defaultLockAtMostFor = "PT30S")
	public class SchedulerConfiguration {
		
	    @Bean
	    public LockProvider lockProvider() {
	        return new JdbcTemplateLockProvider(dataSource());
	    }
	}*/

/*	@Bean(destroyMethod = "close")
	@Primary
	public DataSource dataSource() {
		HikariDataSource hikariDataSource = null;
		try {
			hikariDataSource = new HikariDataSource(hikariConfig());
		}catch(Exception exception) {
			hikariDataSource = new HikariDataSource(hikariConfigDR());
		}
		return hikariDataSource;
	}
*/
	
	public HikariDataSource hikariConfig() {
		System.out.println("Tomcat path : "+System.getProperty("catalina.base"));
		//TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
		String[] activeProfiles = env.getActiveProfiles();
		for(String activeprof:activeProfiles){
			System.out.println("Active Profile :"+activeprof);
		}

		String driver = env.getProperty("spring.datasource.driverClassName");
		String username = env.getProperty("spring.datasource.username");
		String dbpassword = env.getProperty("spring.datasource.password");
		String url = env.getProperty("spring.datasource.url");
		String strpoolname = env.getProperty("spring.datasource.hikari.poolName");
		String maxpoolsize = env.getProperty("spring.datasource.hikari.maximumPoolSize");
		String minidle = env.getProperty("spring.datasource.hikari.minimumIdle");
		
		String url_dr = env.getProperty("spring.datasource.url.dr");
		String username_dr = env.getProperty("spring.datasource.username.dr");
		String dbpassword_dr = env.getProperty("spring.datasource.password.dr");
		
		HikariDataSource dataSource = null;

		HikariConfig hikariConfig = new HikariConfig();
		hikariConfig.setDriverClassName(driver);
		hikariConfig.setJdbcUrl(url);
		hikariConfig.setUsername(username);
		hikariConfig.setPassword(decode(dbpassword));
		//hikariConfig.setPassword(dbpassword);

		hikariConfig.setMaximumPoolSize(Integer.parseInt(maxpoolsize));
		hikariConfig.setMinimumIdle(Integer.parseInt(minidle));
		hikariConfig.setPoolName(strpoolname);
		hikariConfig.setAutoCommit(true);
		hikariConfig.setConnectionTimeout(10000);

		// hikariConfig.setConnectionTestQuery("SELECT 1");
		// hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
		// hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
		// hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
		hikariConfig.setLeakDetectionThreshold(THREE_MINS);
		
		HikariConfig hikariConfigDR = new HikariConfig();
		hikariConfigDR.setDriverClassName(driver);
		hikariConfigDR.setJdbcUrl(url_dr);
		hikariConfigDR.setUsername(username_dr);
		hikariConfigDR.setPassword(decode(dbpassword_dr));

		hikariConfigDR.setMaximumPoolSize(Integer.parseInt(maxpoolsize));
		hikariConfigDR.setMinimumIdle(Integer.parseInt(minidle));
		hikariConfigDR.setPoolName(strpoolname);
		hikariConfigDR.setAutoCommit(true);
		hikariConfigDR.setConnectionTimeout(10000);

		hikariConfigDR.setLeakDetectionThreshold(THREE_MINS);

		try {
			System.out.println("DB Status : Primary Url Connecting....");
			dataSource = new HikariDataSource(hikariConfig);
			System.out.println("DB Status : Primary URL Successfully00000");
		}catch(Exception exception) {
			System.out.println("DB Status : Primary URL Failure");
			
			System.out.println("Primary URL : " + exception);
		
			System.out.println("DB Status : Secondary Url Connecting....");
			dataSource = new HikariDataSource(hikariConfigDR);
			System.out.println("DB Status : Secondary URL Success");
		}
		
		//dataSource = new HikariDataSource(hikariConfig);
		
		return dataSource;
	} 
	

	/*//@Bean(destroyMethod = "close")
	public HikariDataSource hikariConfigDR() {
		System.out.println("Tomcat path : "+System.getProperty("catalina.base"));
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
		String[] activeProfiles = env.getActiveProfiles();
		for(String activeprof:activeProfiles){
			System.out.println("Active Profile :"+activeprof);
		}

		String driver = env.getProperty("spring.datasource.driverClassName");
		String username = env.getProperty("spring.datasource.username");
		String dbpassword = env.getProperty("spring.datasource.password");
		String url = env.getProperty("spring.datasource.url.dr");
		String strpoolname = env.getProperty("spring.datasource.hikari.poolName");
		String maxpoolsize = env.getProperty("spring.datasource.hikari.maximumPoolSize");
		String minidle = env.getProperty("spring.datasource.hikari.minimumIdle");

		HikariConfig hikariConfig = new HikariConfig();
		hikariConfig.setDriverClassName(driver);
		hikariConfig.setJdbcUrl(url);
		hikariConfig.setUsername(username);
		hikariConfig.setPassword(decode(dbpassword));
		//hikariConfig.setPassword(dbpassword);

		hikariConfig.setMaximumPoolSize(Integer.parseInt(maxpoolsize));
		hikariConfig.setMinimumIdle(Integer.parseInt(minidle));
		hikariConfig.setPoolName(strpoolname);
		hikariConfig.setAutoCommit(true);
		hikariConfig.setConnectionTimeout(10000);

		// hikariConfig.setConnectionTestQuery("SELECT 1");
		// hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
		// hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
		// hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
		hikariConfig.setLeakDetectionThreshold(THREE_MINS);

		HikariDataSource dataSource = new HikariDataSource(hikariConfig);
		return dataSource;
	} */

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(hikariConfig());
		entityManagerFactoryBean.setJpaProperties(hibProperties());
		JpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
		entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);
		entityManagerFactoryBean.setPackagesToScan("com.api.ivr.db.entity");
		System.out.println(entityManagerFactoryBean.getDataSource().toString() +"DataSource Value -------");
		System.out.println(entityManagerFactoryBean.getJpaVendorAdapter() +"Jpa Adaptor ---------------");
		System.out.println(entityManagerFactoryBean.getJpaPropertyMap().toString() +"JPAPropertymap ----------------");
		// entityManagerFactoryBean.setPersistenceProvider(persistenceProvider);
		
		
		return entityManagerFactoryBean;
	}

	private Properties hibProperties() {
		Properties properties = new Properties();
		String dialect = env.getProperty("spring.datasource.database-platform");
		properties.put(PROPERTY_NAME_HIBERNATE_DIALECT, dialect);
		return properties;
	}

	@Bean
	public JpaTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
		return transactionManager;
	}

	public String decode(String password) {

		String decodedPassword = null;
		try {
			// Getting decoder
			Base64.Decoder decoder = Base64.getDecoder();
			// Decoding string
			decodedPassword = new String(decoder.decode(password));
			// System.out.println("Decoded string: " + decodedPassword);

		} catch (Exception e) {
			
		}
		return decodedPassword;
	}

}
