package com.huboot.user.organization.service.impl;

import com.huboot.commons.component.exception.BizException;
import com.huboot.commons.filter.RequestInfo;
import com.huboot.commons.jpa.ConditionMap;
import com.huboot.commons.jpa.QueryCondition;
import com.huboot.commons.page.ShowPageImpl;
import com.huboot.commons.utils.AppAssert;
import com.huboot.commons.utils.SnGenerator;
import com.huboot.share.common.constant.BizConstant;
import com.huboot.share.user_service.api.dto.CompanyDetailInfo;
import com.huboot.share.user_service.api.dto.UserDetailInfo;
import com.huboot.share.user_service.data.AreaCacheData;
import com.huboot.share.user_service.data.UserCacheData;
import com.huboot.share.user_service.enums.*;
import com.huboot.user.organization.dto.admin.*;
import com.huboot.user.organization.dto.zkshop.OrganizationCompanyDetailForZkShopResDTO;
import com.huboot.user.organization.entity.OrganizationCompanyEntity;
import com.huboot.user.organization.entity.OrganizationEntity;
import com.huboot.user.organization.entity.OrganizationShopEntity;
import com.huboot.user.organization.repository.IOrganizationCompanyRepository;
import com.huboot.user.organization.repository.IOrganizationRepository;
import com.huboot.user.organization.repository.IOrganizationShopMicropageRepository;
import com.huboot.user.organization.repository.IOrganizationShopRepository;
import com.huboot.user.role.service.IRoleService;
import com.huboot.user.user.repository.IUserEmployeeRepository;

import com.huboot.user.common.constant.UserConstant;
import com.huboot.user.common.utils.UsernameGenerator;
import com.huboot.user.organization.dto.zkshop.OrganizationCompanyCreateForZkShopReqDTO;
import com.huboot.user.organization.entity.OrganizationShopMicropageEntity;
import com.huboot.user.organization.service.IOrganizationCompanyService;
import com.huboot.user.user.dto.zkshop.BusinessLicenseORCDTO;
import com.huboot.user.user.entity.UserEmployeeEntity;
import com.huboot.user.user.support.BaiduOCR;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户服务-公司表ServiceImpl
 */
@Service("organizationCompanyServiceImpl")
public class OrganizationCompanyServiceImpl implements IOrganizationCompanyService {

    private Logger logger = LoggerFactory.getLogger(OrganizationCompanyServiceImpl.class);

    @Autowired
    private IOrganizationCompanyRepository organizationCompanyRepository;
    @Autowired
    private IOrganizationRepository organizationRepository;
    @Autowired
    private UserCacheData userCacheData;
    @Autowired
    private AreaCacheData areaCacheData;
    @Autowired
    private IOrganizationShopRepository organizationShopRepository;
    @Autowired
    private IOrganizationShopMicropageRepository organizationShopMicropageRepository;
    @Autowired
    private BaiduOCR baiduOCR;
    /*@Autowired
    private FileFeignClient fileFeignClient;*/
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IUserEmployeeRepository userEmployeeRepository;

