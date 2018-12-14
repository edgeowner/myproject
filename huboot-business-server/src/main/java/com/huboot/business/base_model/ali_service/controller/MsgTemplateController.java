package com.huboot.business.base_model.ali_service.controller;

import com.huboot.business.base_model.ali_service.dto.template.AddTemplateReqDTO;
import com.huboot.business.base_model.ali_service.dto.template.DisableTemplateReqDTO;
import com.huboot.business.base_model.ali_service.entity.SystemSMSTemplateEntity;
import com.huboot.business.base_model.ali_service.service.MsgTemplateService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "短信模板接口")
@RestController
public class MsgTemplateController {

    @Autowired
    private MsgTemplateService msgTemplateService;

    /***
     * 添加模板
     * **/
    @PostMapping("/base_model/ali_service/sms/template")
    public SystemSMSTemplateEntity addTemplate(@Validated @RequestBody AddTemplateReqDTO addTemplateReqDTO){
        return msgTemplateService.add(addTemplateReqDTO);
    }

    /***
     * 禁用模板
     * **/
    @PutMapping("/base_model/ali_service/sms/template_dis")
    public void disableTemplate(@Validated @RequestBody DisableTemplateReqDTO disableTemplateReqDTO){
        msgTemplateService.disable(disableTemplateReqDTO);
    }
}
