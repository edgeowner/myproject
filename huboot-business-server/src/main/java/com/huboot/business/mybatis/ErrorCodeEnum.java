package com.huboot.business.mybatis;

/**
 * @author zhangtiebin@hn-zhixin.com
 * @ClassName: ErrorCodeEnum
 * @Description: 错误编码定义
 * @date 2015年7月2日 下午2:19:38
 */
public enum ErrorCodeEnum {
    // 系统默认异常
    SystemError(10000, "System Error", "系统错误"),

    /****
     * 认证失败
     */
    AuthFaild(10001, "Auth Faild", "认证失败"),

    /***
     * 错误的HTTP方法
     */
    UnsupportMethod(10002, "Unsupport Method ", "错误的HTTP方法"),

    /**
     * 没有这个API
     */
    NoSuchAPI(10003, "No Such API", "没有这个API"),

    /***
     * 错误的URI,没有找到对应的路由
     */
    NoRoute(10004, "No Permission", "错误的URI,没有找到对应的路由"),

    /**
     * 权限不足
     */
    NoPermission(10005, "No Permission", "权限不足"),

    /**
     * Session已经过期
     */
    SESSIONISEXPIRED(10006, "Sessiion Is Expired", "Session已经过期"),

    /**
     * Session已经过期
     */
    NOTLOGN(10007, "User not login", "用户未登录"),


    /**
     * 未知错误
     */
    UnknownError(10008, "Unknown Error", "未知错误"),

    /***
     * 解析失败
     */
    JsonError(10009, "Json ErrorJson", "解析失败"),

    /***
     * 参数错误
     */
    ParamsError(10010, "Params Error", "参数错误"),

    /*****
     * 无效的用户
     */
    InvalidUser(10011, "Invalid User", "无效的用户"),

    /***
     * 访问的资源不存在
     */
    NotFound(10012, "NotFound", "访问的资源不存在"),

    /**
     * 错误的请求
     */
    BadRequest(10013, "BadRequest", "错误的请求"),

    /**
     * 内容不能为空
     **/
    NOTNULL(10014, "Should Not NULL", "内容不能为空"),
    /**
     * 数据权限不足
     */
    NoDataPermission(10015, "No DATA Permission", "数据权限不足"),
    // 主键不能为空
    PKNOTNULL(10016, "PK Should Not NULL", "主键不能为空"),

    // 主键获取失败
    PKError(10017, "PK Parse Error", "主键获取失败"),

    /***
     * 结果集超过一条
     */
    MoreThanOneResult(10018, "More than one result set", "结果集超过一条"),

    /**
     * 用户未认证
     */
    NOAUDIT(10019, "User not audit", "用户未认证"),

    // RBAC异常
    UserNotFound(102001, "User Not Found", "用户不存在"),

    // 用户名为空
    UserNameEmpty(102002, "User Name Is Empty", "登录账号不能为空"),

    // 用户密码为空
    UserPWDEmpty(102003, "User Password Is Empty", "登录密码不能为空"),

    // 用户名或者密码错误
    UserOrPWDWrong(102004, "User Or Password Is Wrong", "您输入的账号或者密码有误，请重新输入"),

    // 用户已经拥有这个角色
    DuplicateRole(102005, "The Role Is Duplicate For User", "用户已经拥有这个角色"),

    //该账号非管理账户
    UserNotIsManaged(102006, "The User Not is Managed", "该账号非后台管理账户"),

    // 验证码不正确
    VerificationCodeError(200901000, "VerificationCode IsError", "验证码不正确"),

    // 角色名称重复
    SystemDefaultValue(1020011, "System DefaultValue ", "角色名称重复 "),
    // 用户已被冻结
    UserFreezed(1020012, "User Freezed", "用户已被冻结"),
    // 角色名称重复
    DuplicateRoleName(102007, "The Role Name Is Duplicate", "角色名称重复"),

    // 角色显示名称不能为空
    RoleNameNotNull(102008, "The RoleName Is Null", "角色英文名称不能为空"),

    // 角色显示名称不能为空
    RoleDisplayNameNotNull(102009, "The Role DispalyName Is Null", "角色显示名称不能为空"),
    // 角色所属分组不能为空
    RoleGroupIdNotNull(1020010, "The Role Group Is Null", "角色所属分组不能为空"),

    // 角色英文名称太长
    RoleNameTooLong(1021012, "The Name Is Too Long", "角色英文名称太长"),
    // 角色显示名称太长
    RoleDisplayNameTooLong(1021013, "The Name Is Too Long", "角色显示名称太长"),
    // 用户已存在
    UserExists(1021014, "User Not Found", "用户已存在"),
    // 名称不能为空
    NameNotNull(1021011, "The Name Is Null", "名称不能为空"),

    // 名称太长
    NameTooLong(1021012, "The Name Is Too Long", "名称太长"),
    // 描述太长
    DescTooLong(1021013, "The Descript Is Too Long", "描述太长"),

    // 当前数据有子节点，不能删除
    ChildrenRecordExits(1021013, "Children Record Exits,Cant't Deleted!", "当前数据有子节点，不能删除！"),

    // 日志格式异常
    TimeFormatWrong(101007, "Time format Wrong", "日期格式错误"),

