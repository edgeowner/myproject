package com.huboot.user.user.support;

import com.baidu.aip.ocr.AipOcr;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.huboot.commons.component.exception.BizException;
import com.huboot.commons.utils.JsonUtil;
import com.huboot.commons.utils.LocalDateTimeUtils;
import com.huboot.user.user.config.BaiduOCRProperties;
import com.huboot.user.user.dto.wycdriverminiapp.DriverLiscenseORCDTO;
import com.huboot.user.user.dto.wycdriverminiapp.IDCardORCDTO;
import com.huboot.user.user.dto.zkshop.BusinessLicenseORCDTO;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

/**
 * Created by Administrator on 2018/9/12 0012.
 * 文档地址：https://cloud.baidu.com/doc/OCR/OCR-API.html
 */
@Slf4j
@Component
@EnableConfigurationProperties(BaiduOCRProperties.class)
public class BaiduOCR implements InitializingBean {

    public static final String ID_CARD_FRONT = "front";// 身份证正面
    public static final String ID_CARD_BACK = "back";//身份证反面

    @Autowired
    private BaiduOCRProperties appProperties;

    private AipOcr aipOcr;

    @Override
    public void afterPropertiesSet() throws Exception {
        aipOcr = new AipOcr(appProperties.getAppId(), appProperties.getSecretID(), appProperties.getSecretKey());
    }

    public IDCardORCDTO idCardOrc(byte[] imgData, String side) {
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("detect_direction", "true");
        options.put("detect_risk", "false");

        JSONObject jsonObject = aipOcr.idcard(imgData, side, options);
        log.info("身份证解析结果：" + jsonObject.toString());
        JSONObject data = null;
        try {
            data = jsonObject.getJSONObject("words_result");
        } catch (Exception e) {
            throw new BizException("解析失败");
        }
        IDCardResult result = JsonUtil.buildNormalMapper().fromJson(data.toString(), IDCardResult.class);
        IDCardORCDTO orcDto = new IDCardORCDTO();
        orcDto.setName(result.getName().getWords());
        orcDto.setNum(result.getIdCardNum().getWords());
        return orcDto;
    }

    public DriverLiscenseORCDTO driverLiscenseOrc(byte[] imgData) {
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("detect_direction", "true");

        JSONObject jsonObject = aipOcr.drivingLicense(imgData, options);
        log.info("驾驶证解析结果：" + jsonObject.toString());
        JSONObject data = null;
        try {
            data = jsonObject.getJSONObject("words_result");
        } catch (Exception e) {
            throw new BizException("解析失败");
        }
        DriverLiscenseResult result = JsonUtil.buildNormalMapper().fromJson(data.toString(), DriverLiscenseResult.class);
        DriverLiscenseORCDTO orcdto = new DriverLiscenseORCDTO();
        orcdto.setLicName(result.getName().getWords());
        orcdto.setLicNum(result.getDrivingNum().getWords());
        orcdto.setLicCarModel(result.getVehicleModel().getWords());
        orcdto.setLicGetDate(tdf(result.getGetDate().getWords()));
        if (result.getValidPeriod() == null) {
            throw new BizException("解析失败，请确保上传的图片清晰！");
        }
        if (!StringUtils.isEmpty(result.getValidPeriod().getWords())) {
            //有限期限  至
            if (result.getSolstice() != null && !StringUtils.isEmpty(result.getSolstice().getWords())) {
                orcdto.setLicValidity(tdf(result.getValidPeriod().getWords()) + " - " + tdf(result.getSolstice().getWords()));
            }
            //有效起始日期  有限期限
            if (result.getStartDate() != null && !StringUtils.isEmpty(result.getStartDate().getWords())) {
                Integer validPeriod = Integer.valueOf(result.getValidPeriod().getWords().substring(0, 1));
                LocalDate startDate = LocalDate.parse(result.getStartDate().getWords(), DateTimeFormatter.ofPattern(LocalDateTimeUtils.NDATE));
                LocalDate endDate = startDate.plusYears(validPeriod);
                String endStr = endDate.format(DateTimeFormatter.ofPattern(LocalDateTimeUtils.NORMAL_DATE));
                orcdto.setLicValidity(tdf(result.getStartDate().getWords()) + " - " + endStr);
            }
        }

        return orcdto;
    }

