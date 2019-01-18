package com.huboot.user.organization.controller.inner;

import com.huboot.share.user_service.api.dto.CompanyDetailInfo;
import com.huboot.share.user_service.api.feign.CompanyFeignClient;
import com.huboot.user.organization.entity.OrganizationCompanyEntity;
import com.huboot.user.organization.repository.IOrganizationCompanyRepository;
import com.huboot.user.organization.service.IOrganizationCompanyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "内部端-用户服务-公司表 API")
@RestController
public class InnerCompanyController implements CompanyFeignClient {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IOrganizationCompanyRepository organizationCompanyRepository;
    @Autowired
    private IOrganizationCompanyService organizationCompanyService;


    @ApiOperation("查询")
    public CompanyDetailInfo get(@PathVariable("id") Long id) {
        CompanyDetailInfo dto = new CompanyDetailInfo();
        OrganizationCompanyEntity entity = organizationCompanyRepository.find(id);
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    @Override
    public List<CompanyDetailInfo> all() {
        return organizationCompanyService.findAll();
    }
}