    ErrorSQLID(1, "Error SQL ID", "传入findForPager的查询SQLID为空"),
//------------------------------------------------------  分割线 ------------------------------------------------------//
    /**
     * 文件类型出错
     */
    FileTypeError(2005007, "file type error", "文件类型出错"),
    /**
     * 读取文件出错
     */
    FileReadError(2005008, "file type error", "文件读取出错"),

    /**
     * 店铺
     */
    ShopCarInvalid(800000001, "Shop Car Invalid", "部分车辆已失效，点击确认则只提交可租车辆订单"),
    ShopStoreNameIsExist(800000002, "Shop Store Name Is Exist", "地址名称已存在"),
    CompanyNameIsExist(800000003, "Company Name Is Exist", "企业名称已经存在"),
    IsYourFriend(800000004, "Is Your Friend", "该店铺已是您的友商"),

    OfferSheetIsExist(800000004, "OfferSheet Is Exist", "此单已完成报价，无需重复提交"),

    // RBAC异常
    UserOpenIdNotFound(25252525, "User Open Id Not Found", "用户openid不存在"),

    // RBAC异常
    WXException(25252526, "weixin Exception", "微信未知异常"),

    // 订单是否已邀请车管选车
    OrderIsInvited(696265, "Order Is Invited", "微信未知异常"),

    /**
     * 个人中心
     */
    ShopStoreCanNotDelete(900000001, "Shop Car Invalid", "门店下已发布车辆信息，暂不能删除哦"),

    /** 身份证实名认证 */
    IdcardCertificationFailure(400000001, "Idcard Certification Failure", "失败"),
    IdcardCertificationParamError(400000002, "Idcard Certification Param Error", "参数不正确"),
    IdcardCertificationCenterMaintenance(400000003, "Idcard Certification Center Maintenance", "身份证中心维护中"),
    IdcardCertificationBeInconsistentWith(400000004, "Idcard Certification Be Inconsistent With", "身份证姓名与身份证号不匹配"),
    IdcardCertificationCanNotFound(400000005, "Idcard Certification Be Inconsistent With", "无此身份证号码"),

    /** Excel相关 */
    ExcelHasNoData(500000001, "Excel Has No Data", "没有符合条件的数据"),

    PayStatusChange(300000001, "payment status changed", "支付状态已变更，请点击确认后重新操作"),
    /** code与前端做了绑定，不能擅自修改---警告"**/
    PayWXCodeExpire(300000002, "Pay WXCode Expire", "微信授权已过期,请刷新页面"),
    /** 员工管理 */
    StaffManageUserInfoError(600000001, "Staff Manage User Info Error", "账号不存在，无法添加"),
    StaffManageUserNotRequire(600000002, "Staff Manage User Not Require", "账号已有加入的店铺，无法添加"),
    StaffManageNeedCustomerAudit(600000003, "Staff Manage Need Customer Audit", "账号须个人认证后才能添加"),

    /** 违章查询报错提示 */
    ViolationVinOrEnIsError(700000001, "Violation Vin Is Error", "车辆识别代号（车架号）或发动机号信息有误，请联系我们：<br>021-65678087"),
    ViolationPlateNumberIsError(700000002, "Violation Plate Number Is Error", "车牌号信息有误，请联系我们：<br>021-65678087"),
    ViolationCityIsNotSupport(700000003, "Violation City Is Not Support", "抱歉，暂不支持该城市的违章查询"),
    ViolationTryAgain(700000004, "Violation Try Again", "请求未成功，请重新查询"),
    ViolationSystemMaintenance(700000005, "Violation System Maintenance", "系统维护或升级，请稍后重新查询"),
    ViolationInfoIsError(700000006, "Violation Info Is Error", "车辆信息有误，请联系我们：<br>021-65678087"),

    /** 查询团伙欺诈报错提示 */
    MiguanSystemMaintenance(800000001, "Miguan System Maintenance", "系统维护或升级，请稍后重新查询"),
    MiguanIdcardIsError(800000002, "Miguan Idcard Is Error", "查询身份证有误，请重试"),
    MiguanNameIsError(800000003, "Miguan Name Is Error", "查询姓名有误，请重试"),
    MiguanPhoneIsError(800000004, "Miguan Phone Is Error", "查询手机号有误，请重试"),
    MiguanTryAgain(800000005, "Miguan Try Again", "请求未成功，请重新查询"),

    /***
     * 定时任务执行一次
     */
    TimeOutActionOnlyOne(100111, "Only One Error", "执行一次失败"),
    /***
     * 定时任务循环执行
     */
    TimeOutActionLoop(100222, "Loop Error", "循环执行失败"),


    /**
     * 极速业务
     */
    FastUserNotFind(200300, "Fast User Not Find", "用户不存在"),
    FastUserNeedAuth(200301, "Fast User Need Auth", "用户需个人认证"),
    FastUserCompanyNeedAuth(200302, "Fast User Company Need Auth", "用户公司需认证"),
    UserNeedNormalAuth(200303, "User Need Normal Auth", "用户需正常认证"),

    /**
     *下单时阿里云语音通知出租方
     * **/
    Aliyun_vms_exp(900300,"call seller failed","呼叫出租方失败");




    ;



    private int code;
    private String message;
    private String description;

    private ErrorCodeEnum(int code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
