package com.huboot.user.user.service;


import com.huboot.commons.component.exception.BizException;
import com.huboot.commons.page.ShowPageImpl;
import com.huboot.user.user.dto.zkshop.*;

/**
 *用户服务-企业员工表Service
 */
public interface IUserEmployeeService {

    /**
    * 创建
    * @param createReqDTO
    * @throws BizException
    */
    void create(ZkshopUserEmployeeCreateReqDTO createReqDTO) throws BizException;

    /**
    * 更新
    * @param modifyReqDTO
    * @throws BizException
    */
    void modify(ZkshopUserEmployeeModifyReqDTO modifyReqDTO) throws BizException;

    /**
    * 查询
    * @param id
    * @return
    * @throws BizException
    */
    ZkshopUserEmployeeDetailResDTO find(Long id) throws BizException;

    /**
    * 删除
    * @param id
    * @throws BizException
    */
    void delete(Long id) throws BizException;

    /**
    * 分页查询
    * @param queryReqDTO
    * @return
    * @throws BizException
    */
    ShowPageImpl<ZkshopUserEmployeePageResDTO> findPage(ZkshopUserEmployeeQueryReqDTO queryReqDTO) throws BizException;

}
