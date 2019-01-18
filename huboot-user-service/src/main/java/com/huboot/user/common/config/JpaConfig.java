package com.huboot.user.common.config;

import com.huboot.commons.jpa.DefaultBaseRepositoryFactoryBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
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
@EntityScan(basePackages = {"com.huboot.commons.jpa", "com.huboot.**.entity"})
public class JpaConfig {
}
