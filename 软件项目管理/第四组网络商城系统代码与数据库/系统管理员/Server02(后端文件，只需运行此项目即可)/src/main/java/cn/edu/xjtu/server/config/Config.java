package cn.edu.xjtu.server.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.thetransactioncompany.cors.CORSFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Arrays;

@Configuration
@EnableTransactionManagement
public class Config {
    @Bean
    public FilterRegistrationBean myFilter(){
        CORSFilter corsFilter = new CORSFilter();
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(corsFilter);
        filterRegistrationBean.setUrlPatterns(Arrays.asList("/*"));
        return filterRegistrationBean;
    }
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) throws Exception{
        //Spring对@Configuration类会特殊处理；给容器中加组件的方法，多次调用都只是从容器中找组件
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return jdbcTemplate;
    }

    //注册事务管理器在容器中
    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) throws Exception{
        return new DataSourceTransactionManager(dataSource);
    }
}
