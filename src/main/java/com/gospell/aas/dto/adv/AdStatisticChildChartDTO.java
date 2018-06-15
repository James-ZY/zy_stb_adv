package com.gospell.aas.dto.adv;

import java.util.Collection;

/**
 * 广告统计子数据
 *
 * @author zhaohw
 * @date 2018-01-31 15:00
 */
public class AdStatisticChildChartDTO {

    private String type;

    private Collection<AdElementStatisticPlayDTO> list;

    public AdStatisticChildChartDTO(String type, Collection<AdElementStatisticPlayDTO> list) {
        this.type = type;
        this.list = list;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Collection<AdElementStatisticPlayDTO> getList() {
        return list;
    }

    public void setList(Collection<AdElementStatisticPlayDTO> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "AdStatisticChildChartDTO{" +
                "type='" + type + '\'' +
                ", list=" + list +
                '}';
    }
}
