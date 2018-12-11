package com.huboot.business.base_model.weixin_service.dto.business_manage_web;


import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface IBmwAutoReplyController {

    public static final String FIND_AUTOREPLY = "/business_manage_web/autoReply/find/{isDefault}";//
    public static final String SAVE_CONTENT = "/business_manage_web/autoReply/save";//
    public static final String FIND_BY_SHOPUID = "/business_manage_web/autoReply/findByShopUid/{shopUid}";//
    public static final String INIT_DEFAULT_CONTENT = "/business_manage_web/autoReply/init";//

    public static final String GET_LINK_LIST = "/business_manage_web/autoReply/getLinkList";//

    /**
     * 查询自动回复
     * **/
    @GetMapping(FIND_AUTOREPLY)
    @ApiOperation(value = "查询自动回复", notes = "查询自动回复")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK",response = AutoReplyDTO.class)})
    public AutoReplyDTO findAutoReply(Integer isDefault) throws Exception;


    /**
     * 保存自动回复
     * **/
    @PostMapping(SAVE_CONTENT)
    @ApiOperation(value = "保存自动回复", notes = "保存自动回复")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK",response = Void.class)})
    public void saveContent(@RequestBody AutoReplyDTO autoReplyDTO) throws Exception;


    /**
     * 根据shopUid查询自动回复
     * **/
    @GetMapping(FIND_BY_SHOPUID)
    @ApiOperation(value = "查询自动回复", notes = "查询自动回复")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK",response = AutoReplyDTO.class)})
    public AutoReplyDTO findByShopUid(@PathVariable(value = "shopUid") String shopUid) throws Exception;

    /**
     * 保存自动回复
     * **/
    @PostMapping(INIT_DEFAULT_CONTENT)
    @ApiOperation(value = "初始化或修改默认回复", notes = "初始化或修改默认回复")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK",response = Void.class)})
    public void initDefaultContent() throws Exception;

    /**
     * 查询自动回复
     * **/
    @GetMapping(GET_LINK_LIST)
    @ApiOperation(value = "获取链接集合", notes = "获取链接集合")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK",response = ReplyLinkDTO.class)})
    public List<ReplyLinkDTO> getLinkList() throws Exception;

}
