package com.huboot.business.common.jpa;

import com.huboot.business.base_model.login.sso.client.config.SsoConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

import javax.persistence.EntityManager;

public class DefaultBaseRepositoryFactoryBean<Repository extends JpaRepository<Entity, Integer>, Entity> extends JpaRepositoryFactoryBean<Repository, Entity, Integer>{

	 public DefaultBaseRepositoryFactoryBean(Class<? extends Repository> repositoryInterface){
		 super(repositoryInterface);
	 }

	@SuppressWarnings("rawtypes")
	@Override
	protected RepositoryFactorySupport createRepositoryFactory(EntityManager entityManager) {
		return new DefaultBaseRepositoryFactory(entityManager);
	}
	
	private static class DefaultBaseRepositoryFactory extends JpaRepositoryFactory{

		public DefaultBaseRepositoryFactory(EntityManager entityManager) {
			super(entityManager);
		}

		@SuppressWarnings("unchecked")
		@Override
		protected SimpleJpaRepository getTargetRepository(RepositoryInformation information, EntityManager entityManager) {
			SsoConfig ssoConfig = new SsoConfig();
			return new DefaultBaseRepository(information.getDomainType(), entityManager, ssoConfig.objectMapper());
		}

		@Override
		protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
			return DefaultBaseRepository.class;
		}

	}
	
}
