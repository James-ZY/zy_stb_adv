package com.gospell.aas.vo.report;

import java.util.List;

/**
 * 
 * <p> Title: 同ECharts的series</p>
 * 
 * <p> Description: 数据系列，一个图表可能包含多个系列，每一个系列可能包含多个数据</p>
 * 
 * <p> Copyright: Copyright (c) 2015 by Free Lancer </p>
 * 
 * <p> Company: Free Lance </p>
 * 
 * @author: free lance
 * @Email: free.lance@Gmail.com
 * @version: 1.0
 * @date: 2015-3-9
 * 
 */
public class Series {
    public String name;
    public String type;
    public List<Integer> data;

    public Series() {
        // 默认为Line 折线图
        this.type = "line";
    }

    public Series(String name, List<Integer> data) {
        this.name = name;
        // 默认为Line 折线图
        this.type = "line";
        this.data = data;
    }

    public Series(String name, String type, List<Integer> data) {
        this.name = name;
        this.type = type;
        this.data = data;
    }
}