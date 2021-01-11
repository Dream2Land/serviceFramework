package cn.xdaoy.common.jdbc;

import javax.sql.DataSource;

import org.apache.ibatis.io.VFS;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

import cn.xdaoy.common.bean.BaseBean;

@Configuration
@EnableTransactionManagement(proxyTargetClass = false)
public class ContextConfig {

	@Value("${mybatis.type-aliases-package}")
	String typeAliasesPackage;

	@Value("${mybatis.mapper-locations}")
	String mapperLocation;

	@Bean
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
		// set mybatis jar scan
		VFS.addImplClass(SpringBootVFS.class);

		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		sqlSessionFactoryBean.setTypeAliasesPackage(typeAliasesPackage);
		sqlSessionFactoryBean.setTypeAliasesSuperType(BaseBean.class);
		Resource[] resource = new PathMatchingResourcePatternResolver().getResources(mapperLocation);
		sqlSessionFactoryBean.setMapperLocations(resource);
		return sqlSessionFactoryBean.getObject();
	}

	@Bean(name = "dataSource")
	@Primary
	@ConfigurationProperties(prefix = "spring.datasource")
	public HikariDataSource dataSource() {
		return new EncryptPwdHikariDataSource();
	}

	@Primary
	@Bean
	public PlatformTransactionManager dataSourceTransactionManager(DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

}
