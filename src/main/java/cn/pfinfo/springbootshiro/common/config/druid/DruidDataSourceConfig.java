package cn.pfinfo.springbootshiro.common.config.druid;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * Created by qianlong on 2016/12/21.
 */
@Configuration
@EnableConfigurationProperties(DruidSettings.class)
public class DruidDataSourceConfig {

    @Autowired
    private DruidSettings druidSettings;

    @Bean
    @ConfigurationProperties("spring.druid.datasource")
    public DruidDataSource dataSource(
            DataSourceProperties properties) throws Exception{
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(druidSettings.getDriverClassName());
        dataSource.setUrl(druidSettings.getUrl());
        dataSource.setUsername(druidSettings.getUsername());
        dataSource.setPassword(druidSettings.getPassword());
        dataSource.setInitialSize(druidSettings.getInitialSize());
        dataSource.setMinIdle(druidSettings.getMinIdle());
        dataSource.setMaxActive(druidSettings.getMaxActive());
        dataSource.setMaxWait(druidSettings.getMaxWait());
        dataSource.setTimeBetweenEvictionRunsMillis(druidSettings.getTimeBetweenEvictionRunsMillis());
        dataSource.setMinEvictableIdleTimeMillis(druidSettings.getMinEvictableIdleTimeMillis());
        String validationQuery = druidSettings.getValidationQuery();
        if (validationQuery != null && !"".equals(validationQuery)) {
            dataSource.setValidationQuery(validationQuery);
        }
        dataSource.setTestWhileIdle(druidSettings.isTestWhileIdle());
        dataSource.setTestOnBorrow(druidSettings.isTestOnBorrow());
        dataSource.setTestOnReturn(druidSettings.isTestOnReturn());
        if(druidSettings.isPoolPreparedStatements()){
            dataSource.setMaxPoolPreparedStatementPerConnectionSize(druidSettings.getMaxPoolPreparedStatementPerConnectionSize());
        }
        dataSource.setFilters(druidSettings.getFilters());//这是最关键的,否则SQL监控无法生效
        String connectionPropertiesStr = druidSettings.getConnectionProperties();
        if(connectionPropertiesStr != null && !"".equals(connectionPropertiesStr)){
            Properties connectProperties = new Properties();
            String[] propertiesList = connectionPropertiesStr.split(";");
            for(String propertiesTmp:propertiesList){
                String[] obj = propertiesTmp.split("=");
                String key = obj[0];
                String value = obj[1];
                connectProperties.put(key,value);
            }
            dataSource.setConnectProperties(connectProperties);
        }
        dataSource.setUseGlobalDataSourceStat(druidSettings.isUseGlobalDataSourceStat());

        return dataSource;
    }

    /**
     * 注册一个StatViewServlet
     */
    @Bean
    public ServletRegistrationBean druidStatViewServlet(){
        //org.springframework.boot.context.embedded.ServletRegistrationBean提供类的进行注册.
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(),"/druid/*");

        //添加初始化参数：initParams
        //白名单：
        servletRegistrationBean.addInitParameter("allow","127.0.0.1");
        //IP黑名单 (存在共同时，deny优先于allow) : 如果满足deny的话提示:Sorry, you are not permitted to view this page.
        servletRegistrationBean.addInitParameter("deny","192.168.1.73");
        //登录查看信息的账号密码.
        servletRegistrationBean.addInitParameter("loginUsername","root");
        servletRegistrationBean.addInitParameter("loginPassword","password");
        //是否能够重置数据.
        servletRegistrationBean.addInitParameter("resetEnable","false");// 禁用HTML页面上的“Reset All”功能
        return servletRegistrationBean;
    }

    /**
     * 注册一个：filterRegistrationBean
     */
    @Bean
    public FilterRegistrationBean druidStatFilter(){

        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
        filterRegistrationBean.setName("druidWebStatFilter");
        //添加过滤规则.
        filterRegistrationBean.addUrlPatterns("/*");
        //添加忽略的格式信息.
        filterRegistrationBean.addInitParameter("exclusions","*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }
}
