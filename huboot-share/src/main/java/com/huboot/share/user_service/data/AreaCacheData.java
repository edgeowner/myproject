package com.huboot.share.user_service.data;


import com.huboot.share.user_service.api.dto.AreaDTO;
import com.huboot.share.user_service.data.proxy.CacheDataProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2018/9/7 0007.
 */
@Component
public class AreaCacheData {

    @Autowired
    private CacheDataProxy proxy;

    /**
     * 获取地区信息
     * @param id
     * @return
     */
    public AreaDTO getById(Long id) {
        return proxy.getAreaInfo(id);
    }

    /**
     * 通过区域id获取city信息
     * @param areaId
     * @return
     */
    public AreaDTO getCityByAreaId(Long areaId) {
        AreaDTO area = getById(areaId);
        if(area != null) {
            return getById(area.getParentId());
        }
        return null;
    }


    /**
     * 通过区域id获取省份信息
     * @param areaId
     * @return
     */
    public AreaDTO getProvinceByAreaId(Long areaId) {
        AreaDTO city = getCityByAreaId(areaId);
        if(city != null) {
            return getById(city.getParentId());
        }
        return null;
    }
}
