package com.huboot.business.mybatis;


import java.util.List;

/*import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;*/

/**
 * @ClassName: ResponseBuilder
 * @Description:  REST Ful请求响应构建工具类
 */
public class ResponseBuilder {

    /**
     * 构建默认的成功响应信息
     *
     * @param
     * @return
     */
    public static BaseResponse buildSuccessResponse() {
        BaseResponse response = new BaseResponse();
        response.setSuccess(true);
        response.setMessage(Constant.OPERATE_SUCCESS);
        return response;
    }

    /**
     * 构建默认的成功响应信息
     *
     * @param obj
     * @return
     */
    /*@SuppressWarnings("unchecked")
    public static Response buildSuccessResponse(Object obj) {
        BaseResponse response = new BaseResponse();

        response.setSuccess(true);
        response.setMessage(Constant.OPERATE_SUCCESS);

        if (obj != null) {
            if (obj instanceof List) {
                // post
                List<Object> list = (List<Object>) obj;
                if (list.size() == 1) {
                    response.setResult(list.get(0));
                }
            } else if (obj instanceof Object[]) {
                // post
                Object[] array = (Object[]) obj;
                if (array.length == 1) {
                    response.setResult(array[0]);
                }
            } else {
                // patch || put
                response.setResult(obj);
            }
        }

        return Response.status(Response.Status.OK).entity(response).build();

    }*/

    /**
     * 构建默认的成功响应信息
     *
     * @param obj
     * @return
     */
    @SuppressWarnings("unchecked")
    public static BaseResponse buildSuccessResponse(Object obj) {
        if (obj instanceof Pager) {
            return buildPager((Pager) obj);
        }
        BaseResponse response = new BaseResponse();
        response.setSuccess(true);
        response.setMessage(Constant.OPERATE_SUCCESS);
        response.setResult(obj);
        return response;

    }

    @SuppressWarnings("unchecked")
    public static BaseResponse buildSuccessResponse1(Object obj) {
        if (obj instanceof Pager) {
            return buildPager((Pager) obj);
        }
        BaseResponse response = new BaseResponse();
        response.setSuccess(true);
        response.setMessage(Constant.OPERATE_LONG_RENT_SUCCESS);
        response.setResult(obj);
        return response;

    }

    /**
     * 构建默认的成功响应信息
     *
     * @param list
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static BaseResponse buildSuccessResponseByList(List list) {
        BaseResponse response = new BaseResponse();
        response.setSuccess(true);
        response.setMessage(Constant.OPERATE_SUCCESS);
        response.setResult(list);
        return response;

    }

    /**
     * 构建成功响应信息
     *
     * @param obj
     * @param meessage
     * @return
     */
    public static BaseResponse buildSuccessResponse(Object obj, String meessage) {
        if (obj instanceof Pager) {
            return buildPager((Pager) obj);
        }
        BaseResponse response = new BaseResponse();

        response.setSuccess(true);

        response.setResult(obj);

        response.setMessage(meessage);

        return response;
    }

    /**
     * 构建成功响应信息
     *
     * @param
     * @param meessage
     * @return
     */
    public static BaseResponse buildFaildResponse(int code, String meessage) {
        ResourceError error = new ResourceError();
        BaseResponse response = new BaseResponse();
        response.setSuccess(false);
        response.setMessage(meessage);
        error.setCode(code);
        error.setMessage(meessage);
        response.setError(error);
        /*if (code == ErrorCodeEnum.AuthFaild.getCode() || code == ErrorCodeEnum.InvalidUser.getCode()
                || code == ErrorCodeEnum.SESSIONISEXPIRED.getCode()) {
            return Response.status(Response.Status.UNAUTHORIZED).entity(response).build();
        }*/
        /*if (code == ErrorCodeEnum.NoPermission.getCode()) {
            return Response.status(Response.Status.FORBIDDEN).entity(response).build();
        }*/
        return response;
    }

    /**
     * 构建失败响应信息
     *
     * @param e
     * @return
     */
    public static BaseResponse buildFaildResponse(ApiException e) {
        return buildFaildResponse(e.getCode(), e.getMessage());
    }

    /**
     * 构建错误的响应
     *
     * @param
     * @return
     */
    public static BaseResponse buildFaildResponse(ErrorCodeEnum errorCode) {
        return buildFaildResponse(errorCode.getCode(), errorCode.getDescription());
    }

    /**
     * 构建分页响应信息
     *
     * @param pager
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static BaseResponse buildPager(Pager pager) {
        if (pager == null) {
            pager = new Pager();
        }
        PagerResultInfo result_info = new PagerResultInfo();
        BaseResponse response = new BaseResponse();

        response.setSuccess(true);

        response.setResult(pager.getPageItems());
        response.setResult_info(result_info);
        result_info.setPage(pager.getCurrPage());
        result_info.setPer_page(pager.getPageSize());
        result_info.setTotal_count(pager.getRowsCount());
        result_info.setPanorama(pager.getItemsPanorama());
        // 计算总页数
        int rowsCount = pager.getRowsCount();
        int pageSize = pager.getPageSize();
        int pageCount = 0;
        if (pageSize != 0) {
            pageCount = ((rowsCount / pageSize) > (rowsCount / pageSize) ? (rowsCount / pageSize) + 1 : rowsCount / pageSize);
            if (rowsCount % pageSize > 0) {
                pageCount = pageCount + 1;
            }
        }

        result_info.setPage_count(pageCount);

        return response;
    }


    /**
     * 构建成功响应信息
     *
     * @param
     * @param meessage
     * @return
     */
    public static BaseResponse buildFaildResponse(ErrorCodeEnum codeEnum, String meessage,String bizData) {
        ResourceError error = new ResourceError();
        BaseResponse response = new BaseResponse();
        response.setSuccess(false);
        response.setMessage(meessage);
        error.setCode(codeEnum.getCode());
        error.setMessage(meessage);
        error.setBizData(bizData);
        response.setError(error);
        /*if (code == ErrorCodeEnum.AuthFaild.getCode() || code == ErrorCodeEnum.InvalidUser.getCode()
                || code == ErrorCodeEnum.SESSIONISEXPIRED.getCode()) {
            return Response.status(Response.Status.UNAUTHORIZED).entity(response).build();
        }*/
        /*if (code == ErrorCodeEnum.NoPermission.getCode()) {
            return Response.status(Response.Status.FORBIDDEN).entity(response).build();
        }*/
        return response;
    }


}
