package com.xiaomo.domain;

import com.github.pagehelper.PageInfo;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PageVo<T> {
    private PageInfo<T> pageInfo;

    private List<T> list = new ArrayList<T>();    //数据集合


    public PageVo(PageInfo<T> pageInfo, List<T> list) {
        this.pageInfo = pageInfo;
        this.list = list;
    }
}
