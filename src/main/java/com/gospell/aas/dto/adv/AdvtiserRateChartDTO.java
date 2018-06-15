package com.gospell.aas.dto.adv;

import java.util.Collection;

/**
 * 广告商在广告类型中占比Chart
 *
 * @author zhaohw
 * @date 2018-02-26 11:41
 */
public class AdvtiserRateChartDTO {

    private String type;

    private Collection<AdvtiserRateDTO> list;

    public AdvtiserRateChartDTO(String type, Collection<AdvtiserRateDTO> list) {
        this.type = type;
        this.list = list;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Collection<AdvtiserRateDTO> getList() {
        return list;
    }

    public void setList(Collection<AdvtiserRateDTO> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "AdvtiserRateChartDTO{" +
                "type='" + type + '\'' +
                ", list=" + list +
                '}';
    }
}
