package com.gospell.aas.dto.adv;

import java.util.List;

/**
 * 广告统计报表数据
 *
 * @author zhaohw
 * @date 2018-01-31 14:25
 */
public class AdStatisticChartDTO {

    private String language;

    /**
     * 按类型统计
     */
    private List<AdStatisticTypePlayDTO> typeList;

    /**
     * 分类型 按广告统计
     */
    private List<AdStatisticChildChartDTO> elementList;

    public AdStatisticChartDTO(List<AdStatisticTypePlayDTO> typeList, List<AdStatisticChildChartDTO> elementList) {
        this.typeList = typeList;
        this.elementList = elementList;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public List<AdStatisticTypePlayDTO> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<AdStatisticTypePlayDTO> typeList) {
        this.typeList = typeList;
    }

    public List<AdStatisticChildChartDTO> getElementList() {
        return elementList;
    }

    public void setElementList(List<AdStatisticChildChartDTO> elementList) {
        this.elementList = elementList;
    }

    @Override
    public String toString() {
        return "AdStatisticChartDTO{" +
                "language='" + language + '\'' +
                ", typeList=" + typeList +
                ", elementList=" + elementList +
                '}';
    }
}
