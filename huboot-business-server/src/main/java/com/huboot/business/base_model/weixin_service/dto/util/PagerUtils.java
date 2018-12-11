package com.huboot.business.base_model.weixin_service.dto.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*********
 * 分页数据
 *
 * @author Coollf
 *
 */
public class PagerUtils {

    private static Logger logger = LoggerFactory.getLogger(PagerUtils.class);

    /*********
     * 分页数据转换
     *
     * @param pager
     * @param convert
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T, F> Pager<T> convert(Page<?> pager, IConvert<F, T> convert){
        if (pager == null) {
            return null;
        }
        Pager<T> outPage = new Pager<>();
        outPage.setCurrPage(pager.getNumber() + 1);
        outPage.setPageCount(pager.getTotalPages());
        outPage.setPageRowsCount(pager.getSize());
        outPage.setPageSize(pager.getSize());
        outPage.setRowsCount(new Long(pager.getTotalElements()).intValue());
        List<T> pageItems = new ArrayList<>();
        if (!CollectionUtils.isEmpty(pager.getContent())) {
            for (Object item : pager.getContent()) {
                try {
                    pageItems.add((T) convert.doConvert((F) item));
                } catch (RuntimeException | IOException e) {
                    e.printStackTrace();
                    logger.error("分页转行错误",e);
                }
            }
        }
        outPage.setPageItems(pageItems);

        return outPage;
    }

    /*********
     * 分页数据转换
     *
     * @param pager
     * @param convert
     * @return
     */
    public static <T, F> Pager<T> convert(Pager<?> pager, IConvert<F, T> convert){
        if (pager == null) {
            return null;
        }
        Pager<T> outPage = new Pager<>();
        outPage.setCurrPage(pager.getCurrPage());
        outPage.setPageCount(pager.getPageCount());
        outPage.setPageRowsCount(pager.getPageRowsCount());
        outPage.setPageSize(pager.getPageSize());
        outPage.setRowsCount(pager.getRowsCount());
        List<T> pageItems = new ArrayList<>();
        if (!CollectionUtils.isEmpty(pager.getPageItems())) {
            for (Object item : pager.getPageItems()) {
                try {
                    pageItems.add((T) convert.doConvert((F) item));
                } catch (IOException e) {
                    e.printStackTrace();
                    logger.error("分页转行错误",e);
                }
            }
        }
        outPage.setPageItems(pageItems);

        return outPage;
    }

    @FunctionalInterface
    public interface IConvert<E, T> {
        T doConvert(E source) throws IOException,RuntimeException;
    }

    /*public static <T> Pager<T> toPager(Pager source, Class<T> targetClass) {
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
    }*/

}
