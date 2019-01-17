package com.huboot.commons.jpa;

import com.huboot.commons.utils.HibernateToolsUtil;
import com.huboot.commons.component.exception.BizException;
import com.huboot.commons.filter.RequestInfo;
import com.huboot.commons.utils.keyGenerator.PrimaryKeyGenerator;
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
import javax.persistence.criteria.*;
import javax.persistence.metamodel.EntityType;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.repository.query.QueryUtils.toOrders;

/**
 * Created by Administrator on 2018/1/8 0008.
 */
@Transactional(readOnly = true)
public class DefaultBaseRepository<Entity extends AbstractEntity> extends SimpleJpaRepository<Entity, Long> implements IBaseRepository<Entity> {

    private static final Long SYSTEM_USER_ID = 0L;//系统用户id

    protected Logger logger = LoggerFactory.getLogger(DefaultBaseRepository.class);

    private final EntityManager entityManager;

    public DefaultBaseRepository(Class<Entity> domainClass, EntityManager entityManager) {
        super(domainClass, entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public Entity find(Long id) {
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
        if (limit == null) {
            throw new BizException("限制条数limit不能为空");
        }
        Example<Entity> example = Example.of(entity);
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Entity> query = builder.createQuery(example.getProbeType());
        Root<Entity> root = query.from(example.getProbeType());
        query.where(QueryByExamplePredicateBuilder.getPredicate(root, builder, example));
        query.select(root);
        if (sort != null && sort.isSorted()) {
            query.orderBy(toOrders(sort, root, builder));
        }
        return entityManager.createQuery(query).setMaxResults(limit).getResultList();
    }

    @Override
    public List<Entity> findByCondition(QueryCondition condition) {
        if (condition.getSize() == 0) {
            condition.limit(50);
        }
        Specification<Entity> specification = getSpecification(condition);
        Pageable pageable;
        if (condition.getSort() != null) {
            pageable = PageRequest.of(0, condition.getSize(), condition.getSort());
        } else {
            pageable = PageRequest.of(0, condition.getSize());
        }
        Page<Entity> page = findAll(specification, pageable);
        return page.getContent();
    }

    private Specification<Entity> getSpecification(QueryCondition condition) {
        Class<Entity> clazz = (Class<Entity>) condition.getClazz();
        EntityType<Entity> entityType = entityManager.getMetamodel().entity(clazz);
        //https://blog.csdn.net/han1196639488/article/details/54909100/
        //https://blog.csdn.net/qq587492/article/details/81629689
        return new Specification<Entity>() {
            @Override
            public Predicate toPredicate(Root<Entity> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                List<Predicate> predicateList = convertToPredicateList(condition.getConditionMapList(), root, builder, entityType);
                Predicate[] p = new Predicate[predicateList.size()];
                query.where(builder.and(predicateList.toArray(p)));
                return query.getRestriction();
//                Predicate predicate = builder.conjunction();
            }
        };
    }

    private List<Predicate> convertToPredicateList(List<ConditionMap> conditionMapList, Root<Entity> root, CriteriaBuilder builder, EntityType<Entity> entityType) {
        List<Predicate> predicateList = new ArrayList<>();
        for (ConditionMap cond : conditionMapList) {
            if (StringUtils.isEmpty(cond.getField())) {
                continue;
            }
            String[] fieldArray = cond.getField().split("\\" + JoinSignEnum.join.getSplitSign());

            //单表查询
            if (fieldArray.length == 1) {
//                Attribute<?, ?> attribute = entityType.getDeclaredAttribute(cond.getField());
                switch (cond.getSignEnum()) {
                    case eq:
                        predicateList.add(builder.equal(root.get(cond.getField()).as(String.class), cond.getValue()));
//                                predicate.getExpressions().add(builder.equal(root.get(cond.getField()).as(attribute.getJavaType()), cond.getValue()));
                        break;
                    case lt:
                        predicateList.add(builder.lessThan(root.get(cond.getField()), (Comparable) cond.getValue()));
//                                predicate.getExpressions().add(builder.lessThan(root.get(cond.getField()), (Comparable) cond.getValue()));
                        break;
                    case lte:
                        predicateList.add(builder.lessThanOrEqualTo(root.get(cond.getField()), (Comparable) cond.getValue()));
//                                predicate.getExpressions().add(builder.lessThanOrEqualTo(root.get(cond.getField()), (Comparable) cond.getValue()));
                        break;
                    case gt:
                        predicateList.add(builder.greaterThan(root.get(cond.getField()), (Comparable) cond.getValue()));
//                                predicate.getExpressions().add(builder.greaterThan(root.get(cond.getField()), (Comparable) cond.getValue()));
                        break;
                    case gte:
                        predicateList.add(builder.greaterThanOrEqualTo(root.get(cond.getField()), (Comparable) cond.getValue()));
//                                predicate.getExpressions().add(builder.greaterThanOrEqualTo(root.get(cond.getField()), (Comparable) cond.getValue()));
                        break;
                    case ne:
                        predicateList.add(builder.notEqual(root.get(cond.getField()).as(String.class), cond.getValue()));
//                                predicate.getExpressions().add(builder.notEqual(root.get(cond.getField()), cond.getValue()));
                        break;
                    case in: {
                        if (cond.getValue() instanceof List) {
                            predicateList.add(builder.and(root.get(cond.getField()).as(String.class).in(((List) cond.getValue()).toArray())));
                        }
//                                Object[] objects = (Object[]) cond.getValue();
//                                root.get(cond.getField()).as(attribute.getJavaType()).in(objects);
//                                predicate.getExpressions().add(root.get(cond.getField()).as(attribute.getJavaType()).in(objects));
                        break;
                    }
                    case like:
                        predicateList.add(builder.like(root.get(cond.getField()).as(String.class), "%" + cond.getValue().toString() + "%"));
//                        predicate.getExpressions().add(builder.like(root.get(cond.getField()).as(String.class), "%" + cond.getValue().toString() + "%"));
                        break;
                    case or:
                        if (cond.getValue() instanceof List) {
                            List<ConditionMap> conditionMapList1 = (List<ConditionMap>) cond.getValue();
                            List<Predicate> predicateList1 = this.convertToPredicateList(conditionMapList1, root, builder, entityType);
                            Predicate[] p = new Predicate[predicateList1.size()];
                            predicateList.add(builder.or(predicateList1.toArray(p)));
                        }
                        break;
                    default:
                        throw new BizException("数据库操作符不存在: sign=" + cond.getSignEnum().getDbOp());
                }
            } else {
                //多表关联查询 https://www.cnblogs.com/dreamroute/p/5173896.html
                //属性分割-》判断关联方式
                Join<Entity, ?> join = null;
                Path path = null;
                //取出第一个用root join-》join -》最后一个是属性
                for (int i = 0; i < fieldArray.length; i++) {
                    JoinType joinType = JoinType.INNER;
                    //判断+的位置
                    String joinSign = fieldArray[i].substring(0, 1);
                    if (joinSign.equals(JoinSignEnum.right_join.getSplitSign())) {
                        fieldArray[i] = fieldArray[i].replace(joinSign,"");
                        joinType = JoinType.RIGHT;
                    } else if (joinSign.equals(JoinSignEnum.left_join.getSplitSign())) {
                        fieldArray[i] = fieldArray[i].replace(joinSign,"");
                        joinType = JoinType.LEFT;
                    }
                    if (i == 0) {
                        join = root.join(fieldArray[i], joinType);
                    } else if (i == fieldArray.length - 1) {
                        path = join.get(fieldArray[i]);
                    } else {
                        join.join(fieldArray[i], joinType);
                    }
                }
//                Attribute<?, ?> attribute = entityType.getDeclaredAttribute(fieldArray[0]);
                switch (cond.getSignEnum()) {
                    case eq:
                        predicateList.add(builder.equal(path.as(String.class), cond.getValue()));
                        break;
                    case lt:
                        predicateList.add(builder.lessThan(path.as(String.class), (Comparable) cond.getValue()));
                        break;
                    case lte:
                        predicateList.add(builder.lessThanOrEqualTo(path.as(String.class), (Comparable) cond.getValue()));
                        break;
                    case gt:
                        predicateList.add(builder.greaterThan(path.as(String.class), (Comparable) cond.getValue()));
                        break;
                    case gte:
                        predicateList.add(builder.greaterThanOrEqualTo(path.as(String.class), (Comparable) cond.getValue()));
                        break;
                    case ne:
                        predicateList.add(builder.notEqual(path.as(String.class), cond.getValue()));
                        break;
                    case in: {
                        if (cond.getValue() instanceof List) {
                            predicateList.add(builder.and(path.as(String.class).in(((List) cond.getValue()).toArray())));
                        }
                        break;
                    }
                    case like:
                        predicateList.add(builder.like(path.as(String.class), "%" + cond.getValue().toString() + "%"));
                        break;
                    case or:
                        if (cond.getValue() instanceof List) {
                            List<ConditionMap> conditionMapList1 = (List<ConditionMap>) cond.getValue();
                            List<Predicate> predicateList1 = this.convertToPredicateList(conditionMapList1, root, builder, entityType);
                            Predicate[] p = new Predicate[predicateList1.size()];
                            predicateList.add(builder.or(predicateList1.toArray(p)));
                        }
                        break;
                    default:
                        throw new BizException("数据库操作符不存在: sign=" + cond.getSignEnum().getDbOp());
                }
            }
        }
        return predicateList;
    }


    @Override
    public Page<Entity> findPage(QueryCondition condition) {
        Integer index = condition.getPage() - 1 < 0 ? 0 : condition.getPage() - 1;
        Specification<Entity> specification = getSpecification(condition);
        Pageable pageable;
        if (condition.getSort() != null) {
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
        //判断留着，数据迁移的时候，用得上
        if (StringUtils.isEmpty(entity.getId())) {
            entity.setId(PrimaryKeyGenerator.SEQUENCE.next());
        }
        Long userId = SYSTEM_USER_ID;
        try {
            userId = Long.valueOf(RequestInfo.getJwtUser().getUserId());
        }catch (Exception e){

        }
        if (StringUtils.isEmpty(entity.getCreatorId())) {
            entity.setCreatorId(userId);
        }
        if (StringUtils.isEmpty(entity.getModifierId())) {
            entity.setModifierId(userId);
        }
        if (StringUtils.isEmpty(entity.getCreateTime())) {
            entity.setCreateTime(LocalDateTime.now());
        }
        if (StringUtils.isEmpty(entity.getModifyTime())) {
            entity.setModifyTime(LocalDateTime.now());
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
        if (CollectionUtils.isEmpty(entities)) {
            if (logger.isWarnEnabled()) {
                logger.warn("创建数据集合为空");
            }
            return new ArrayList<>();
        }
        for (Entity entity : entities) {
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
    public Entity modify(Entity entity) {
        Long userId = SYSTEM_USER_ID;
        try {
             userId = Long.valueOf(RequestInfo.getJwtUser().getUserId());
        }catch (Exception e){

        }
        entity.setModifierId(userId);
        entity.setModifyTime(LocalDateTime.now());
        return super.save(entity);
    }

    @Transactional
    @Override
    public Entity modifyAndFlush(Entity entity) {
        modify(entity);
        flush();
        return entity;
    }

    @Transactional
    @Override
    public List<Entity> modify(List<Entity> entities) {
        if (CollectionUtils.isEmpty(entities)) {
            if (logger.isWarnEnabled()) {
                logger.warn("更新数据集合为空");
            }
            return new ArrayList<>();
        }
        for (Entity entity : entities) {
            modify(entity);
        }
        return entities;
    }

    @Transactional
    @Override
    public List<Entity> modifyAndFlush(List<Entity> entities) {
        modify(entities);
        flush();
        return entities;
    }

    @Override
    public <S extends Entity> S save(S entity) {
        throw new MethodCanNotUseException("请使用create或者modify方法");
    }

    @Override
    public <S extends Entity> S saveAndFlush(S entity) {
        throw new MethodCanNotUseException("请使用createAndFlush或者modifyAndFlush方法");
    }

    @Override
    public <S extends Entity> List<S> saveAll(Iterable<S> entities) {
        throw new MethodCanNotUseException("请使用create或者modify方法");
    }

    @Transactional
    @Override
    public Entity remove(Long id) {
        Entity entity = find(id);
        if (entity != null) {
            List<Entity> entities = Arrays.asList(entity);
            removeDataStrategy(entities);
            super.delete(entity);
        }
        return entity;
    }

    private void removeDataStrategy(List<Entity> entities) {
        if (CollectionUtils.isEmpty(entities)) {
            return;
        }
        SystemDataRecycleEntity entity = new SystemDataRecycleEntity();
        Long userId = SYSTEM_USER_ID;
        try {
            userId = Long.valueOf(RequestInfo.getJwtUser().getUserId());
        }catch (Exception e){

        }
        entity.setId(PrimaryKeyGenerator.SEQUENCE.next());
        entity.setCreatorId(userId);
        entity.setModifierId(userId);
        entity.setTableName(HibernateToolsUtil.getTableName(entities.get(0).getClass()));
        entity.setContent(entities);
        entity.setCreateTime(LocalDateTime.now());
        entity.setModifyTime(LocalDateTime.now());
        entityManager.persist(entity);
    }

    @Transactional
    @Override
    public void remove(List<Entity> entities) {
        removeDataStrategy(entities);
        super.deleteInBatch(entities);
    }

    @Override
    public void deleteById(Long aLong) {
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
