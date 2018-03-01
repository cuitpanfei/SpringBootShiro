package cn.pfinfo.springbootshiro.configuration.druid;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Created by qianlong on 2016/12/21.
 */
//@ConfigurationProperties(prefix = "spring.druid.datasource",locations = "classpath:druid-config.properties")
@Component
@ConfigurationProperties(prefix = "spring.druid.datasource")
@PropertySource("classpath:druid-config.properties")
@Setter
@Getter
public class DruidSettings {

    private String type;
    private String driverClassName;
    private String url;
    private String username;
    private String password;

    private Integer initialSize;
    private Integer minIdle;
    private Integer maxActive;
    private Long maxWait;
    private Long timeBetweenEvictionRunsMillis;
    private Long minEvictableIdleTimeMillis;
    private String validationQuery;
    private boolean testWhileIdle;
    private boolean testOnBorrow;
    private boolean testOnReturn;
    private boolean poolPreparedStatements;
    private Integer maxPoolPreparedStatementPerConnectionSize;
    private String filters;
    private String connectionProperties;
    private boolean useGlobalDataSourceStat;


}