    public BusinessLicenseORCDTO businessLicenseOrc(byte[] imgData) {
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<>();
        options.put("detect_direction", "true");
        options.put("accuracy", "normal");

        JSONObject jsonObject = aipOcr.businessLicense(imgData, options);
        log.info("身份证解析结果：" + jsonObject.toString());
        JSONObject data = null;
        try {
            data = jsonObject.getJSONObject("words_result");
        } catch (Exception e) {
            throw new BizException("解析失败");
        }
        BusinessLicenseResult result = JsonUtil.buildNormalMapper().fromJson(data.toString(), BusinessLicenseResult.class);
        BusinessLicenseORCDTO orcDto = new BusinessLicenseORCDTO();
        orcDto.setName(result.getCompanyName().getWords().equals("无") ? "" : result.getCompanyName().getWords());
        orcDto.setRegNum(result.getCreditCode().getWords().equals("无") ? "" : result.getCreditCode().getWords());
        orcDto.setPerson(result.getLegalPerson().getWords().equals("无") ? "" : result.getLegalPerson().getWords());
        orcDto.setPersonIdcard(result.getCertificateNumber().getWords().equals("无") ? "" : result.getCertificateNumber().getWords());
        return orcDto;
    }

    private String tdf(String odate) {
        if (StringUtils.isEmpty(odate)) {
            return odate;
        }
        LocalDate date = LocalDate.parse(odate, DateTimeFormatter.ofPattern(LocalDateTimeUtils.NDATE));
        return date.format(DateTimeFormatter.ofPattern(LocalDateTimeUtils.NORMAL_DATE));
    }


    @Data
    @ToString
    public static class IDCardResult {
        @JsonProperty("住址")
        private Result address;
        @JsonProperty("出生")
        private Result birth;
        @JsonProperty("姓名")
        private Result name;
        @JsonProperty("公民身份号码")
        private Result idCardNum;
        @JsonProperty("性别")
        private Result sex;
        @JsonProperty("民族")
        private Result nation;
        @JsonProperty("签发日期")
        private Result issueDate;
        @JsonProperty("签发机关")
        private Result authority;
        @JsonProperty("失效日期")
        private Result expiryDate;
    }

    @Data
    public static class DriverLiscenseResult {
        @JsonProperty("住址")
        private Result address;
        @JsonProperty("出生日期")
        private Result birth;
        @JsonProperty("姓名")
        private Result name;
        @JsonProperty("证号")
        private Result drivingNum;
        @JsonProperty("准驾车型")
        private Result vehicleModel;
        @JsonProperty("国籍")
        private Result nationality;
        @JsonProperty("性别")
        private Result sex;
        @JsonProperty("初次领证日期")
        private Result getDate;
        @JsonProperty("有效起始日期")
        private Result startDate;
        @JsonProperty("有效期限")
        private Result validPeriod;
        @JsonProperty("至")
        private Result solstice;
    }

    @Data
    public static class BusinessLicenseResult {
        @JsonProperty("单位名称")
        private Result companyName;
        @JsonProperty("法人")
        private Result legalPerson;
        @JsonProperty("地址")
        private Result address;
        @JsonProperty("有效期")
        private Result validity;
        @JsonProperty("证件编号")
        private Result certificateNumber;
        @JsonProperty("社会信用代码")
        private Result creditCode;
        @JsonProperty("成立日期")
        private Result registerDate;
        @JsonProperty("组成形式")
        private Result groupForm;
        @JsonProperty("注册资本")
        private Result registerCapital;
        @JsonProperty("类型")
        private Result type;
    }

    @Data
    public static class Result {
        //内容
        private String words;
    }
}
