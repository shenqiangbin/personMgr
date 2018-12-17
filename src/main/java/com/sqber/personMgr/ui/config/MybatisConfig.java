package com.sqber.personMgr.ui.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@Configuration
@MapperScan(basePackages = { "com.sqber.personMgr.dal" }, sqlSessionFactoryRef = "sqlSessionFactoryKBase")
public class MybatisConfig {
	@Autowired
	@Qualifier("MySQLDS")
	private DataSource ds;

	@Value("${mybatis.mapperLocations}")
	private String mapperLocations;

	@Value("${mybatis.typeAliasesPackage}")
	private String typeAliasesPackage;

	@Bean
	public SqlSessionFactory sqlSessionFactoryKBase() throws Exception {
		SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
		Resource[] resources = new PathMatchingResourcePatternResolver().getResources(mapperLocations);
		factoryBean.setDataSource(ds);
		factoryBean.setTypeAliasesPackage(typeAliasesPackage);
		factoryBean.setMapperLocations(resources);
		return factoryBean.getObject();
	}

	@Bean
	public SqlSessionTemplate sqlSessionTemplateKBase() throws Exception {
		SqlSessionTemplate template = new SqlSessionTemplate(sqlSessionFactoryKBase()); // 使用上面配置的Factory
		return template;
	}
}
