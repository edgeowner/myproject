package com.huboot.commons.jpa;

import com.huboot.commons.filter.RequestInfo;
import com.huboot.commons.utils.HibernateToolsUtil;
import com.huboot.commons.component.exception.BizException;
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
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.EntityType;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.*;

import static com.huboot.commons.sso.SSOConstant.SYSTEM_USER_ID;
import static org.springframework.data.jpa.repository.query.QueryUtils.toOrders;

/**
 * Created by Administrator on 2018/1/8 0008.
 */
@Transactional(readOnly = true)
public class DefaultBaseRepository1<Entity extends AbstractEntity> extends SimpleJpaRepository<Entity, Long> implements IBaseRepository<Entity> {


    protected Logger logger = LoggerFactory.getLogger(DefaultBaseRepository1.class);

    private final EntityManager entityManager;

    public DefaultBaseRepository1(Class<Entity> domainClass, EntityManager entityManager) {
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
            condition.limit(Integer.MAX_VALUE);
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
        List<String> innerList = condition.getInnerList();
        List<String> leftOuterList = condition.getLeftOuterList();
        return new Specification<Entity>() {
            @Override
            public Predicate toPredicate(Root<Entity> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Predicate predicate = builder.conjunction();
                for (ConditionMap cond : condition.getConditionMapList()) {
                    if (isJoin(innerList, cond.getField())) {
                        joinPredicate(cond, JoinType.INNER, clazz, root, predicate, builder,null);
                    } else if (isJoin(leftOuterList, cond.getField())) {
                        joinPredicate(cond, JoinType.LEFT, clazz, root, predicate, builder,null);
                    } else {
                        onlyOnePredicate(cond, predicate, root, builder, entityType);
                    }
                }
                return predicate;
            }
        };
    }

    private boolean isJoin(List<String> joinList, String field) {
        for (String join : joinList) {
            if (field.startsWith(join)) {
                String joinField = field.split("\\.")[1];
                if (StringUtils.isEmpty(joinField)) {
                    throw new BizException("字段错误, joinField=" + joinField);
                }
                return true;
            }
        }
        return false;
    }

    private void onlyOnePredicate(ConditionMap cond, Predicate predicate, Root<Entity> root, CriteriaBuilder builder, EntityType<Entity> entityType) {
        Attribute<?, ?> attribute = null;
        try {
            attribute = entityType.getDeclaredAttribute(cond.getField());
        } catch (Exception e) {
            attribute = entityType.getSupertype().getDeclaredAttribute(cond.getField());
        }
        switch (cond.getSignEnum()) {
            case eq: {
                predicate.getExpressions().add(builder.equal(root.get(cond.getField()).as(attribute.getJavaType()), cond.getValue()));
                break;
            }
            case lt: {
                predicate.getExpressions().add(builder.lessThan(root.get(cond.getField()), (Comparable) cond.getValue()));
                break;
            }
            case lte: {
                predicate.getExpressions().add(builder.lessThanOrEqualTo(root.get(cond.getField()), (Comparable) cond.getValue()));
                break;
            }
            case gt: {
                predicate.getExpressions().add(builder.greaterThan(root.get(cond.getField()), (Comparable) cond.getValue()));
                break;
            }
            case gte: {
                predicate.getExpressions().add(builder.greaterThanOrEqualTo(root.get(cond.getField()), (Comparable) cond.getValue()));
                break;
            }
            case ne: {
                predicate.getExpressions().add(builder.notEqual(root.get(cond.getField()), cond.getValue()));
                break;
            }
            case in: {
                if (cond.getValue() instanceof List) {
                    List list = (ArrayList) cond.getValue();
                    Object[] objects = list.toArray();
                    predicate.getExpressions().add(root.get(cond.getField()).as(attribute.getJavaType()).in(objects));
                    break;
                } else {
                    throw new BizException("in条件值要为List");
                }
            }
            case like: {
                predicate.getExpressions().add(builder.like(root.get(cond.getField()).as(String.class), "%" + escapeSQLLike(cond.getValue().toString()) + "%", '/'));
                break;
            }
            default:
                throw new BizException("数据库操作符不存在: sign=" + cond.getSignEnum().getDbOp());
        }
    }

