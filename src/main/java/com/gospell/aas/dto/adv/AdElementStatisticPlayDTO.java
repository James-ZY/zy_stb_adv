package com.gospell.aas.dto.adv;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 广告播放统计
 *
 * @author zhaohw
 * @date 2018-01-31 10:15
 */
public class AdElementStatisticPlayDTO extends AdStatisticPlayDTO{

    /**
     * 广告名称
     */
    @JsonProperty("advName")
    private String advName;

    /**
     * 广告类型
     */
    @JsonProperty("advTypeId")
    private String advTypeId;

    public String getAdvName() {
        return advName;
    }

    public void setAdvName(String advName) {
        this.advName = advName;
    }

    public String getAdvTypeId() {
        return advTypeId;
    }

    public void setAdvTypeId(String advTypeId) {
        this.advTypeId = advTypeId;
    }

    @Override
    public String toString() {
        return "AdElementStatisticPlayDTO{" +
                "advName='" + advName + '\'' +
                ", advTypeId='" + advTypeId + '\'' +
                '}';
    }
}
