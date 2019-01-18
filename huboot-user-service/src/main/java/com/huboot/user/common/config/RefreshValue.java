package com.huboot.user.common.config;

import com.huboot.user.weixin.dto.wycshop.WxmpMenuTempalteDTO;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2018/7/5 0005.
 */
@Data
@RefreshScope
@Component
public class RefreshValue {

    @Value("${domain.wycminiapp.requestdomain}")
    private String requestdomain;
    @Value("${domain.wycminiapp.wsrequestdomain}")
    private String wsrequestdomain;
    @Value("${domain.wycminiapp.uploaddomain}")
    private String uploaddomain;
    @Value("${domain.wycminiapp.downloaddomain}")
    private String downloaddomain;

    @Value("${idCardCertification.open}")
    private boolean idCardCertificationOpen;

    @Value("${idCardAndDriverLic.open}")
    private boolean idCardAndDriverLic;

    /*@Value("${wxmp.menu.commission.name}")
    private String menuCommissionName;
    @Value("${wxmp.menu.commission.displayname}")
    private String menuCommissionDisplayname;
    @Value("${wxmp.menu.commission.url}")
    private String menuCommissionUrl;
    @Value("${wxmp.menu.driver.name}")
    private String menuDriverName;
    @Value("${wxmp.menu.driver.displayname}")
    private String menuDriverDisplayname;
    @Value("${wxmp.menu.driver.url}")
    private String menuDriverUrl;*/

    //@Value("${wxmp.menu}")
    //private List<WxmpMenuTempalteDTO> menuTempalteList;
    @Bean
    @ConfigurationProperties(prefix = "wxmp.menu")
    public WxmpMenuTempalteDTO menuTempalteList() {
        return new WxmpMenuTempalteDTO();
    }
}
