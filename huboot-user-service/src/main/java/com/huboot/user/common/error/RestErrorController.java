package com.huboot.user.common.error;

import com.huboot.commons.utils.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
public class RestErrorController extends BasicErrorController {
    private static final Logger logger = LoggerFactory.getLogger(RestErrorController.class);

    public RestErrorController(ErrorAttributes errorAttributes, ServerProperties serverProperties) {
        super(errorAttributes, serverProperties.getError());
    }

    @Override
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        Map<String, Object> body = getErrorAttributes(request,
                isIncludeStackTrace(request, MediaType.ALL));
        HttpStatus status = getStatus(request);
        String message = (String) body.get("message");
        logger.error("系统异常，url={{}}，参数={}", request.getRequestURI(),
                JsonUtil.buildNormalMapper().toJson(request.getParameterMap()), body);
        if (HttpStatus.UNAUTHORIZED.equals(status)) {
            message = "未登录或缺失访问凭证";
        } else if (HttpStatus.NOT_FOUND.equals(status)) {
            message = "你确定接口地址写对了？";
        } else if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            message = "系统异常";
        }
        Map<String, Object> map = new HashMap<>();
        map.put("code", status.value());
        map.put("message", message);
        return new ResponseEntity<>(map, status);
    }

}
