package com.huboot.business.common.jpa.support;

import org.hibernate.query.criteria.internal.CriteriaBuilderImpl;
import org.hibernate.query.criteria.internal.predicate.ComparisonPredicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.SingularAttribute;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Deprecated
public class CustomerSpecs {
	
	public static <T> Specification<T> byAuto(final EntityManager entityManager,final T example){
		
		@SuppressWarnings("unchecked")
		final Class<T> type=(Class<T>) example.getClass();
		
		return new Specification<T>(){

			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query,CriteriaBuilder cb) {
				List<Predicate> predicates=new ArrayList<Predicate>();
				EntityType<T> entity=entityManager.getMetamodel().entity(type);
				for(Attribute<T,?> attr : entity.getDeclaredAttributes()){
					Object attrValue=getValue(example,attr);
					if(attrValue !=null){
						if(attr.getJavaType() == String.class){
							if(!StringUtils.isEmpty(attrValue)){
								predicates.add(cb.like(root.get(attribute(entity,attr.getName(),String.class)),pattern((String)attrValue)));
							}
						}else{
							predicates.add(cb.equal(root.get(attribute(entity,attr.getName(),attrValue.getClass())),attrValue));
						}
					}
				}
				return predicates.isEmpty()?cb.conjunction():cb.and(toArray(predicates));
			}
			
		};
	}
	
	public static <T> Specification<T> byAuto(final EntityManager entityManager, final List<Map<String,ComparisonPredicate.ComparisonOperator>> operators, final T example){
		
		@SuppressWarnings("unchecked")
		final Class<T> type=(Class<T>) example.getClass();
		
		return new Specification<T>(){

			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query,CriteriaBuilder cb) {
				List<Predicate> predicates=new ArrayList<Predicate>();
				EntityType<T> entity=entityManager.getMetamodel().entity(type);
				for(Attribute<T,?> attr : entity.getDeclaredAttributes()){
					Object attrValue=getValue(example,attr);
					if(attrValue !=null){
						SingularAttribute<T, ? extends Object> attribute=attribute(entity,attr.getName(),attrValue.getClass());
						List<Map<String,ComparisonPredicate.ComparisonOperator>> os=operators.stream().filter(s->s.containsKey(attribute.getName())).collect(Collectors.toList());
						if(!os.isEmpty()){
							os.forEach(s->{
								ComparisonPredicate.ComparisonOperator comparisonOperator=s.get(attribute.getName());
								predicates.add(new ComparisonPredicate( (CriteriaBuilderImpl) cb, comparisonOperator, root.get(attribute(entity,attr.getName(),attrValue.getClass())), attrValue ));
							});
						}
					}
				}
				return predicates.isEmpty()?cb.conjunction():cb.and(toArray(predicates));
			}
			
		};
	}
	
	
	
	public static Predicate[] toArray(List<Predicate> predicates){
		int size=predicates.size();
		Predicate[] arr = (Predicate[])predicates.toArray(new Predicate[size]);
		return arr;
	}
	
	private static <T> Object getValue(T example,Attribute<T,?> attr){
		return ReflectionUtils.getField((Field) attr.getJavaMember(), example);
	}
	
	private static <E,T> SingularAttribute<T,E> attribute(EntityType<T> entity,String fieldName,Class<E> fieldClass){
		return entity.getDeclaredSingularAttribute(fieldName,fieldClass);
	}
	
	private static String pattern(String str){
		return "%" + str + "%";
	}
	
	

}
