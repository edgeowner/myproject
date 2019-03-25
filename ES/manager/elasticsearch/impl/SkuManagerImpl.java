package com.zchz.business.manager.elasticsearch.impl;


import com.zchz.business.domain.sku.SkuDomain;
import com.zchz.business.domain.sku.SkuYearDomain;
import com.zchz.business.domain.spu.SpuBrandDomain;
import com.zchz.business.domain.spu.SpuClassDomain;
import com.zchz.business.domain.spu.SpuDomain;
import com.zchz.business.domain.spu.SpuSupplierDomain;
import com.zchz.business.dto.elasticsearch.EsSkuDTO;
import com.zchz.business.manager.elasticsearch.EsSkuManager;
import com.zchz.business.service.elasticsearch.EsSkuService;
import com.zchz.business.service.sku.ISkuService;
import com.zchz.business.service.sku.ISkuYearService;
import com.zchz.business.service.spu.ISpuBrandService;
import com.zchz.business.service.spu.ISpuClassService;
import com.zchz.business.service.spu.ISpuService;
import com.zchz.business.service.spu.ISpuSupplierService;
import com.zchz.business.vo.car.CarQueryVO;
import com.zchz.business.vo.car.NavigationSkuVO;
import com.zchz.framework.exception.ApiException;
import com.zchz.framework.model.enums.dict.BrandEnableStatusEnum;
import com.zchz.framework.model.pager.Pager;
import com.zchz.framework.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 搜索-基础车型 ManagerImpl
 *
 * @author Tory.li
 * @create 2016-11-22 13:36
 **/
@Service("esSkuManagerImpl")
public class SkuManagerImpl implements EsSkuManager {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private EsSkuService esSkuService;
    @Autowired
    private ISkuService skuService;
    @Autowired
    private ISpuBrandService spuBrandService;
    @Autowired
    private ISpuSupplierService spuSupplierService;
    @Autowired
    private ISpuClassService spuClassService;
    @Autowired
    private ISpuService spuService;
    @Autowired
    private ISkuYearService skuYearService;

    @Override
    public void rebuildIndex() throws ApiException {
        //删除车型SKU索引库
        esSkuService.deleteAll();

        //循环新增document,一次1000条记录
        int currentPage = 1;
        do {
            Pager<SkuDomain> pager = skuService.findForPager(new SkuDomain(), currentPage, 1000);
            if (pager == null || CollectionUtils.isEmpty(pager.getPageItems())) {
                break;
            }
            List<EsSkuDTO> list = new ArrayList<>();
            for (SkuDomain skuDomain : pager.getPageItems()) {
                EsSkuDTO dto = new EsSkuDTO();
                dto.setId(skuDomain.getId().toString());
                SpuBrandDomain spuBrandDomain = spuBrandService.find(skuDomain.getSpuBrandId());
                dto.setSpuBrandName(spuBrandDomain.getName());
                SpuSupplierDomain spuSupplierDomain = spuSupplierService.find(skuDomain.getSpuSupplierId());
                dto.setSpuSupplierName(spuSupplierDomain.getName());
                SpuClassDomain spuClassDomain = spuClassService.find(skuDomain.getSpuClassId());
                dto.setSpuClassName(spuClassDomain.getName());
                SpuDomain spuDomain = spuService.find(skuDomain.getSpuId());
                dto.setSpuName(spuDomain.getName());
                SkuYearDomain skuYearDomain = skuYearService.find(skuDomain.getSkuYearId());
                dto.setSkuYearName(skuYearDomain.getName());
                dto.setImage(skuDomain.getImage());
                dto.setName(skuDomain.getName());
                dto.setDisplacement(skuDomain.getDisplacement());
                dto.setSeat(skuDomain.getSeat());
                dto.setGearbox(skuDomain.getGearbox());
                list.add(dto);
            }
            if(CollectionUtils.isEmpty(list)){
                break;
            }
            esSkuService.saveSkuList(list);
            logger.info("Indexing zchz/sku complete with " + pager.getCurrPage() + "/" + pager.getPageCount() + " Total rows is " + pager.getPageRowsCount());
            currentPage++;
        } while (true);
    }

    @Override
    public String findCarByQuery(CarQueryVO query) throws ApiException {
        Page<EsSkuDTO> esCarDTOPage = esSkuService.search(query.getBrandName(), query.getBrandSupplierName(), query.getBrandCarSeriesName());
        List<NavigationSkuVO> navigationSkuVOList = new ArrayList<NavigationSkuVO>();
        Iterator<EsSkuDTO> it = esCarDTOPage.iterator();
        while(it.hasNext()) {
            EsSkuDTO dto = it.next();
            //查询数据库判断此车型是否已经禁用
            SkuDomain skuDomain = skuService.find(Long.valueOf(dto.getId()));
            if(skuDomain.getEnableStatus().equals(BrandEnableStatusEnum.Enabled.getValue())){
                NavigationSkuVO navigationSkuVO = new NavigationSkuVO();
                navigationSkuVO.setId(dto.getId());
                navigationSkuVO.setYear(Integer.parseInt(dto.getSkuYearName()));
                navigationSkuVO.setBrand(dto.getSpuBrandName());
                navigationSkuVO.setSpu(dto.getSpuName());
                navigationSkuVO.setChexingmingcheng(dto.getName());
                navigationSkuVOList.add(navigationSkuVO);
            }
        }
        Collections.sort(navigationSkuVOList);
        Map<String , List<NavigationSkuVO>> map = new TreeMap<>(new Comparator<String>(){
            public int compare(String o1, String o2) {
                return o2.compareTo(o1);
            }
        });
        for (NavigationSkuVO navigationSkuVO : navigationSkuVOList) {
            String key = navigationSkuVO.getYearAndBrandAndSpu();
            if(map.containsKey(key)) {
                map.get(key).add(navigationSkuVO);
            } else {
                List<NavigationSkuVO> voList = new ArrayList<>();
                voList.add(navigationSkuVO);
                map.put(key, voList);
            }
        }
        return JsonUtils.toJsonString(map);
    }


    @Override
    public void rebuildIndexById(Long skuId) throws ApiException {
        SkuDomain skuDomain = skuService.find(skuId);

        EsSkuDTO dto = new EsSkuDTO();
        dto.setId(skuDomain.getId().toString());
        SpuBrandDomain spuBrandDomain = spuBrandService.find(skuDomain.getSpuBrandId());
        dto.setSpuBrandName(spuBrandDomain.getName());
        SpuSupplierDomain spuSupplierDomain = spuSupplierService.find(skuDomain.getSpuSupplierId());
        dto.setSpuSupplierName(spuSupplierDomain.getName());
        SpuClassDomain spuClassDomain = spuClassService.find(skuDomain.getSpuClassId());
        dto.setSpuClassName(spuClassDomain.getName());
        SpuDomain spuDomain = spuService.find(skuDomain.getSpuId());
        dto.setSpuName(spuDomain.getName());
        SkuYearDomain skuYearDomain = skuYearService.find(skuDomain.getSkuYearId());
        dto.setSkuYearName(skuYearDomain.getName());
        dto.setImage(skuDomain.getImage());
        dto.setName(skuDomain.getName());
        dto.setDisplacement(skuDomain.getDisplacement());
        dto.setSeat(skuDomain.getSeat());
        dto.setGearbox(skuDomain.getGearbox());
        esSkuService.saveSku(dto);

    }

    @Override
    public List<String> customPriceSkus() {

        return null;
    }

}
