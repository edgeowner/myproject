package com.huboot.user.user.entity;

import com.huboot.commons.jpa.AbstractEntity;
import com.huboot.commons.jpa.JpaConverterJson;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

import lombok.Data;

import java.util.List;

/**
*用户服务-个人信息表
*/
@Entity
@Table(name = "us_user_personal")
@DynamicInsert
@DynamicUpdate
@Data
public class UserPersonalEntity extends AbstractEntity {

    private static final long serialVersionUID = 1L;
    //用户ID
    private Long userId ;
    //身份证正面路径
    private String idcardFacePath ;
    //身份证反面路径
    private String idcardBackPath ;
    //名称
    private String name ;
    //身份证号
    private String num ;
    //驾驶证图片
    private String driverLicensePath ;
    //驾驶证姓名
    private String licName ;
    //驾驶证号
    private String licNum ;
    //初次领证日期
    private String licGetDate ;
    //准驾车型
    private String licCarModel ;
    //有效期
    private String licValidity ;
    //性别
    private String sex ;
    //
    private Long birthplaceCityId;
    //籍贯城市
    private String birthplace ;
    //
    private Long liveAreaId;
    //居住地区域地址
    private String liveAreaAddr ;
    //紧急联系人
    @Convert(converter = JpaConverterJson.class)
    private List<Contact> contacters ;
    //风控编号
    private String riskSn;

    //自定义添加
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "id", insertable = false, updatable = false)
    private UserEntity userEntity;

    @Data
    public static class Contact {
        private String relation ;
        private String name ;
        private String phone ;
    }


}

