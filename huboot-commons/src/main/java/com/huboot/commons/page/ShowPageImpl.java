package com.huboot.commons.page;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@JsonIgnoreProperties({"pageable", "numberOfElements", "sort"})
public class ShowPageImpl<T> extends PageImpl<T> {

    public ShowPageImpl(List<T> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    @Override
    public <U> ShowPageImpl<U> map(Function<? super T, ? extends U> converter) {
        return new ShowPageImpl<>(getConvertedContent(converter), getPageable(), getTotalElements());
    }

    public ShowPageImpl(Page<T> page) {
        this(page.getContent(), page.getPageable(), page.getTotalElements());
    }

    public static ShowPageImpl pager(Page page) {
        return new ShowPageImpl(page.getContent(), page.getPageable(), page.getTotalElements());
    }

    public static ShowPageImpl emptyPager(int size) {
        Pageable pageable = PageRequest.of(0, size);
        return new ShowPageImpl(new ArrayList(), pageable, 0);
    }

    @Override
    @ApiModelProperty("内容")
    public List<T> getContent() {
        return super.getContent();
    }

    @Override
    @ApiModelProperty("是否第一页")
    public boolean isFirst() {
        return super.isFirst();
    }

    @Override
    @ApiModelProperty("是否最后一页")
    public boolean isLast() {
        return super.isLast();
    }

    @Override
    @ApiModelProperty("页码")
    public int getNumber() {
        return super.getNumber() + 1;
    }

    @Override
    @ApiModelProperty("每页大小")
    public int getSize() {
        return super.getSize();
    }

    @Override
    @ApiModelProperty("总条数")
    public long getTotalElements() {
        return super.getTotalElements();
    }

    @Override
    @ApiModelProperty("总页数")
    public int getTotalPages() {
        return super.getTotalPages();
    }

    @Override
    @ApiModelProperty(value = "content集合的大小", hidden = true)
    public int getNumberOfElements() {
        return super.getNumberOfElements();
    }

    @Override
    @ApiModelProperty(value = "排序信息", hidden = true)
    public Sort getSort() {
        return super.getSort();
    }

    @Override
    @ApiModelProperty(value = "分页信息", hidden = true)
    public Pageable getPageable() {
        return super.getPageable();
    }
}