    /**
     * like的通配符 ，需要替换
     *
     * @param likeStr
     * @return
     */
    private String escapeSQLLike(String likeStr) {
        String str = StringUtils.replace(likeStr, "_", "/_");
        str = StringUtils.replace(str, "%", "/%");
//        str = StringUtils.replace(str, "?", "_");
//        str = StringUtils.replace(str, "*", "%");
        return str;
    }

    private void joinPredicate(ConditionMap cond, JoinType type, Class<Entity> clazz, Root<Entity> root, Predicate predicate, CriteriaBuilder builder,Join<Entity, ?> join) {
        String[] str = cond.getField().split("\\.");
        String joinTable = str[0];
        String joinField = str[1];
        Class<?> tclazz = null;
        try {
            Field field = clazz.getDeclaredField(joinTable);
            tclazz = field.getType();
            if (tclazz.isAssignableFrom(List.class) || tclazz.isAssignableFrom(Set.class)) {
                // 如果是List类型，得到其Generic的类型
                Type genericType = field.getGenericType();
                // 如果是泛型参数的类型
                if (genericType instanceof ParameterizedType) {
                    ParameterizedType pt = (ParameterizedType) genericType;
                    //得到泛型里的class类型对象
                    tclazz = (Class<?>) pt.getActualTypeArguments()[0];
                }
            }
        } catch (Exception e) {
            throw new BizException("获取joinField类型错误，joinField=" + joinField);
        }
        if(root.getJavaType().isAssignableFrom(clazz)){
            join = root.join(joinTable, type);
        }else {
            join = join.join(joinTable,type);
        }
        //复杂关联
        if (str.length > 2) {
            Class<Entity> tableClazz = (Class<Entity>) tclazz;
            String field = cond.getField().replaceFirst(joinTable + "\\.", "");
            ConditionMap conditionMap = ConditionMap.build(field, cond.getSignEnum(), cond.getValue());
            joinPredicate(conditionMap, type, tableClazz, root, predicate, builder,join);
            return;
        }
        EntityType<?> joinEntityType = entityManager.getMetamodel().entity(tclazz);
        Attribute<?, ?> attribute = null;
        try {
            attribute = joinEntityType.getDeclaredAttribute(joinField);
        } catch (Exception e) {
            attribute = joinEntityType.getSupertype().getDeclaredAttribute(joinField);
        }
        switch (cond.getSignEnum()) {
            case eq: {
                predicate.getExpressions().add(builder.equal(join.get(joinField).as(attribute.getJavaType()), cond.getValue()));
                break;
            }
            case lt: {
                predicate.getExpressions().add(builder.lessThan(join.get(joinField), (Comparable) cond.getValue()));
                break;
            }
            case lte: {
                predicate.getExpressions().add(builder.lessThanOrEqualTo(join.get(joinField), (Comparable) cond.getValue()));
                break;
            }
            case gt: {
                predicate.getExpressions().add(builder.greaterThan(join.get(joinField), (Comparable) cond.getValue()));
                break;
            }
            case gte: {
                predicate.getExpressions().add(builder.greaterThanOrEqualTo(join.get(joinField), (Comparable) cond.getValue()));
                break;
            }
            case ne: {
                predicate.getExpressions().add(builder.notEqual(join.get(joinField), cond.getValue()));
                break;
            }
            case in: {
                if (cond.getValue() instanceof List) {
                    List list = (ArrayList) cond.getValue();
                    Object[] objects = list.toArray();
                    predicate.getExpressions().add(join.get(joinField).as(attribute.getJavaType()).in(objects));
                    break;
                } else {
                    throw new BizException("in条件值要为List");
                }
            }
            case like: {
                predicate.getExpressions().add(builder.like(join.get(joinField).as(String.class), "%" + escapeSQLLike(cond.getValue().toString()) + "%", '/'));
                break;
            }
            default:
                throw new BizException("数据库操作符不存在: sign=" + cond.getSignEnum().getDbOp());
        }
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
        } catch (Exception e) {

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
        } catch (Exception e) {

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
        } catch (Exception e) {

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
