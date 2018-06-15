package com.gospell.aas.vo.report;

/**
 * 
 * <p> Title: 统计图表 坐标轴</p>
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
 * @date: 2015-3-8 下午9:56:30
 * 
 */
public class AxisVo {
    // 直角坐标系中的横轴，通常并默认为类目型
    private String xAxisData;
    // 直角坐标系中的纵轴，通常并默认为数值型
    private Integer yAxisData;

    /**
     * @return the xAxisData
     */
    public String getxAxisData() {
        return xAxisData;
    }

    /**
     * @param xAxisData
     *            the xAxisData to set
     */
    public void setxAxisData(String xAxisData) {
        this.xAxisData = xAxisData;
    }

    /**
     * @return the yAxisData
     */
    public Integer getyAxisData() {
        return yAxisData;
    }

    /**
     * @param yAxisData
     *            the yAxisData to set
     */
    public void setyAxisData(Integer yAxisData) {
        this.yAxisData = yAxisData;
    }

}
