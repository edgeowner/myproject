package com.huboot.share.notify_service.enums;

import com.huboot.commons.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by Administrator on 2018/8/27 0027.
 */
@AllArgsConstructor
@Getter
public enum SMSSendSignEnum implements BaseEnum {

    zuchehezi("【租车盒子】"),
    wujiechuxing("【无界出行】"),
    ;

    private String showName;

    @Override
    public String getShowName() {
        return showName;
    }

}
