package com.huboot.business.common.jpa.support;

import org.hibernate.query.criteria.internal.predicate.ComparisonPredicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import static com.huboot.business.common.jpa.support.CustomerSpecs.byAuto;

@Deprecated
public class CustomRepositoryImpl<T,ID extends Serializable> extends SimpleJpaRepository<T, ID> implements CustomRepository<T, ID> {

	private final EntityManager entityManager;
	
	public CustomRepositoryImpl(Class<T> domainClass, EntityManager em) {
		super(domainClass, em);
		this.entityManager=em;
	}

	@Override
	public Page<T> findByAuto(T example, List<Map<String,ComparisonPredicate.ComparisonOperator>> operators, Pageable pageable) {
		return findAll(CustomerSpecs.byAuto(entityManager,operators, example),pageable);
	}
	
	@Override
	public Page<T> findByAuto(T example, Pageable pageable) {
		return findAll(CustomerSpecs.byAuto(entityManager, example),pageable);
	}

}
