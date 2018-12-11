package com.huboot.business.common.jpa;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huboot.business.base_model.login.sso.client.component.SsoComponent;
import com.huboot.business.common.component.exception.BizException;
import com.huboot.business.common.component.exception.MethodCanNotUseException;
import com.huboot.business.common.jpa.datarecycle.entity.SystemDataRecycleEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.convert.QueryByExamplePredicateBuilder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.EntityType;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

import static org.springframework.data.jpa.repository.query.QueryUtils.toOrders;

/**
 * Created by Administrator on 2018/1/8 0008.
 */
@Transactional(readOnly = true)
public class DefaultBaseRepository<Entity extends AbstractEntity> extends SimpleJpaRepository<Entity, Integer> implements IBaseRepository<Entity> {

    protected Logger logger = LoggerFactory.getLogger(DefaultBaseRepository.class);

    private final EntityManager entityManager;
    private ObjectMapper objectMapper;

    public DefaultBaseRepository(Class<Entity> domainClass, EntityManager entityManager) {
        super(domainClass, entityManager);
        this.entityManager = entityManager;
    }

    public DefaultBaseRepository(Class<Entity> domainClass, EntityManager entityManager, ObjectMapper objectMapper) {
        this(domainClass, entityManager);
        this.objectMapper = objectMapper;
    }

    @Override
    public Entity find(Integer id) {
        Optional<Entity> optional = findById(id);
        return optional.orElse(null);
    }

    @Override
    public List<Entity> findByBeanProp(Entity entity) {
        Example<Entity> ex = Example.of(entity);
        return findAll(ex);
    }

    @Override
    public long findCountByBeanProp(Entity entity) {
        Example<Entity> ex = Example.of(entity);
        return count(ex);
    }

    @Override
    public List<Entity> findByBeanPropWithSort(Entity entity, Sort sort) {
        Example<Entity> ex = Example.of(entity);
        return findAll(ex, sort);
    }

    @Override
    public List<Entity> findByBeanPropWithLimit(Entity entity, Integer limit) {
        return findByBeanPropWithSortAndLimit(entity, null, limit);
    }

    @Override
    public List<Entity> findByBeanPropWithSortAndLimit(Entity entity, Sort sort, Integer limit) {
        if(limit == null) {
            throw new BizException("限制条数limit不能为空");
        }
        Example<Entity> example = Example.of(entity);
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Entity> query = builder.createQuery(example.getProbeType());
        Root<Entity> root = query.from(example.getProbeType());
        query.where(QueryByExamplePredicateBuilder.getPredicate(root, builder, example));
        query.select(root);
        if(sort != null && sort.isSorted()) {
            query.orderBy(toOrders(sort, root, builder));
        }
        return entityManager.createQuery(query).setMaxResults(limit).getResultList();
    }

    @Override
    public List<Entity> findByCondition(QueryCondition condition) {
        if(condition.getSize() == 0) {
            condition.limit(50);
        }
        Specification<Entity> specification = getSpecification(condition);
        Pageable pageable;
        if(condition.getSort() != null) {
            pageable = PageRequest.of(0, condition.getSize(), condition.getSort());
        } else {
            pageable = PageRequest.of(0, condition.getSize());
        }
        Page<Entity> page = findAll(specification, pageable);
        return page.getContent();
    }

    private Specification<Entity> getSpecification(QueryCondition condition) {
        Class<Entity> clazz = (Class<Entity>)condition.getClazz();
        EntityType<Entity> entityType = entityManager.getMetamodel().entity(clazz);
        return new Specification<Entity>() {
            @Override
            public Predicate toPredicate(Root<Entity> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Predicate predicate = builder.conjunction();
                for(ConditionMap cond : condition.getConditionMapList()) {
                    Attribute<?, ?> attribute = null;
                    try {
                        attribute = entityType.getDeclaredAttribute(cond.getField());
                    } catch (Exception e) {
                        attribute = entityType.getSupertype().getDeclaredAttribute(cond.getField());
                    }
                    switch (cond.getSignEnum()) {
                        case eq: predicate.getExpressions().add(builder.equal(root.get(cond.getField()).as(attribute.getJavaType()), cond.getValue())); break;
                        case lt: predicate.getExpressions().add(builder.lessThan(root.get(cond.getField()), (Comparable)cond.getValue())); break;
                        case lte: predicate.getExpressions().add(builder.lessThanOrEqualTo(root.get(cond.getField()), (Comparable)cond.getValue())); break;
                        case gt: predicate.getExpressions().add(builder.greaterThan(root.get(cond.getField()), (Comparable)cond.getValue())); break;
                        case gte: predicate.getExpressions().add(builder.greaterThanOrEqualTo(root.get(cond.getField()), (Comparable)cond.getValue()));break;
                        case ne: predicate.getExpressions().add(builder.notEqual(root.get(cond.getField()), cond.getValue())); break;
                        case is_not_null: predicate.getExpressions().add(builder.isNotNull(root.get(cond.getField()))); break;
                        case is_null: predicate.getExpressions().add(builder.isNull(root.get(cond.getField()))); break;
                        case in: {
                            Object[] objects = (Object[])cond.getValue();
                            predicate.getExpressions().add(root.get(cond.getField()).as(attribute.getJavaType()).in(objects));
                            break;
                        }
                        case like: predicate.getExpressions().add(builder.like(root.get(cond.getField()).as(String.class), "%" + cond.getValue().toString() + "%")); break;
                        default: throw new BizException("数据库操作符不存在: sign=" + cond.getSignEnum().getDbOp());
                    }
                }
                return predicate;
            }
        };
    }