    @Transactional
    @Override
    public void create(@Valid OrganizationCompanyCreateReqDTO createReqDTO) throws BizException {
        long countByCode = organizationCompanyRepository.countByCode(createReqDTO.getCode());
        if (countByCode > 0) {
            throw new BizException("公司代码已经存在");
        }
        long countByName = organizationCompanyRepository.countByName(createReqDTO.getName());
        if (countByName > 0) {
            throw new BizException("公司名称已经存在");
        }
        //创建根节点
        OrganizationCompanyEntity entity = new OrganizationCompanyEntity();
        BeanUtils.copyProperties(createReqDTO, entity);
        OrganizationEntity organizationEntity = new OrganizationEntity();
        organizationEntity.setName(createReqDTO.getName());
        organizationEntity.setParentId(UserConstant.ORGANIZATION_ROOT_ID);
        organizationEntity.setPath(Arrays.asList(organizationEntity.getId()));
        organizationEntity.setStatus("");
        //organizationEntity.setSystem(Arrays.asList(wyc_shop_admin, wyc_driver_miniapp));
        organizationEntity.setType("");
        organizationRepository.create(organizationEntity);
        //创建公司信息
        entity.setOrganizationId(organizationEntity.getId());
        entity.setSn(SnGenerator.generatorOrganizationCompany());
        entity.setAuditStatus(OrganizationCompanyStatusEnum.audited);
        organizationCompanyRepository.create(entity);
        //创建网约车店铺
        OrganizationShopEntity organizationShopEntity = new OrganizationShopEntity();
        organizationShopEntity.setAddress(entity.getAddress());
        organizationShopEntity.setAreaId(entity.getAreaId());
        organizationShopEntity.setBusinessType(OrganizationShopBusinessTypeEnum.default_type);
        organizationShopEntity.setContract("");
        organizationShopEntity.setLogoPath("");
        organizationShopEntity.setOrganizationId(entity.getOrganizationId());
        organizationShopEntity.setPhone("");
        organizationShopEntity.setSn(SnGenerator.generatorOrganizationShop());
        organizationShopEntity.setStatus(OrganizationShopStatusEnum.default_status);
        //organizationShopEntity.setSystem(Arrays.asList(wyc_shop_admin, wyc_driver_miniapp));
        organizationShopEntity.setName(organizationEntity.getName());
        organizationShopRepository.create(organizationShopEntity);
        //创建网约车微页面
        OrganizationShopMicropageEntity organizationShopMicropageEntity = new OrganizationShopMicropageEntity();
        organizationShopMicropageEntity.setShopSn(organizationShopEntity.getSn());
        organizationShopMicropageEntity.setIntroductionRc("");
        organizationShopMicropageEntity.setIntroductionRelease("");
        organizationShopMicropageEntity.setWelfareRc("");
        organizationShopMicropageEntity.setWelfareRelease("");
        organizationShopMicropageEntity.setModelRc("");
        organizationShopMicropageEntity.setModelRelease("");
        organizationShopMicropageEntity.setGuideRc("");
        organizationShopMicropageEntity.setGuideRelease("");
        organizationShopMicropageEntity.setContactRc("");
        organizationShopMicropageEntity.setContactRelease("");
        organizationShopMicropageEntity.setPromotionRc("");
        organizationShopMicropageEntity.setPromotionRelease("");
        organizationShopMicropageRepository.create(organizationShopMicropageEntity);

    }

    @Transactional
    @Override
    public void modify(@Valid OrganizationCompanyModifyReqDTO modifyReqDTO) throws BizException {
        List<OrganizationCompanyEntity> organizationCompanyEntityList = organizationCompanyRepository.findByName(modifyReqDTO.getName());
        if (organizationCompanyEntityList.size() > 1) {
            throw new BizException("公司名称已经存在");
        } else {
            if (organizationCompanyEntityList.size() == 1 && !organizationCompanyEntityList.get(0).getId().equals(modifyReqDTO.getId())) {
                throw new BizException("公司名称已经存在");
            }
        }
        OrganizationCompanyEntity entity = organizationCompanyRepository.find(modifyReqDTO.getId());
        BeanUtils.copyProperties(modifyReqDTO, entity);
        OrganizationEntity organizationEntity = entity.getOrganizationEntity();
        organizationEntity.setName(entity.getName());
        OrganizationShopEntity shopEntity = organizationShopRepository.findByOrganizationId(organizationEntity.getId());
        shopEntity.setName(entity.getName());
        organizationRepository.modify(organizationEntity);
        organizationCompanyRepository.modify(entity);
        organizationShopRepository.modify(shopEntity);
    }

    @Transactional
    @Override
    public void delete(Long id) throws BizException {
        organizationCompanyRepository.remove(id);
    }

