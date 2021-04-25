package io.kang.provider.config

import com.alibaba.druid.pool.DruidDataSource;
import io.seata.rm.datasource.DataSourceProxy;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import java.io.IOException
import javax.sql.DataSource

@Configuration
@EnableConfigurationProperties(MybatisProperties::class)
class DataSourceConfiguration {
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    fun dataSource(): DataSource {
        return DruidDataSource()
    }

    @Bean
    @Primary
    fun dataSourceProxy(dataSource: DataSource): DataSourceProxy {
        return DataSourceProxy(dataSource)
    }

    @Bean
    fun sqlSessionFactoryBean(dataSourceProxy: DataSourceProxy,
                              mybatisProperties: MybatisProperties): SqlSessionFactoryBean {
        val bean = SqlSessionFactoryBean()
        bean.setDataSource(dataSourceProxy)

        val resolver = PathMatchingResourcePatternResolver()
        try {
            val locations = resolver.getResources(mybatisProperties.mapperLocations[0])
            bean.setMapperLocations(*locations)

            if (StringUtils.isNotBlank(mybatisProperties.configLocation)) {
                val resources = resolver.getResources(mybatisProperties.configLocation)
                bean.setConfigLocation(resources[0])
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return bean
    }
}