    @Override
    public Page<Entity> findPage(QueryCondition condition) {
        Integer index = condition.getPage() - 1 < 0 ? 0 : condition.getPage() - 1;
        Specification<Entity> specification = getSpecification(condition);
        Pageable pageable;
        if(condition.getSort() != null) {
            pageable = PageRequest.of(index, condition.getSize(), condition.getSort());
        } else {
            pageable = PageRequest.of(index, condition.getSize());
        }

        Page<Entity> page = findAll(specification, pageable);
        return page;
    }

    @Transactional
    @Override
    public Entity create(Entity entity) {
        if(StringUtils.isEmpty(entity.getCreatorId())) {
            entity.setCreatorId(SsoComponent.getCurrentBid() == null ? "" : SsoComponent.getCurrentBid());
        }
        if(StringUtils.isEmpty(entity.getModifierId())) {
            entity.setModifierId(SsoComponent.getCurrentBid() == null ? "" : SsoComponent.getCurrentBid());
        }
        if(StringUtils.isEmpty(entity.getCreateTime())) {
            entity.setCreateTime(LocalDateTime.now());
        }
        if(StringUtils.isEmpty(entity.getUpdateTime())) {
            entity.setUpdateTime(LocalDateTime.now());
        }
        super.save(entity);
        return entity;
    }

    @Transactional
    @Override
    public Entity createAndFlush(Entity entity) {
        create(entity);
        flush();
        return entity;
    }

    @Transactional
    @Override
    public List<Entity> create(List<Entity> entities) {
        if(CollectionUtils.isEmpty(entities)) {
            if(logger.isWarnEnabled()) {
                logger.warn("创建数据集合为空");
            }
            return new ArrayList<>();
        }
        for(Entity entity : entities) {
            create(entity);
        }
        return entities;
    }

    @Transactional
    @Override
    public List<Entity> createAndFlush(List<Entity> entities) {
        create(entities);
        flush();
        return entities;
    }

    @Transactional
    @Override
    public Entity update(Entity entity) {
        entity.setModifierId(SsoComponent.getCurrentBid() == null ? "" : SsoComponent.getCurrentBid());
        entity.setUpdateTime(LocalDateTime.now());
        return super.save(entity);
    }

    @Transactional
    @Override
    public Entity updateAndFlush(Entity entity) {
        update(entity);
        flush();
        return entity;
    }

    @Transactional
    @Override
    public List<Entity> update(List<Entity> entities) {
        if(CollectionUtils.isEmpty(entities)) {
            if(logger.isWarnEnabled()) {
                logger.warn("更新数据集合为空");
            }
            return new ArrayList<>();
        }
        for(Entity entity : entities) {
            update(entity);
        }
        return entities;
    }

    @Transactional
    @Override
    public List<Entity> updateAndFlush(List<Entity> entities) {
        update(entities);
        flush();
        return entities;
    }

    @Override
    public <S extends Entity> S save(S entity) {
        throw new MethodCanNotUseException("请使用create或者update方法");
    }

    @Override
    public <S extends Entity> S saveAndFlush(S entity) {
        throw new MethodCanNotUseException("请使用createAndFlush或者updateAndFlush方法");
    }

    @Override
    public <S extends Entity> List<S> saveAll(Iterable<S> entities) {
        throw new MethodCanNotUseException("请使用create或者update方法");
    }

    @Transactional
    @Override
    public Entity remove(Integer id) throws IOException{
        Entity entity = find(id);
        if(entity != null) {
            List<Entity> entities = Arrays.asList(entity);
            removeDataStrategy(entities);
            super.delete(entity);
        }
        return entity;
    }

    private void removeDataStrategy(List<Entity> entities) throws IOException{
        if(CollectionUtils.isEmpty(entities)) {
            return;
        }
        SystemDataRecycleEntity systemDataRecycleEntity = new SystemDataRecycleEntity();
        systemDataRecycleEntity.setCreatorId(SsoComponent.getCurrentBid());
        systemDataRecycleEntity.setModifierId(SsoComponent.getCurrentBid());
        systemDataRecycleEntity.setEntity(entities.get(0).getClass().getName());
        systemDataRecycleEntity.setContent(objectMapper.writeValueAsString(entities));
        systemDataRecycleEntity.setCreateTime(LocalDateTime.now());
        systemDataRecycleEntity.setUpdateTime(LocalDateTime.now());
        entityManager.persist(systemDataRecycleEntity);
    }

    @Transactional
    @Override
    public void remove(List<Entity> entities) throws IOException{
        removeDataStrategy(entities);
        super.deleteInBatch(entities);
    }

    @Override
    public void deleteById(Integer aLong) {
        throw new MethodCanNotUseException("请使用remove方法");
    }

    @Override
    public void delete(Entity entity) {
        throw new MethodCanNotUseException("请使用remove方法");
    }

    @Override
    public void deleteAll(Iterable<? extends Entity> entities) {
        throw new MethodCanNotUseException("请使用remove方法");
    }

    @Override
    public void deleteInBatch(Iterable<Entity> entities) {
        throw new MethodCanNotUseException("请使用remove方法");
    }

    @Override
    public void deleteAll() {
        throw new MethodCanNotUseException("请使用remove方法");
    }

    @Override
    public void deleteAllInBatch() {
        throw new MethodCanNotUseException("请使用remove方法");
    }
}
