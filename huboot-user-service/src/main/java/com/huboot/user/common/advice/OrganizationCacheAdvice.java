package com.huboot.user.common.advice;

import com.huboot.user.common.cache.OrganizationCachePutData;
import com.huboot.user.organization.entity.OrganizationCompanyEntity;
import com.huboot.user.organization.entity.OrganizationShopEntity;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Aspect
public class OrganizationCacheAdvice {

    @Autowired
    private OrganizationCachePutData organizationCachePutData;

    /**
     * 公司信息修改后，清空缓存信息
     * https://my.oschina.net/itblog/blog/211693
     *
     * @param param
     */
    @After(value = "(execution(* com.huboot.user.organization.repository.IOrganizationCompanyRepository.create*(..)) " +
            "|| execution(* com.huboot.user.organization.repository.IOrganizationCompanyRepository.save*(..)) " +
            "|| execution(* com.huboot.user.organization.repository.IOrganizationCompanyRepository.modify*(..)) " +
            "|| execution(* com.huboot.user.organization.repository.IOrganizationCompanyRepository.remove*(..)) " +
            "|| execution(* com.huboot.user.organization.repository.IOrganizationCompanyRepository.delete*(..)) )&& args(param)", argNames = "param")
    public void afterAdviceIOrganizationCompanyRepository(Object param) {
        if (param instanceof OrganizationCompanyEntity) {
            Long id = ((OrganizationCompanyEntity) param).getId();
            organizationCachePutData.clearCompanyInfoCache(id);
        } else if (param instanceof List) {
            List<OrganizationCompanyEntity> organizationCompanyEntities = (List<OrganizationCompanyEntity>) param;
            organizationCompanyEntities.forEach(organizationCompanyEntity -> {
                organizationCachePutData.clearCompanyInfoCache(organizationCompanyEntity.getId());
            });
        } else if (param instanceof Long) {
            Long id = (Long) param;
            organizationCachePutData.clearCompanyInfoCache(id);
        }
    }

    /**
     * 店铺信息修改后，清空缓存信息
     * https://my.oschina.net/itblog/blog/211693
     *
     * @param param
     */
    @After(value = "(execution(* com.huboot.user.organization.repository.IOrganizationShopRepository.create*(..)) " +
            "|| execution(* com.huboot.user.organization.repository.IOrganizationShopRepository.save*(..)) " +
            "|| execution(* com.huboot.user.organization.repository.IOrganizationShopRepository.modify*(..)) " +
            "|| execution(* com.huboot.user.organization.repository.IOrganizationShopRepository.remove*(..)) " +
            "|| execution(* com.huboot.user.organization.repository.IOrganizationShopRepository.delete*(..)) )&& args(param)", argNames = "param")
    public void afterAdviceIOrganizationShopRepository(Object param) {
        if (param instanceof OrganizationShopEntity) {
            Long id = ((OrganizationShopEntity) param).getId();
            organizationCachePutData.clearShopInfoCache(id);
        } else if (param instanceof List) {
            List<OrganizationShopEntity> userEntityList = (List<OrganizationShopEntity>) param;
            userEntityList.forEach(userEntity -> {
                organizationCachePutData.clearShopInfoCache(userEntity.getId());
            });
        } else if (param instanceof Long) {
            Long id = (Long) param;
            organizationCachePutData.clearShopInfoCache(id);
        }
    }

}
