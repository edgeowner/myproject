package com.huboot.business.common.config;

import com.huboot.business.common.jpa.DefaultBaseRepositoryFactoryBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by Administrator on 2018/1/8 0008.
 */
@Configuration
@EnableJpaRepositories(
        repositoryFactoryBeanClass = DefaultBaseRepositoryFactoryBean.class,
        basePackages="com.huboot.business.**.repository"
)
public class JpaConfig {
}
