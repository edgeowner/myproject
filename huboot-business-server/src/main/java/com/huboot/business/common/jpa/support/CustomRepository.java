package com.huboot.business.common.jpa.support;

import org.hibernate.query.criteria.internal.predicate.ComparisonPredicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Deprecated
@NoRepositoryBean
public interface CustomRepository<T,ID extends Serializable> extends JpaRepository<T, ID> ,JpaSpecificationExecutor<T>{


	Page<T> findByAuto(T example, List<Map<String, ComparisonPredicate.ComparisonOperator>> operators, Pageable pageable);
	
	Page<T> findByAuto(T example, Pageable pageable);
	
	
}
