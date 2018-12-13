package com.huboot.business.mybatis;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tonymac on 15/2/2.
 */
@ApiModel(value = "分页数据", description = "分页数据")
public class Pager<E> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 返回当前页号
     **/
    @ApiModelProperty("返回当前页号")
    private int currPage;

    /**
     * 返回分页大小
     **/
    @ApiModelProperty("返回分页大小")
    private int pageSize;

    /**
     * 返回总页数
     **/
    @ApiModelProperty("返回总页数")
    private int pageCount;

    /**
     * 返回当前页的记录条数
     **/
    @ApiModelProperty("返回当前页的记录条数")
    private int pageRowsCount;

    /**
     * 返回记录总行数
     **/
    @ApiModelProperty("返回记录总行数")
    private int rowsCount;

    /**
     * 返回记录详细内容
     **/
    @ApiModelProperty("返回记录详细内容")
    private List<E> pageItems = new ArrayList<E>();

    /**
     * 返回记录详细内容的聚合结果
     **/
    @ApiModelProperty("返回记录详细内容的聚合结果")
    private Map<String, Object> itemsPanorama = new HashMap();

    public Pager() {
    }

    public Pager(int curPage, int pageSize, int rowsCount, List<E> pageItems) {
        this.currPage = curPage;
        this.pageSize = pageSize;
        this.rowsCount = rowsCount;
        this.pageItems = pageItems;
        int pageCount = (int) Math.ceil((double) rowsCount / (double) pageSize);
        setCurrPage(curPage);
        setPageCount(pageCount);
        setPageItems(pageItems);
        setPageSize(pageSize);
        setRowsCount(rowsCount);
        if (pageItems == null || pageItems.isEmpty()) {
            setPageRowsCount(0);
        } else {
            setPageRowsCount(pageItems.size());
        }
    }


    public int getRowsCount() {
        return rowsCount;
    }

    public void setRowsCount(int rowsCount) {
        this.rowsCount = rowsCount;
    }

    public int getCurrPage() {
        return currPage;
    }

    public void setCurrPage(int currPage) {
        this.currPage = currPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getPageRowsCount() {
        return pageRowsCount;
    }

    public void setPageRowsCount(int pageRowsCount) {
        this.pageRowsCount = pageRowsCount;
    }

    public void setPageItems(List<E> pageItems) {
        this.pageItems = pageItems;
    }

    public List<E> getPageItems() {
        return pageItems;
    }

    public Map<String, Object> getItemsPanorama() {
        return itemsPanorama;
    }

    public void setItemsPanorama(Map<String, Object> itemsPanorama) {
        this.itemsPanorama = itemsPanorama;
    }

    public void addPanoramaItem(String key, Object value) {
        itemsPanorama.put(key, value);
    }
}
