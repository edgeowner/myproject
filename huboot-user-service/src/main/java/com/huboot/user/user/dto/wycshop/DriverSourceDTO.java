package com.huboot.user.user.dto.wycshop;

import com.huboot.share.user_service.enums.DriverSourceOneLevelEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/9/7 0007.
 */
@Data
public class DriverSourceDTO {

    @ApiModelProperty("值")
    private String value;
    @ApiModelProperty("名称")
    private String label ;
    @ApiModelProperty("子渠道")
    private List<SubDriverSource> children = new ArrayList<>();

    public DriverSourceDTO() {
    }

    public DriverSourceDTO(String value, String label) {
        this.value = value;
        this.label = label;
    }

    public DriverSourceDTO(DriverSourceOneLevelEnum oneLevelEnum) {
        this.value = oneLevelEnum.name();
        this.label = oneLevelEnum.getShowName();
    }

    @Data
    public static class SubDriverSource {
        @ApiModelProperty("值")
        private String value;
        @ApiModelProperty("名称")
        private String label ;

        public SubDriverSource() {
        }

        public SubDriverSource(String value, String label) {
            this.value = value;
            this.label = label;
        }
    }


}
