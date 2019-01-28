package com.huboot.file.common.config;

import com.huboot.commons.jpa.DefaultBaseRepositoryFactoryBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by Administrator on 2018/1/8 0008.
 */
@Configuration
@EnableJpaRepositories(
        repositoryFactoryBeanClass = DefaultBaseRepositoryFactoryBean.class,
        basePackages = "com.huboot.**.repository"
)
public class JpaConfig {
}