    @Override
    public OrganizationCompanyDetailResDTO find(Long id) throws BizException {
        OrganizationCompanyEntity entity = organizationCompanyRepository.find(id);
        OrganizationCompanyDetailResDTO dto = new OrganizationCompanyDetailResDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    @Override
    public List<OrganizationCompanyNameResDTO> findByName(String name, String abbreviation) throws BizException {
        QueryCondition queryCondition = QueryCondition.from(OrganizationCompanyEntity.class).innerJoin("organizationEntity").where(list -> {
            list.add(ConditionMap.like("organizationEntity.system", OrganizationSystemEnum.wyc_shop_admin.toString()));
            if (!StringUtils.isEmpty(name)) list.add(ConditionMap.like("name", name));
            if (!StringUtils.isEmpty(abbreviation)) list.add(ConditionMap.like("abbreviation", abbreviation));
        }).sort(Sort.by("id").descending()).limit(10);
        List<OrganizationCompanyEntity> organizationCompanyEntities = organizationCompanyRepository.findByCondition(queryCondition);
        List<OrganizationCompanyNameResDTO> list = organizationCompanyEntities.stream().map(organizationCompanyEntity -> {
            OrganizationCompanyNameResDTO dto = new OrganizationCompanyNameResDTO();
            BeanUtils.copyProperties(organizationCompanyEntity, dto);
            return dto;
        }).collect(Collectors.toList());
        return list;
    }

    @Override
    public ShowPageImpl<OrganizationCompanyPageResDTO> findPage(OrganizationCompanyQueryReqDTO queryReqDTO) throws BizException {
        Page<OrganizationCompanyEntity> page = organizationCompanyRepository.findPage(QueryCondition.from(OrganizationCompanyEntity.class).innerJoin("organizationEntity").where(list -> {
            // 只查询公司信息-wyc_shop_admin;
            list.add(ConditionMap.like("organizationEntity.system", OrganizationSystemEnum.wyc_shop_admin));
            if (!StringUtils.isEmpty(queryReqDTO.getName())) list.add(ConditionMap.eq("name", queryReqDTO.getName()));
            if (!StringUtils.isEmpty(queryReqDTO.getAbbreviation()))
                list.add(ConditionMap.eq("abbreviation", queryReqDTO.getAbbreviation()));
            if (!StringUtils.isEmpty(queryReqDTO.getCode())) list.add(ConditionMap.like("code", queryReqDTO.getCode()));
            if (!StringUtils.isEmpty(queryReqDTO.getAreaId()))
                list.add(ConditionMap.like("areaId", queryReqDTO.getAreaId()));
        }).sort(Sort.by("id").descending()).limit(queryReqDTO.getPage(), queryReqDTO.getSize()));

        return new ShowPageImpl<>(page).map(entity -> {
            OrganizationCompanyPageResDTO dto = new OrganizationCompanyPageResDTO();
            BeanUtils.copyProperties(entity, dto);
            UserDetailInfo userDetailInfo = userCacheData.getUserDetailInfo(entity.getModifierId());
            dto.setModifyName(userDetailInfo.getUser().getName());
            dto.setAreaName(areaCacheData.getById(dto.getAreaId()).getFullName());
            return dto;
        });
    }

    @Override
    public String generatorUsername(Long organizationId) {
        //公司信息
        OrganizationCompanyEntity organizationCompanyEntity = organizationCompanyRepository.findByOrganizationId(organizationId);
        AppAssert.notNull(organizationCompanyEntity, "公司不存在");
        Integer lastNum = organizationCompanyEntity.getLastNum();
        lastNum++;
        organizationCompanyEntity.setLastNum(lastNum);
        organizationCompanyRepository.modify(organizationCompanyEntity);
        String currentNum = UsernameGenerator.getUsername(organizationCompanyEntity.getCode(), lastNum);
        return currentNum;
    }

    @Override
    public List<CompanyDetailInfo> findAll() throws BizException {
        List<OrganizationCompanyEntity> entityList = organizationCompanyRepository.findAll();
        List<CompanyDetailInfo> result = new ArrayList<>();
        entityList.stream().forEach(entity -> {
            CompanyDetailInfo info = new CompanyDetailInfo();
            BeanUtils.copyProperties(entity, info);
            result.add(info);
        });
        return result;
    }

    @Override
    public BusinessLicenseORCDTO businessLicenseOrc(MultipartFile file) throws Exception {
        BusinessLicenseORCDTO orcDto = baiduOCR.businessLicenseOrc(file.getBytes());
        /*FileDetailResDTO resDTO = fileFeignClient.create(file);
        orcDto.setBusinessLicensePath(resDTO.getFullPath());*/
        return orcDto;
    }

    @Override
    @Transactional
    public void create(@Valid OrganizationCompanyCreateForZkShopReqDTO createReqDTO) throws BizException {
        Long userId = RequestInfo.getJwtUser().getUserId();
        long countByName = organizationCompanyRepository.countByName(createReqDTO.getName());
        if (countByName > 0) {
            throw new BizException("公司名称已经存在");
        }
        UserEmployeeEntity userEmployeeEntity = userEmployeeRepository.findByUserId(userId);
        if (userEmployeeEntity != null) {
            throw new BizException("不能重复创建");
        }
        //创建根节点
        OrganizationCompanyEntity entity = new OrganizationCompanyEntity();
        BeanUtils.copyProperties(createReqDTO, entity);
        OrganizationEntity organizationEntity = new OrganizationEntity();
        organizationEntity.setName(createReqDTO.getName());
        organizationEntity.setParentId(UserConstant.ORGANIZATION_ROOT_ID);
        organizationEntity.setPath(Arrays.asList(organizationEntity.getId()));
        organizationEntity.setStatus("");
        //organizationEntity.setSystem(Arrays.asList(zk_shop, zk_user));
        organizationEntity.setType("");
        organizationRepository.create(organizationEntity);
        //创建公司信息
        entity.setOrganizationId(organizationEntity.getId());
        entity.setSn(SnGenerator.generatorOrganizationCompany());
        entity.setAuditStatus(OrganizationCompanyStatusEnum.audited);
        organizationCompanyRepository.create(entity);
        //创建直客店铺
        OrganizationShopEntity organizationShopEntity = new OrganizationShopEntity();
        organizationShopEntity.setAddress(entity.getAddress());
        organizationShopEntity.setAreaId(entity.getAreaId());
        organizationShopEntity.setBusinessType(OrganizationShopBusinessTypeEnum.default_type);
        organizationShopEntity.setContract("");
        organizationShopEntity.setLogoPath("");
        organizationShopEntity.setOrganizationId(entity.getOrganizationId());
        organizationShopEntity.setPhone("");
        organizationShopEntity.setSn(SnGenerator.generatorOrganizationShop());
        organizationShopEntity.setStatus(OrganizationShopStatusEnum.default_status);
        //organizationShopEntity.setSystem(Arrays.asList(zk_shop, zk_user));
        organizationShopEntity.setName(organizationEntity.getName());
        organizationShopRepository.create(organizationShopEntity);
        //初始组织角色
        roleService.initRoleForZkShopFormDefaultRole(entity.getOrganizationId());
        //添加用户角色--店长
        roleService.createRoleForZkShopFormDefaultRole(userId,entity.getOrganizationId(), BizConstant.ZKSHOP_MANAGER_ROLE_ID);
        //创建员工
        userEmployeeEntity = new UserEmployeeEntity();
        userEmployeeEntity.setJobNumber("");
        userEmployeeEntity.setOrganizationId(organizationEntity.getId());
        userEmployeeEntity.setStatus(UserEmployeeStatusEnum.enabled);
        userEmployeeEntity.setUserId(userId);
        userEmployeeRepository.create(userEmployeeEntity);
        //todo 创建账户-
    }

    @Override
    public OrganizationCompanyDetailForZkShopResDTO findForZkShop(Long id) throws BizException {
        return null;
    }
}
