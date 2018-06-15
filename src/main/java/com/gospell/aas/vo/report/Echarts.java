package com.gospell.aas.vo.report;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * 
 * <p> Title: 封装传递的数据</p>
 * 
 * <p> Description: </p>
 * 
 * <p> Copyright: Copyright (c) 2015 by Free Lancer </p>
 * 
 * <p> Company: Free Lance </p>
 * 
 * @author: free lance
 * @Email: free.lance@Gmail.com
 * @version: 1.0
 * @date: 2015-3-9 下午4:05:19
 * 
 */
public class Echarts {
    public List<String> legend = Lists.newArrayList();// 数据分组
    public List<String> axis = Lists.newArrayList();// 横坐标
    public List<Series> series = Lists.newArrayList();// 纵坐标

    public Echarts(List<String> legendList, List<String> categoryList, List<Series> seriesList) {
        super();
        this.legend = legendList;
        this.axis = categoryList;
        this.series = seriesList;
    }
}