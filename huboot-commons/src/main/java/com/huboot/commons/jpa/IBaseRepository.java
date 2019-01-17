package com.huboot.commons.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

/**
 * Created by xlaoy on 2017/12/25 0025.
 */
@NoRepositoryBean
public interface IBaseRepository<Entity> extends JpaRepository<Entity, Long>, JpaSpecificationExecutor<Entity> {

    /**
     * 根据id查询数据
     *
     * @param id
     * @return
     */
    Entity find(Long id);

    /**
     * bean属性查询
     *
     * @param entity
     * @return
     */
    List<Entity> findByBeanProp(Entity entity);

    /**
     * bean属性查询数量
     *
     * @param entity
     * @return
     */
    long findCountByBeanProp(Entity entity);

    /**
     * bean属性查询带排序
     *
     * @param entity
     * @return
     */
    List<Entity> findByBeanPropWithSort(Entity entity, Sort sort);

    /**
     * bean属性查询限制个数
     *
     * @param entity
     * @return
     */
    List<Entity> findByBeanPropWithLimit(Entity entity, Integer limit);

    /**
     * bean属性查询带排序和个数
     *
     * @param entity
     * @return
     */
    List<Entity> findByBeanPropWithSortAndLimit(Entity entity, Sort sort, Integer limit);

    /**
     * 1.支持bean条件查询
     * 2.支持排序
     * 3.支持查询条数
     *
     * @param condition
     * @return
     */
    List<Entity> findByCondition(QueryCondition condition);

    /**
     * 创建
     *
     * @param entity
     * @return
     */
    Entity create(Entity entity);

    /**
     * 创建并立即提交
     *
     * @param entity
     * @return
     */
    Entity createAndFlush(Entity entity);

    /**
     * 批量创建
     *
     * @param entityList
     * @return
     */
    List<Entity> create(List<Entity> entityList);

    /**
     * 批量创建
     *
     * @param entityList
     * @return
     */
    List<Entity> createAndFlush(List<Entity> entityList);

    /**
     * 更新
     *
     * @param entity
     */
    Entity modify(Entity entity);

    /**
     * 更新
     *
     * @param entityList
     */
    List<Entity> modify(List<Entity> entityList);

    /**
     * 更新
     *
     * @param entity
     */
    Entity modifyAndFlush(Entity entity);

    /**
     * 更新
     *
     * @param entityList
     */
    List<Entity> modifyAndFlush(List<Entity> entityList);

    /**
     * 删除
     *
     * @param id
     * @return 删除成功返回Entity，删除错误返回null
     */
    Entity remove(Long id);

    /**
     * 删除
     *
     * @param entityList
     * @return
     */
    void remove(List<Entity> entityList);

    /**
     * 分页查询
     *
     * @return
     */
    Page<Entity> findPage(QueryCondition condition);

}
