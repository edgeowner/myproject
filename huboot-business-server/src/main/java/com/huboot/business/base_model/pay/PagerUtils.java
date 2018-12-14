package com.huboot.business.base_model.pay;

import com.huboot.business.mybatis.Pager;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/*********
 * 分页数据
 *
 * @author Coollf
 *
 */
public class PagerUtils {

    /*********
     * 分页数据转换
     *
     * @param pager
     * @param convert
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T, F> Pager<T> convert(Pager<?> pager, IConvert<F, T> convert) {
        if (pager == null) {
            return null;
        }
        Pager<T> outPage = new Pager<>();
        outPage.setCurrPage(pager.getCurrPage());
        outPage.setPageCount(pager.getPageCount());
        outPage.setPageRowsCount(pager.getPageRowsCount());
        outPage.setPageSize(pager.getPageSize());
        outPage.setRowsCount(pager.getRowsCount());
        outPage.setItemsPanorama(pager.getItemsPanorama());
        List<T> pageItems = new ArrayList<>();
        if (!CollectionUtils.isEmpty(pager.getPageItems())) {
            for (Object item : pager.getPageItems()) {
                pageItems.add((T) convert.doConvert((F) item));
            }
        }
        outPage.setPageItems(pageItems);

        return outPage;
    }

    public interface IConvert<E, T> {
        T doConvert(E source);
    }

    public static <T> Pager<T> toPager(Pager source, Class<T> targetClass) {
        Pager<T> target = new Pager<>();
        target.setCurrPage(source.getCurrPage());
        target.setPageCount(source.getPageCount());
        target.setPageRowsCount(source.getPageRowsCount());
        target.setPageSize(source.getPageSize());
        target.setRowsCount(source.getRowsCount());

        List<T> itemList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(source.getPageItems())) {
            for (Object item : source.getPageItems()) {
                try {
                    T targetItem = targetClass.newInstance();
                    BeanUtils.copyProperties(item, targetItem);
                    itemList.add(targetItem);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        }
        target.setPageItems(itemList);

        return target;
    }

}
