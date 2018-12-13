package com.huboot.business.mybatis;

import com.huboot.business.common.jpa.PrimaryKeyGenerator;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: AbstractBaseService
 * @Description: 基础服务类的抽象实现
 */
public abstract class AbstractBaseService<Domain extends AbstractDomain<PK>, PK extends Serializable>
        implements IBaseService<Domain, PK> {
    private IBaseDao<Domain, PK> businessDao = null;
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    public AbstractBaseService(IBaseDao<Domain, PK> businessDao) {
        this.businessDao = businessDao;
    }

    @SuppressWarnings("unchecked")
    @Override
    public PK create(Domain domain) throws ApiException {
        // 设置主键
        domain.setId(PrimaryKeyGenerator.SEQUENCE.next());
        // 设置创建人和修改人
        if (domain.getCreatorId() == null) {
            try {
                domain.setCreatorId(SecurityContextHelper.getUserIdIfExits());
            } catch (ApiException e) {
                logger.info("创建记录创建人：{}", e.getMessage());
//                domain.setCreatorId(Constant.SYSTEM_USER_ID);
            }
        }
        if (domain.getModifierId() == null) {
            try {
                domain.setModifierId(SecurityContextHelper.getUserIdIfExits());
            } catch (ApiException e) {
                logger.info("创建记录修改人：{}", e.getMessage());
//                domain.setModifierId(Constant.SYSTEM_USER_ID);
            }
        }
        // 设置status状态
        domain.setRecordStatus(RecordStatusEnum.activited.getValue());
        businessDao.create(domain);
        return (PK) domain.getId();
    }

    @Override
    public int update(Domain domain) throws ApiException {
        // 设置修改时间和修改人
        if (domain.getModifierId() == null) {
            try {
                domain.setModifierId(SecurityContextHelper.getUserIdIfExits());
            } catch (ApiException e) {
                logger.info("修改记录修改人：{}", e.getMessage());
//                domain.setModifierId(Constant.SYSTEM_USER_ID);
            }
        }
        // 执行更新
        return businessDao.update(domain);
    }

    /**
     * 更新指定非空字段
     *
     * @param domain
     * @throws ApiException
     */
    public int merge(Domain domain) throws ApiException {
        if (domain.getModifierId() == null) {
            try {
                domain.setModifierId(SecurityContextHelper.getUserIdIfExits());
            } catch (ApiException e) {
                logger.info("修改部分记录修改人：{}", e.getMessage());
//                domain.setModifierId(Constant.SYSTEM_USER_ID);
            }
        }
        // 拷贝树形
        domain.setModifyTime(new Timestamp(new Date().getTime()));
        return businessDao.merge(domain);
    }

    /**
     * 更新记录
     *
     * @param tage 要跟新的字段
     * @param sour 条件字段
     * @return
     */
    public int mergeByBeanProp(Domain tage, Domain sour) {
        /*if (tage.getModifierId() == null) {
            try{
                tage.setModifierId(SecurityContextHelper.getUserIdIfExits());
            }catch (ApiException e){
                logger.info("条件修改部分记录修改人：{}",e.getMessage());
//                tage.setModifierId(Constant.SYSTEM_USER_ID);
            }
        }*/
        // 拷贝树形
        tage.setModifyTime(new Timestamp(new Date().getTime()));
        return businessDao.mergeByBeanProp(tage, sour);
    }

    @Override
    public int remove(PK pk) throws ApiException {
        if (pk == null) {
            throw new ApiException(ErrorCodeEnum.PKNOTNULL);
        }
        return businessDao.remove(pk);
    }

    @Override
    public Domain find(PK pk) throws ApiException {
        if (pk == null) {
            throw new ApiException(ErrorCodeEnum.PKNOTNULL);
        }
        return businessDao.find(pk);
    }

    @Override
    public List<Domain> find(List<PK> pkList) throws ApiException {
        return businessDao.find(pkList);
    }

    @Override
    public List<Domain> findAll() throws ApiException {
        return businessDao.findAll();
    }

    @Override
    public Integer findCountByBeanProp(Domain domain) throws ApiException {
        return businessDao.findCountByBeanProp(domain);
    }

    @Override
    public List<Domain> findByBeanProp(Domain domain) throws ApiException {
        return businessDao.findByBeanProp(domain);
    }

    @Override
    public List<Domain> findByBeanPropWithOrder(Domain domain, List<CompositeOrder> compositeOrderList) throws ApiException {
        return businessDao.findByBeanPropWithOrder(domain, compositeOrderList);
    }

    /***
     * 根据属性查询数据 返回结果的第一个,如果结果有多个 将报错
     *
     * @param domain
     * @return
     * @throws ApiException
     */
    @Override
    public Domain getSingleByBeanProp(Domain domain) throws ApiException {
        List<Domain> list = businessDao.findByBeanProp(domain);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        } else if (list.size() > 1) {
            throw new ApiException(ErrorCodeEnum.MoreThanOneResult);
        }
        return list.get(0);
    }

    /**********
     * 根据属性查询数据 ,返回第一个
     *
     * @param domain
     * @return
     * @throws ApiException
     */
    @Override
    public Domain getFirstByBeanProp(Domain domain) throws ApiException {
        List<Domain> list = businessDao.findByBeanProp(domain);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public Pager<Domain> findForPager(Domain domain, Integer curPage, Integer pageSize) throws ApiException {
        Pager<Domain> pager = businessDao.findForPager(domain, curPage, pageSize);
        if (pager != null && pager.getPageItems() != null && pager.getPageItems().size() > 0) {
            Pager<Domain> newPager = new Pager<>();
            BeanUtils.copyProperties(pager, newPager);
            newPager.setPageItems(pager.getPageItems());
            return newPager;
        } else {
            Pager<Domain> emptyPager = new Pager<>();
            List<Domain> emptyPageItems = new ArrayList<>();
            if (pager != null) {
                BeanUtils.copyProperties(pager, emptyPager);
            }
            emptyPager.setPageItems(emptyPageItems);
            return emptyPager;
        }
    }

    @Override
    public List<Domain> create(List<Domain> domainList) throws ApiException {
        if (CollectionUtils.isEmpty(domainList)) {
            throw new ApiException(ErrorCodeEnum.NOTNULL);
        } else {
            for (Domain domain : domainList) {
                this.create(domain);
            }
        }
        return domainList;
    }

    @Override
    public int update(List<Domain> domainList) throws ApiException {
        int rows = 0;
        // 循环修改
        if (CollectionUtils.isEmpty(domainList)) {
            throw new ApiException(ErrorCodeEnum.NOTNULL);
        } else {
            for (Domain domain : domainList) {
                rows = rows + this.update(domain);
            }
        }
        return rows;
    }

    @Override
    public Pager<Domain> findForPager(QueryCondition queryCondition) throws ApiException {
        return this.businessDao.findForPager(queryCondition);
    }

    /**
     * 动态查询,返回map
     *
     * @param queryCondition
     * @return
     */
    public Pager<Map<String, Object>> findForPagerMap(QueryCondition queryCondition) throws ApiException {
        return this.businessDao.findForPagerMap(queryCondition);
    }

    @Override
    public int remove(List<PK> pkList) throws ApiException {
        int rows = 0;
        // 循环遍历主键数组
        if (!CollectionUtils.isEmpty(pkList)) {
            for (PK pk : pkList) {
                rows = rows + this.remove(pk);
            }
        }
        return rows;
    }

    @Override
    public List<Map<String, Object>> countWithGroupBy(QueryCondition queryCondition) throws ApiException {
        if (queryCondition == null) {
            return new ArrayList<Map<String, Object>>();
        }
        return businessDao.countWithGroupBy(queryCondition);
    }

    @Override
    public Map<String, Object> findForMap(QueryCondition queryCondition) throws ApiException {
        if (queryCondition == null) {
            return new HashedMap();
        }
        return businessDao.findForMap(queryCondition);
    }

    @Override
    public PK createInit(Domain domain) throws ApiException {
        // 设置主键
        if(domain.getId() == null){
            domain.setId(PrimaryKeyGenerator.SEQUENCE.next());
        }
        // 设置创建人和修改人
        if (domain.getCreatorId() == null) {
            try {
                domain.setCreatorId(SecurityContextHelper.getUserIdIfExits());
            } catch (ApiException e) {
                logger.info("创建记录创建人：{}", e.getMessage());
//                domain.setCreatorId(Constant.SYSTEM_USER_ID);
            }
        }
        if (domain.getModifierId() == null) {
            try {
                domain.setModifierId(SecurityContextHelper.getUserIdIfExits());
            } catch (ApiException e) {
                logger.info("创建记录修改人：{}", e.getMessage());
//                domain.setModifierId(Constant.SYSTEM_USER_ID);
            }
        }
        // 设置status状态
        domain.setRecordStatus(RecordStatusEnum.activited.getValue());
        businessDao.create(domain);
        return (PK) domain.getId();
    }
}
